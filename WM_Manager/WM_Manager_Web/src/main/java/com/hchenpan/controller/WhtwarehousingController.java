package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.*;
import com.hchenpan.service.*;
import com.hchenpan.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.WhtwarehousingController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class WhtwarehousingController extends BaseController {
    private final WhtwarehousingService whtwarehousingService;
    private final WhtwarehousinglistService whtwarehousinglistService;
    private final SparepartCodeService sparepartCodeService;
    private final StockService stockService;
    private final DictionaryschildService dictionaryschildService;
    private final LogsService logsService;
    private final UserService userService;

    @Autowired
    public WhtwarehousingController(WhtwarehousingService whtwarehousingService, WhtwarehousinglistService whtwarehousinglistService, SparepartCodeService sparepartCodeService, StockService stockService, DictionaryschildService dictionaryschildService, LogsService logsService, UserService userService) {
        this.whtwarehousingService = whtwarehousingService;
        this.whtwarehousinglistService = whtwarehousinglistService;
        this.sparepartCodeService = sparepartCodeService;
        this.stockService = stockService;
        this.dictionaryschildService = dictionaryschildService;
        this.logsService = logsService;
        this.userService = userService;
    }

    @GetMapping("/whtwarehousing")
    public String whtwarehousing() {
        return View("/reserve/whtwarehousing");
    }

    /**
     * 功能:采购入库维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/whtwarehousing/search")
    public String search(Whtwarehousing whtwarehousing) {
        /*通用字段赋值*/
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        Page<Whtwarehousing> page = getPage();
        EntityWrapper<Whtwarehousing> ew = new EntityWrapper<>();
        if (StringUtil.notTrimEmpty(whtwarehousing.getNotecode())) {
            ew.like("notecode", whtwarehousing.getNotecode(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(whtwarehousing.getDatetime1())) {
            ew.ge("entrydate", whtwarehousing.getDatetime1());
        }
        if (StringUtil.notTrimEmpty(whtwarehousing.getDatetime2())) {
            ew.le("entrydate", whtwarehousing.getDatetime2());
        }
        ew.eq("userid", loginUser.getId());
        ew.eq("flag", "E");
        return jsonPage(whtwarehousingService.selectPage(page, ew));
    }

    /**
     * 功能:提供所有采购入库信息,供下拉
     */
    @ResponseBody
    @PostMapping("/whtwarehousing/getall")
    public String getall() {
        return ERROR;
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/whtwarehousing/getbyid")
    public String getbyid(Whtwarehousing whtwarehousing) {
        return GetGsonString(whtwarehousingService.selectById(whtwarehousing.getId()));
    }

    /**
     * 功能：新增采购入库
     */
    @ResponseBody
    @PostMapping("/whtwarehousing/create")
    public String create(Whtwarehousing whtwarehousing) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            whtwarehousing.setId(getUUID());
            whtwarehousing.setCreatorid(loginUser.getId());
            whtwarehousing.setCreator(loginUser.getUsername());
            whtwarehousing.setCreatetime(timeString);
            whtwarehousing.setUpdaterid(loginUser.getId());
            whtwarehousing.setUpdater(loginUser.getUsername());
            whtwarehousing.setUpdatetime(timeString);
            whtwarehousing.setFlag("E");

            whtwarehousing.setNotecode(whtwarehousingService.createnotecode());
            whtwarehousing.setUserid(loginUser.getId().trim());
            whtwarehousing.setConsignee(loginUser.getRealname().trim());
            List<Dictionaryschild> list = dictionaryschildService.getdchildlistbydecode("BZ");
            whtwarehousing.setZjname(list.get(0).getName().trim());
            whtwarehousing.setZjcode(list.get(0).getCode().trim());

            whtwarehousing.setSummoney("0");
            whtwarehousing.setNote(whtwarehousing.getNote().trim());
            whtwarehousing.setRkstatus("未入库");

            whtwarehousingService.insert(whtwarehousing);

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(whtwarehousing.getId());
            logs.setName("com.hchenpan.controller.WhtwarehousingController.create");
            logs.setParams("com.hchenpan.pojo.Whtwarehousing类");
            logs.setDescription("新增无合同入库申请");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(whtwarehousing));
            logs.setCreatorid(loginUser.getId());
            logs.setCreator(loginUser.getUsername());
            logs.setCreatetime(timeString);
            logs.setRealname(loginUser.getRealname());
            logs.setUpdater(loginUser.getUsername());
            logs.setUpdatetime(timeString);
            logs.setId(getUUID());
            logsService.insert(logs);
            return SUCCESS;

        }
        return ERROR;
    }

    /**
     * 功能：更新采购入库
     */
    @ResponseBody
    @PostMapping("/whtwarehousing/update")
    public String update(Whtwarehousing whtwarehousing) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            whtwarehousing.setUpdaterid(loginUser.getId());
            whtwarehousing.setUpdater(loginUser.getUsername());
            whtwarehousing.setUpdatetime(timeString);

            Whtwarehousing d = whtwarehousingService.selectById(whtwarehousing.getId());
            String oldcontent = GetGsonString(d);
            whtwarehousingService.updateById(whtwarehousing);

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(whtwarehousing.getId());
            logs.setName("com.hchenpan.controller.WhtwarehousingController.update");
            logs.setParams("com.hchenpan.pojo.Whtwarehousing类");
            logs.setDescription("修改无合同入库申请大类");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(whtwarehousing));
            /* 修改，需要保存修改前后的数据 */
            logs.setOldcontent(oldcontent);
            logs.setCreatorid(loginUser.getId());
            logs.setCreator(loginUser.getUsername());
            logs.setCreatetime(timeString);
            logs.setUpdater(loginUser.getUsername());
            logs.setRealname(loginUser.getRealname());
            logs.setUpdatetime(timeString);
            logs.setId(getUUID());
            logsService.insert(logs);
            return SUCCESS;
        }
        return ERROR;
    }

    /**
     * 功能:删除采购入库
     */
    @ResponseBody
    @GetMapping("/whtwarehousing/delete")
    public String delete(Whtwarehousing whtwarehousing) {
        if (checkuser()) {
            Whtwarehousing d = whtwarehousingService.selectById(whtwarehousing.getId());
            String oldcontent = GetGsonString(d);
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            d.setUpdaterid(loginUser.getId());
            d.setUpdater(loginUser.getUsername());
            d.setUpdatetime(timeString);

            whtwarehousinglistService.delete(new EntityWrapper<Whtwarehousinglist>().eq("rkcode", d.getNotecode()));
            whtwarehousingService.deleteById(d);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(d.getId());
            logs.setName("com.hchenpan.controller.WhtwarehousingController.delete");
            logs.setParams("com.hchenpan.pojo.Whtwarehousing类");
            logs.setDescription("删除无合同入库申请大类");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(d));
            /* 修改，需要保存修改前后的数据 */
            logs.setOldcontent(oldcontent);
            logs.setCreatorid(loginUser.getId());
            logs.setCreator(loginUser.getUsername());
            logs.setCreatetime(timeString);
            logs.setUpdater(loginUser.getUsername());
            logs.setRealname(loginUser.getRealname());
            logs.setUpdatetime(timeString);
            logs.setId(getUUID());
            logsService.insert(logs);
            return SUCCESS;

        }
        return ERROR;
    }

    @ResponseBody
    @GetMapping("/whtwarehousing/getByrkcode")
    public String getByrkcode(String notecode) {
        return GetGsonString(whtwarehousingService.selectOne(new EntityWrapper<Whtwarehousing>()
                .eq("notecode", notecode)
                .eq("flag", "E")
        ));
    }

    /**
     * 功能:根据仓库编码获取所有入库单信息
     *
     * @author zws
     */
    @ResponseBody
    @GetMapping("/whtwarehousing/getalllist")
    public String getalllist(Whtwarehousing whtwarehousing) {
        return GetGsonString(whtwarehousingService.selectList(new EntityWrapper<Whtwarehousing>()
                .eq("storehousecode", whtwarehousing.getStorehousecode())
                .eq("flag", "E")
                .eq("rkstatus", "已入库")
        ));
    }

    @ResponseBody
    @GetMapping("/whtwarehousing/getwarehousinglist")
    public String getwarehousinglist() {
        /*通用字段赋值*/
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");

        return GetGsonString(whtwarehousingService.selectWHlist(loginUser.getId()));
    }


    /**
     * 功能:入库
     */
    @ResponseBody
    @GetMapping("/whtwarehousing/rk")
    public String rk(Whtwarehousing whtwarehousing) {
        if (checkuser()) {
            Whtwarehousing t = whtwarehousingService.selectById(whtwarehousing.getId());
            String oldcontent = GetGsonString(t);
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            int kongsl = whtwarehousinglistService.getkongnum(t.getNotecode().trim());
            int kongsl1 = whtwarehousinglistService.getkongnum1(t.getNotecode().trim());

            if (kongsl > 0 || kongsl1 == 0) {
                return "存在数量为空";
            } else {
                String storehousecode = t.getStorehousecode().trim();
                String storehousename = t.getStorehousename().trim();
                List<Whtwarehousinglist> rklist = whtwarehousinglistService.getallrkmx(t.getNotecode().trim());
                for (int i = 0; i < rklist.size(); i++) {
                    int getwz = stockService.getwznum(rklist.get(i).getWzcode().trim(), storehousecode);
                    if (getwz == 0) {
                        List<SparepartCode> sparepartcodelist = sparepartCodeService.getcodelx(rklist.get(i).getWzcode().trim(), "物资");
                        Stock d = new Stock();
                        d.setStockcode(storehousecode);
                        d.setStockname(storehousename);
                        d.setWzname(rklist.get(i).getWzname().trim());
                        d.setWzcode(rklist.get(i).getWzcode().trim());
                        d.setModelspcification(sparepartcodelist.get(0).getModelspecification().trim());
                        d.setUnit(sparepartcodelist.get(0).getUnit().trim());
                        d.setPrice(rklist.get(i).getPlanprice().trim());
                        List<Dictionaryschild> list = dictionaryschildService.getdchildlistbydecode("BZ");
                        d.setZjname(list.get(0).getName().trim());
                        d.setZjcode(list.get(0).getCode().trim());
                        d.setBqstart("0.00");
                        d.setBqstartmoney("0.00");
                        d.setBqin(rklist.get(i).getSjnum());
                        d.setBqinmoney(rklist.get(i).getSjmoney());
                        d.setBqout("0.00");
                        d.setBqoutmoney("0.00");
                        d.setBqend(rklist.get(i).getSjnum());
                        d.setBqendmoney(rklist.get(i).getSjmoney());
                        d.setPrice(rklist.get(i).getPlanprice());
                        d.setId(getUUID());
                        d.setCreatorid(loginUser.getId());
                        d.setCreator(loginUser.getUsername());
                        d.setCreatetime(timeString);
                        d.setUpdaterid(loginUser.getId());
                        d.setUpdater(loginUser.getUsername());
                        d.setUpdatetime(timeString);
                        stockService.insert(d);

                    } else {
                        List<Stock> stocklist = stockService.getwzlist(storehousecode, rklist.get(i).getWzcode().trim());
                        List<SparepartCode> sparepartcodelist = sparepartCodeService.getcodelx(rklist.get(i).getWzcode().trim(), "物资");
                        String kcnumber1 = stocklist.get(0).getBqend().trim();
                        BigDecimal kcnumber2 = new BigDecimal(kcnumber1);
                        String all = stocklist.get(0).getBqendmoney().trim();
                        String allrk = stocklist.get(0).getBqinmoney().trim();
                        BigDecimal kctotal = new BigDecimal(all);
                        BigDecimal rktotal = new BigDecimal(allrk);

                        String sjnum = rklist.get(i).getSjnum().trim();
                        BigDecimal sjnum1 = new BigDecimal(sjnum);
                        String syrknum3 = "";
                        String pjprice = "";
                        String sl = "";
                        Stock d1 = stockService.selectById(stocklist.get(0).getId());

                        String sjprice1 = rklist.get(i).getPlanprice().trim();
                        BigDecimal sjprice2 = new BigDecimal(sjprice1);
                        BigDecimal sjtotal = sjnum1.multiply(sjprice2).setScale(2, BigDecimal.ROUND_HALF_UP);
                        BigDecimal rktotal1 = rktotal.add(sjtotal).setScale(2, BigDecimal.ROUND_HALF_UP);
                        BigDecimal total = kctotal.add(sjtotal).setScale(2, BigDecimal.ROUND_HALF_UP);
                        d1.setBqinmoney(rktotal1.toString());
                        d1.setBqendmoney(total.toString());
                        BigDecimal sl1 = kcnumber2.add(sjnum1).setScale(2, BigDecimal.ROUND_HALF_UP);
                        BigDecimal pjprice1 = total.divide(sl1, 2, BigDecimal.ROUND_HALF_UP);
                        pjprice = pjprice1.toString();
                        sl = sl1.toString();


                        d1.setWzname(rklist.get(i).getWzname().trim());
                        d1.setWzcode(rklist.get(i).getWzcode().trim());
                        d1.setModelspcification(sparepartcodelist.get(0).getModelspecification().trim());
                        d1.setUnit(sparepartcodelist.get(0).getUnit().trim());
                        d1.setPrice(pjprice);
                        d1.setStockcode(storehousecode);
                        d1.setStockname(storehousename);

                        String in = d1.getBqin().trim();
                        String inmoney = d1.getBqinmoney().trim();
                        BigDecimal in1 = new BigDecimal(in);
                        BigDecimal innew = in1.add(sjnum1).setScale(2, BigDecimal.ROUND_HALF_UP);
                        d1.setBqin(innew.toString());
                        String end = d1.getBqend().trim();
                        BigDecimal end1 = new BigDecimal(end);
                        BigDecimal endnew = end1.add(sjnum1).setScale(2, BigDecimal.ROUND_HALF_UP);
                        d1.setBqend(endnew.toString());
                        d1.setUpdaterid(loginUser.getId());
                        d1.setUpdater(loginUser.getUsername());
                        d1.setUpdatetime(timeString);
                        stockService.updateById(d1);
                    }
                }
                t.setUpdaterid(loginUser.getId());
                t.setUpdater(loginUser.getUsername());
                t.setUpdatetime(timeString);
                t.setRkstatus("已入库");
                t.setUpdatetime(timeString);
                whtwarehousingService.updateById(t);

                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(t.getId());
                logs.setName("com.hchenpan.controller.WhtwarehousingController.rk");
                logs.setParams("com.hchenpan.pojo.Whtwarehousing类");
                logs.setDescription("无合同入库");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(t));
                /* 修改，需要保存修改前后的数据 */
                logs.setOldcontent(oldcontent);
                logs.setCreatorid(loginUser.getId());
                logs.setCreator(loginUser.getUsername());
                logs.setCreatetime(timeString);
                logs.setUpdater(loginUser.getUsername());
                logs.setRealname(loginUser.getRealname());
                logs.setUpdatetime(timeString);
                logs.setId(getUUID());
                logsService.insert(logs);
                return SUCCESS;

            }
        }
        return ERROR;
    }
}