package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.Dictionaryschild;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.Stock;
import com.hchenpan.pojo.User;
import com.hchenpan.service.DictionaryschildService;
import com.hchenpan.service.LogsService;
import com.hchenpan.service.StockService;
import com.hchenpan.service.UserService;
import com.hchenpan.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.StockController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class StockController extends BaseController {
    private final StockService stockService;
    private final DictionaryschildService dictionaryschildService;
    private final LogsService logsService;
    private final UserService userService;

    @Autowired
    public StockController(StockService stockService, DictionaryschildService dictionaryschildService, LogsService logsService, UserService userService) {
        this.stockService = stockService;
        this.dictionaryschildService = dictionaryschildService;
        this.logsService = logsService;
        this.userService = userService;
    }

    @GetMapping("/stock")
    public String stock() {
        return View("reserve/stock");
    }

    @GetMapping("/stockjz")
    public String stockjz() {
        return View("reserve/stockjz");
    }

    /**
     * 功能:库存维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/stock/search")
    public String search(Stock stock) {
        Page<Stock> page = getPage();
        EntityWrapper<Stock> ew = new EntityWrapper<>();
        if (StringUtil.notTrimEmpty(stock.getWzcode())) {
            ew.like("wzcode", stock.getWzcode(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(stock.getWzname())) {
            ew.like("wzname", stock.getWzname(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(stock.getStockname())) {
            ew.like("stockname", stock.getStockname(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(stock.getStockcode())) {
            ew.like("stockcode", stock.getStockcode(), SqlLike.DEFAULT);
        }
        ew.isNull("stockyearmon").isNull("tranflag");
        return jsonPage(stockService.selectPage(page, ew));
    }

    /**
     * 功能:提供所有库存信息,供下拉
     */
    @ResponseBody
    @PostMapping("/stock/getall")
    public String getall() {
        return ListToGson(stockService.selectList(new EntityWrapper<Stock>().orderBy("stockname")));
    }

    /**
     * 功能:提供所有库存信息,供下拉 依据物资编码排序
     */
    @ResponseBody
    @PostMapping("/stock/ getallOrderByWzbm")
    public String getallOrderByWzbm(Stock stock) {
        return ListToGson(stockService.selectList(new EntityWrapper<Stock>()
                .orderBy("wzcode")
                .ge("bqend", "0.00")
                .ge("bqend", "0")
                .ne("bqend", "0")
                .ne("bqend", "0.00")
                .isNull("stockyearmon")
                .isNull("tranflag")
                .ne(stock.getStockcode() != null, "stockcode", stock.getStockcode()))
        );
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/stock/getbyid")
    public String getbyid(Stock stock) {
        return GetGsonString(stockService.selectById(stock.getId()));
    }

    /**
     * 功能：根据仓库编码获取物资
     */
    @ResponseBody
    @GetMapping("/stock/getgoodslist")
    public String getgoodslist(Stock stock) {
        return ListToGson(stockService.selectList(new EntityWrapper<Stock>().eq("stockcode", stock.getStockcode()).isNull("stockyearmon").isNull("tranflag")));
    }

    /**
     * 功能：根据仓库编码获取物资
     */
    @ResponseBody
    @GetMapping("/stock/getgoods")
    public String getgoods(Stock stock) {
        return ListToGson(stockService.selectList(new EntityWrapper<Stock>()
                .ge("bqend", "0.00")
                .ge("bqend", "0")
                .ne("bqend", "0")
                .ne("bqend", "0.00")
                .isNull("stockyearmon")
                .isNull("tranflag")
                .ne(stock.getStockcode() != null, "stockcode", stock.getStockcode()))
        );
    }

    /**
     * 功能：获取结转 库存
     */
    @ResponseBody
    @GetMapping("/stock/jzlist")
    public String jzlist(Stock stock) {
        return ListToGson(stockService.selectList(new EntityWrapper<Stock>().isNull("stockyearmon").isNull("tranflag")));
    }

    /**
     * 功能：结转 库存
     */
    @ResponseBody
    @GetMapping("/stock/jz")
    public String jz(Stock stock) throws ParseException {
        if (checkuser()) {
            String stockyearmon = stock.getStockyearmon();
            int result = stockService.selectCount(new EntityWrapper<Stock>().eq("stockyearmon", stock.getStocklastyearmon()).eq("tranflag", "A"));
            int result1 = stockService.selectCount(new EntityWrapper<Stock>().eq("stockyearmon", stock.getStockyearmon()).eq("tranflag", "A"));
            int result2 = stockService.selectCount(new EntityWrapper<Stock>().eq("tranflag", "A"));
            List<Stock> stocklistzc = stockService.selectList(new EntityWrapper<Stock>().eq("tranflag", "A").orderBy("stockyearmon"));
            if (stocklistzc.size() != 0) {
                String beginTime = stocklistzc.get(0).getStockyearmon();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                if (dateFormat.parse(stock.getStockyearmon()).getTime() < dateFormat.parse(beginTime).getTime()) {
                    return "选取时间";
                } else if (result == 0 && (dateFormat.parse(stock.getStocklastyearmon()).getTime() > dateFormat.parse(beginTime).getTime())) {
                    return "上月未结转";
                } else if (result1 > 0) {
                    return "该月已结转";
                }
            }
            List<Stock> stocklist = stockService.selectList(new EntityWrapper<Stock>().isNull("stockyearmon").isNull("tranflag"));
            for (Stock d : stocklist) {
                d.setStockyearmon(stockyearmon);
                d.setTranflag("A");
                /*通用字段赋值*/
                User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                String timeString = GetCurrentTime();
                d.setUpdaterid(loginUser.getId());
                d.setUpdater(loginUser.getUsername());
                d.setUpdatetime(timeString);

                //更新已有记录 添加结转标志
                String oldcontent = GetGsonString(d);
                stockService.updateById(d);

                //新增结转后的 库存记录
                Stock st = new Stock();
                st.setStockcode(d.getStockcode().trim());
                st.setStockname(d.getStockname().trim());
                st.setModelspcification(d.getModelspcification().trim());
                st.setUnit(d.getUnit().trim());
                st.setWzcode(d.getWzcode().trim());
                st.setWzname(d.getWzname().trim());
                st.setBqstart(d.getBqend().trim());
                st.setBqstartmoney(d.getBqendmoney().trim());
                st.setBqend(d.getBqend().trim());
                st.setBqendmoney(d.getBqendmoney().trim());
                st.setBqin("0.00");
                st.setBqinmoney("0.00");
                st.setBqout("0.00");
                st.setBqoutmoney("0.00");
                st.setPrice(d.getPrice().trim());
                st.setUpdatetime(GetCurrentTime());
                List<Dictionaryschild> list = dictionaryschildService.getdchildlistbydecode("BZ");
                st.setZjname(list.get(0).getName());
                st.setZjcode(list.get(0).getCode());

                /*通用字段赋值*/
                st.setId(getUUID());
                st.setCreatorid(loginUser.getId());
                st.setCreator(loginUser.getUsername());
                st.setCreatetime(timeString);
                st.setUpdaterid(loginUser.getId());
                st.setUpdater(loginUser.getUsername());
                st.setUpdatetime(timeString);
                //写入日志表
                Logs logs = new Logs();
                logs.setId(getUUID());
                logs.setFlagid(st.getId());
                logs.setName("com.hchenpan.controller.StockController.create");
                logs.setParams("com.hchenpan.pojo.Stock类");
                logs.setDescription("库存结转");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(st));
                /* 修改，需要保存修改前后的数据 */
                logs.setOldcontent(oldcontent);
                logs.setCreatorid(loginUser.getId());
                logs.setCreator(loginUser.getUsername());
                logs.setCreatetime(timeString);
                logs.setUpdater(loginUser.getUsername());
                logs.setRealname(loginUser.getRealname());
                logs.setUpdatetime(timeString);
                logsService.insert(logs);
            }
            return SUCCESS;
        }
        return ERROR;
    }

    /**
     * 功能：查询库存数量
     */
    @ResponseBody
    @PostMapping("/stock/getkcnum")
    public String getkcnum(Stock stock) {
        return stockService.selectOne(new EntityWrapper<Stock>()
                .eq("stockcode", stock.getStockcode())
                .eq("wzcode", stock.getWzcode())
                .isNull("stockyearmon")
                .isNull("tranflag")
        ).getBqend();
    }

    /**
     * 功能：查询本期出库总合---数量
     */
    @ResponseBody
    @PostMapping("/stock/getcknum")
    public String getcknum(Stock stock) {
        return stockService.selectOne(new EntityWrapper<Stock>()
                .eq("stockcode", stock.getStockcode())
                .eq("wzcode", stock.getWzcode())
                .isNull("stockyearmon")
                .isNull("tranflag")
        ).getBqout();
    }

    /**
     * 功能:查询本期期末金额
     */
    @ResponseBody
    @PostMapping("/stock/getbqendmoney")
    public String getbqendmoney(Stock stock) {
        return stockService.selectOne(new EntityWrapper<Stock>()
                .eq("stockcode", stock.getStockcode())
                .eq("wzcode", stock.getWzcode())
                .isNull("stockyearmon")
                .isNull("tranflag")
        ).getBqendmoney();
    }

    /**
     * 功能:查询本期出库金额
     */
    @ResponseBody
    @PostMapping("/stock/getbqoutmoney")
    public String getbqoutmoney(Stock stock) {
        return stockService.selectOne(new EntityWrapper<Stock>()
                .eq("stockcode", stock.getStockcode())
                .eq("wzcode", stock.getWzcode())
                .isNull("stockyearmon")
                .isNull("tranflag")
        ).getBqoutmoney();
    }

    /**
     * 功能:查询物资实时单价
     */
    @ResponseBody
    @PostMapping("/stock/getprice")
    public String getprice(Stock stock) {
        return stockService.selectOne(new EntityWrapper<Stock>()
                .eq("stockcode", stock.getStockcode())
                .eq("wzcode", stock.getWzcode())
                .isNull("stockyearmon")
                .isNull("tranflag")
        ).getPrice();
    }

}