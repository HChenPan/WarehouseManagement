package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.*;
import com.hchenpan.service.*;
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
 * ClassName : com.hchenpan.controller.WarehousingController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class WarehousingController extends BaseController {
    private final WarehousingService warehousingService;
    private final WarehousinglistService warehousinglistService;
    private final ContractGoodsService contractGoodsService;
    private final SparepartCodeService sparepartCodeService;
    private final StockService stockService;
    private final LogsService logsService;
    private final DictionaryschildService dictionaryschildService;

    @Autowired
    public WarehousingController(WarehousingService warehousingService, WarehousinglistService warehousinglistService, ContractGoodsService contractGoodsService, SparepartCodeService sparepartCodeService, StockService stockService, LogsService logsService, DictionaryschildService dictionaryschildService, UserService userService) {
        this.warehousingService = warehousingService;
        this.warehousinglistService = warehousinglistService;
        this.contractGoodsService = contractGoodsService;
        this.sparepartCodeService = sparepartCodeService;
        this.stockService = stockService;
        this.logsService = logsService;
        this.dictionaryschildService = dictionaryschildService;
    }

    @GetMapping("/warehousing")
    public String warehousing() {
        return View("/reserve/warehousing");
    }

    /**
     * 功能:采购入库维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/warehousing/search")
    public String search(Warehousing warehousing) {
        Page<Warehousing> page = getPage();
        EntityWrapper<Warehousing> ew = new EntityWrapper<>();
        if (StringUtils.isNotEmpty(warehousing.getNotecode())) {
            ew.like("notecode", warehousing.getNotecode(), SqlLike.DEFAULT);
        }
        if (StringUtils.isNotEmpty(warehousing.getDatetime1())) {
            ew.gt("entrydate", warehousing.getDatetime1());

        }
        if (StringUtils.isNotEmpty(warehousing.getDatetime2())) {
            ew.lt("entrydate", warehousing.getDatetime2());
        }
        /*通用字段赋值*/
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        ew.eq("userid", loginUser.getId());

        return jsonPage(warehousingService.selectPage(page, ew));
    }

    /**
     * 功能:提供所有采购入库信息,供下拉
     */
    @ResponseBody
    @PostMapping("/warehousing/getall")
    public String getall() {
        return ERROR;
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/warehousing/getallbyid")
    public String getallbyid(Warehousing warehousing) {
        return GetGsonString(warehousingService.selectById(warehousing.getId()));
    }

    /**
     * 功能：新增采购入库
     */
    @ResponseBody
    @PostMapping("/warehousing/create")
    public String create(Warehousing warehousing) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            warehousing.setId(getUUID());
            warehousing.setCreatorid(loginUser.getId());
            warehousing.setCreator(loginUser.getUsername());
            warehousing.setCreatetime(timeString);
            warehousing.setUpdaterid(loginUser.getId());
            warehousing.setUpdater(loginUser.getUsername());
            warehousing.setUpdatetime(timeString);
            warehousing.setFlag("E");


            warehousing.setNotecode(warehousingService.createnotecode());
            warehousing.setUserid(loginUser.getId().trim());
            warehousing.setConsignee(loginUser.getRealname().trim());
            List<Dictionaryschild> list = dictionaryschildService.getdchildlistbydecode("BZ");
            warehousing.setZjname(list.get(0).getName().trim());
            warehousing.setZjcode(list.get(0).getCode().trim());
            warehousing.setSummoney("0");
            warehousing.setRkstatus("未入库");

            warehousingService.insert(warehousing);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(warehousing.getId());
            logs.setName("com.hchenpan.controller.WarehousingController.create");
            logs.setParams("com.hchenpan.pojo.Warehousing类");
            logs.setDescription("新增采购入库申请");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(warehousing));
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
    @PostMapping("/warehousing/update")
    public String update(Warehousing warehousing) {
        if (checkuser()) {
            {
                /*通用字段赋值*/
                User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                String timeString = GetCurrentTime();
                warehousing.setUpdaterid(loginUser.getId());
                warehousing.setUpdater(loginUser.getUsername());
                warehousing.setUpdatetime(timeString);
                Warehousing d = warehousingService.selectById(warehousing.getId());
                String oldcontent = GetGsonString(d);
                warehousingService.updateById(warehousing);
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(warehousing.getId());
                logs.setName("com.hchenpan.controller.WarehousingController.update");
                logs.setParams("com.hchenpan.pojo.Warehousing类");
                logs.setDescription("修改采购入库申请大类");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(warehousing));
                logs.setCreatorid(loginUser.getId());
                logs.setCreator(loginUser.getUsername());
                logs.setCreatetime(timeString);
                logs.setOldcontent(oldcontent);
                logs.setRealname(loginUser.getRealname());
                logs.setUpdater(loginUser.getUsername());
                logs.setUpdatetime(timeString);
                logs.setId(getUUID());
                logsService.insert(logs);
                return SUCCESS;

            }
        }
        return ERROR;
    }

    /**
     * 功能:删除采购入库
     */
    @ResponseBody
    @GetMapping("/warehousing/delete")
    public String delete(Warehousing warehousing) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            warehousing.setUpdaterid(loginUser.getId());
            warehousing.setUpdater(loginUser.getUsername());
            warehousing.setUpdatetime(timeString);
            Warehousing d = warehousingService.selectById(warehousing.getId());
            String oldcontent = GetGsonString(d);

            warehousinglistService.delete(new EntityWrapper<Warehousinglist>().eq("rkcode", d.getNotecode()));
            warehousingService.deleteById(d);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(warehousing.getId());
            logs.setName("com.hchenpan.controller.WarehousingController.delete");
            logs.setParams("com.hchenpan.pojo.Warehousing类");
            logs.setDescription("删除采购入库申请大类");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(warehousing));
            logs.setCreatorid(loginUser.getId());
            logs.setCreator(loginUser.getUsername());
            logs.setCreatetime(timeString);
            logs.setOldcontent(oldcontent);
            logs.setRealname(loginUser.getRealname());
            logs.setUpdater(loginUser.getUsername());
            logs.setUpdatetime(timeString);
            logs.setId(getUUID());
            logsService.insert(logs);
            return SUCCESS;
        }
        return ERROR;
    }

    @ResponseBody
    @GetMapping("/warehousing/getByrkcode")
    public String getByrkcode(String notecode) {
        return GetGsonString(warehousingService.selectOne(new EntityWrapper<Warehousing>()
                .eq("notecode", notecode)
                .eq("flag", "E")

        ));
    }

    /**
     * 功能:入库
     *
     * @author rj
     */
    @ResponseBody
    @GetMapping("/warehousing/rk2")
    public String rk2(Warehousing warehousing) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            Warehousing t = warehousingService.selectById(warehousing.getId());
            String oldcontent = GetGsonString(t);
            int kongsl = warehousinglistService.getkongnum(t.getNotecode().trim());
            int kongsl1 = warehousinglistService.getkongnum1(t.getNotecode().trim());
            if (kongsl > 0 || kongsl1 == 0) {
                return "存在数量为空";
            } else {

                List<Warehousinglist> rklist = warehousinglistService.getallrkmx(t.getNotecode().trim());
                for (int i = 0; i < rklist.size(); i++) {

                    String id = contractGoodsService.getid(rklist.get(i).getBuycode(), rklist.get(i).getWzcode(), rklist.get(i).getPlancode(), rklist.get(i).getContractbasicid());
                    ContractGoods ht = contractGoodsService.selectById(id);
                    String syrknum = ht.getSyrksum().trim();
                    BigDecimal syrknum1 = new BigDecimal(syrknum);
                    String sjnum = rklist.get(i).getSjnum().trim();
                    BigDecimal sjnum1 = new BigDecimal(sjnum);
                    String syrknum3 = "";
                    if (sjnum1.compareTo(syrknum1) >= 0) {
                        syrknum3 = "0.00";
                    } else {
                        BigDecimal syrknum2 = syrknum1.subtract(sjnum1).setScale(2, BigDecimal.ROUND_HALF_UP);
                        syrknum3 = syrknum2.toString();
                    }
                    ht.setSyrksum(syrknum3);
                    ht.setUpdaterid(loginUser.getId());
                    ht.setUpdater(loginUser.getUsername());
                    ht.setUpdatetime(timeString);
                    contractGoodsService.updateById(ht);


                }

                t.setUpdaterid(loginUser.getId());
                t.setUpdater(loginUser.getUsername());
                t.setUpdatetime(timeString);
                t.setRkstatus("已入库");
                warehousingService.updateById(t);
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(t.getId());
                logs.setName("com.hchenpan.controller.WarehousingController.rk2");
                logs.setParams("com.hchenpan.pojo.Warehousing类");
                logs.setDescription("补录合同入库");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(t));
                logs.setCreatorid(loginUser.getId());
                logs.setCreator(loginUser.getUsername());
                logs.setCreatetime(timeString);
                logs.setOldcontent(oldcontent);
                logs.setRealname(loginUser.getRealname());
                logs.setUpdater(loginUser.getUsername());
                logs.setUpdatetime(timeString);
                logs.setId(getUUID());
                logsService.insert(logs);
                return SUCCESS;
            }


        }
        return ERROR;
    }

    /**
     * 功能:根据仓库编码获取所有入库单信息
     *
     * @author zws
     */
    @ResponseBody
    @PostMapping("/warehousing/getalllist")
    public String getalllist(Warehousing warehousing) {
        return GetGsonString(warehousingService.selectList(new EntityWrapper<Warehousing>()
                .eq("flag", "E")
                .eq("storehousecode", warehousing.getStorehousecode())


        ));
    }

    @ResponseBody
    @PostMapping("/warehousing/getwarehousinglist")
    public String getwarehousinglist() {
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        return GetGsonString(warehousingService.selectUserList(loginUser.getId()));
    }

    /**
     * 功能:入库
     *
     * @author rj
     */
    @ResponseBody
    @PostMapping("/warehousing/rk")
    public String rk(Warehousing warehousing) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            Warehousing t = warehousingService.selectById(warehousing.getId());
            String oldcontent = GetGsonString(t);
            int kongsl = warehousinglistService.getkongnum(t.getNotecode().trim());
            int kongsl1 = warehousinglistService.getkongnum1(t.getNotecode().trim());
            if (kongsl > 0 || kongsl1 == 0) {
                return "存在数量为空";
            } else {
                String storehousecode = t.getStorehousecode().trim();
                String storehousename = t.getStorehousename().trim();
                List<Warehousinglist> rklist = warehousinglistService.getallrkmx(t.getNotecode().trim());
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
                        d.setUpdatetime(timeString);
                        d.setId(getUUID());
                        d.setCreatorid(loginUser.getId());
                        d.setCreator(loginUser.getUsername());
                        d.setCreatetime(timeString);
                        d.setUpdaterid(loginUser.getId());
                        d.setUpdater(loginUser.getUsername());
                        d.setUpdatetime(timeString);

                        stockService.insert(d);

                        String id = contractGoodsService.getid(rklist.get(i).getBuycode(), rklist.get(i).getWzcode(), rklist.get(i).getPlancode(), rklist.get(i).getContractbasicid());
                        ContractGoods ht = contractGoodsService.selectById(id);
                        String syrknum = ht.getSyrksum().trim();
                        BigDecimal syrknum1 = new BigDecimal(syrknum);
                        String sjnum = rklist.get(i).getSjnum().trim();
                        BigDecimal sjnum1 = new BigDecimal(sjnum);
                        String syrknum3 = "";
                        if (sjnum1.compareTo(syrknum1) >= 0) {
                            syrknum3 = "0.00";
                        } else {
                            BigDecimal syrknum2 = syrknum1.subtract(sjnum1).setScale(2, BigDecimal.ROUND_HALF_UP);
                            syrknum3 = syrknum2.toString();
                        }
                        ht.setSyrksum(syrknum3);

                        ht.setUpdaterid(loginUser.getId());
                        ht.setUpdater(loginUser.getUsername());
                        ht.setUpdatetime(timeString);
                        contractGoodsService.updateById(ht);

                    } else {
                        List<Stock> stocklist = stockService.getwzlist(storehousecode, rklist.get(i).getWzcode().trim());
                        List<SparepartCode> sparepartcodelist = sparepartCodeService.getcodelx(rklist.get(i).getWzcode().trim(), "物资");
                        String kcnumber1 = stocklist.get(0).getBqend().trim();
                        BigDecimal kcnumber2 = new BigDecimal(kcnumber1);
                        String all = stocklist.get(0).getBqendmoney().trim();
                        String allrk = stocklist.get(0).getBqinmoney().trim();
                        BigDecimal kctotal = new BigDecimal(all);
                        BigDecimal rktotal = new BigDecimal(allrk);
                        String id = contractGoodsService.getid(rklist.get(i).getBuycode(), rklist.get(i).getWzcode(), rklist.get(i).getPlancode(), rklist.get(i).getContractbasicid());
                        ContractGoods ht = contractGoodsService.selectById(id);
                        String syrknum = ht.getSyrksum().trim();
                        BigDecimal syrknum1 = new BigDecimal(syrknum);
                        String sjnum = rklist.get(i).getSjnum().trim();
                        BigDecimal sjnum1 = new BigDecimal(sjnum);
                        String syrknum3 = "";
                        String pjprice = "";
                        String sl = "";
                        Stock d1 = stockService.selectById(stocklist.get(0).getId());
                        if (sjnum1.compareTo(syrknum1) >= 0) {
                            syrknum3 = "0.00";
                            String sjprice1 = rklist.get(i).getPlanprice().trim();
                            BigDecimal sjprice2 = new BigDecimal(sjprice1);
                            BigDecimal sjtotal = sjnum1.multiply(sjprice2).setScale(2, BigDecimal.ROUND_HALF_UP);
                            BigDecimal rktotal1 = rktotal.add(sjtotal).setScale(2, BigDecimal.ROUND_HALF_UP);
                            ;
                            BigDecimal total = kctotal.add(sjtotal).setScale(2, BigDecimal.ROUND_HALF_UP);
                            ;
                            d1.setBqinmoney(rktotal1.toString());
                            d1.setBqendmoney(total.toString());
                            BigDecimal sl1 = kcnumber2.add(sjnum1).setScale(2, BigDecimal.ROUND_HALF_UP);
                            ;
                            BigDecimal pjprice1 = total.divide(sl1, 2, BigDecimal.ROUND_HALF_UP);
                            pjprice = pjprice1.toString();
                            sl = sl1.toString();
                        } else {
                            BigDecimal syrknum2 = syrknum1.subtract(sjnum1).setScale(2, BigDecimal.ROUND_HALF_UP);
                            syrknum3 = syrknum2.toString();
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
                        }

                        d1.setWzname(rklist.get(i).getWzname().trim());
                        d1.setWzcode(rklist.get(i).getWzcode().trim());
                        d1.setModelspcification(sparepartcodelist.get(0).getModelspecification().trim());
                        d1.setUnit(sparepartcodelist.get(0).getUnit().trim());
                        d1.setPrice(pjprice);
                        d1.setStockcode(storehousecode);
                        d1.setStockname(storehousename);
                        d1.setUpdaterid(loginUser.getId());
                        d1.setUpdater(loginUser.getUsername());
                        d1.setUpdatetime(timeString);

                        String in = d1.getBqin().trim();
                        String inmoney = d1.getBqinmoney().trim();
                        BigDecimal in1 = new BigDecimal(in);
                        BigDecimal innew = in1.add(sjnum1).setScale(2, BigDecimal.ROUND_HALF_UP);
                        d1.setBqin(innew.toString());
                        String end = d1.getBqend().trim();
                        BigDecimal end1 = new BigDecimal(end);
                        BigDecimal endnew = end1.add(sjnum1).setScale(2, BigDecimal.ROUND_HALF_UP);
                        d1.setBqend(endnew.toString());
                        stockService.updateById(d1);
                        ht.setSyrksum(syrknum3);
                        ht.setUpdaterid(loginUser.getId());
                        ht.setUpdater(loginUser.getUsername());
                        ht.setUpdatetime(timeString);
                        contractGoodsService.updateById(ht);

                    }

                }
                t.setRkstatus("已入库");
                t.setUpdaterid(loginUser.getId());
                t.setUpdater(loginUser.getUsername());
                t.setUpdatetime(timeString);
                warehousingService.updateById(t);
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(t.getId());
                logs.setName("com.hchenpan.controller.WarehousingController.rk2");
                logs.setParams("com.hchenpan.pojo.Warehousing类");
                logs.setDescription("采购入库");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(t));
                logs.setCreatorid(loginUser.getId());
                logs.setCreator(loginUser.getUsername());
                logs.setCreatetime(timeString);
                logs.setOldcontent(oldcontent);
                logs.setRealname(loginUser.getRealname());
                logs.setUpdater(loginUser.getUsername());
                logs.setUpdatetime(timeString);
                logs.setId(getUUID());
                logsService.insert(logs);
                return SUCCESS;

            }
        }
        return ERROR;

    }
}