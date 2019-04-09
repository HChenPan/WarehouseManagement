package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.*;
import com.hchenpan.service.*;
import com.hchenpan.service.impl.DictionaryschildServiceImpl;
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
 * ClassName : com.hchenpan.controller.TransferlistController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class TransferlistController extends BaseController {
    private final TransferlistService transferlistService;
    private final LogsService logsService;
    private final UserService userService;
    private final ApplytransferService applytransferService;
    private final StockService stockService;
    private final SparepartCodeService sparepartCodeService;
    private final WarehouseNumUserService warehouseNumUserService;
    private final WarehouseNumService warehouseNumService;
    private final DictionaryschildServiceImpl dictionaryschildService;

    @Autowired
    public TransferlistController(TransferlistService transferlistService, LogsService logsService, UserService userService, ApplytransferService applytransferService, StockService stockService, SparepartCodeService sparepartCodeService, WarehouseNumUserService warehouseNumUserService, WarehouseNumService warehouseNumService, DictionaryschildServiceImpl dictionaryschildService) {
        this.transferlistService = transferlistService;
        this.logsService = logsService;
        this.userService = userService;
        this.applytransferService = applytransferService;
        this.stockService = stockService;
        this.sparepartCodeService = sparepartCodeService;
        this.warehouseNumUserService = warehouseNumUserService;
        this.warehouseNumService = warehouseNumService;
        this.dictionaryschildService = dictionaryschildService;
    }


    @GetMapping("/transfer")
    public String transfer() {
        return View("/innerdeal/transfer");
    }

    /**
     * 功能:任务调拨单明细维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/transferlist/search")
    public String search(Transferlist transferlist) {
        if (StringUtil.notTrimEmpty(transferlist.getApplytransfercodeid())) {
            Page<Transferlist> page = getPage();
            return jsonPage(transferlistService.selectPage(page, new EntityWrapper<Transferlist>().eq("flag", "E").eq("applytransfercodeid", transferlist.getApplytransfercodeid())));

        }
        return ERROR;
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/transferlist/getallbyid")
    public String getallbyid(Transferlist transferlist) {
        return GetGsonString(transferlistService.selectById(transferlist.getId()));
    }

    /**
     * 功能：新增任务调拨单明细
     */
    @ResponseBody
    @PostMapping("/transferlist/create")
    public String create() {
        if (checkuser()) {
            String arrayList = request.getParameter("arrayList");
            String applytransfercodeid = request.getParameter("applytransfercodeid");
            //将 arrayList 字符串转换成 json 对象
            //将 json 对象转换成 stock 集合
            List<Stock> list = null;
            //获取调拨单 信息
            Applytransfer applyTransfer = applytransferService.selectById(applytransfercodeid);
            if ("已申请".equals(applyTransfer.getSbstatus()) || "已发货".equals(applyTransfer.getSbstatus())) {
                return "已申请";
            } else {
                //遍历 选中的仓库
                for (Stock stock : list) {
                    WarehouseNum warehouseNum = warehouseNumService.selectOne(new EntityWrapper<WarehouseNum>().eq("flag", "E").eq("stockname", stock.getStockname()));
                    SparepartCode sparepartCode = sparepartCodeService.selectOne(new EntityWrapper<SparepartCode>().eq("code", stock.getWzcode()));
                    //判断物资编码对应的 明细是否已存在
                    if (transferlistService.selectCount(new EntityWrapper<Transferlist>().eq("applytransfercodeid", applytransfercodeid).eq("dcckcode", warehouseNum.getStockcode()).eq("wzcode", sparepartCode.getCode()).eq("flag", "E")) > 0) {
                        return "仓库  " + warehouseNum.getStockname() + " 物资编码 " + sparepartCode.getCode() + " 的物资明细已存在，请修改";
                    }
                }
                for (Stock stock : list) {
                    WarehouseNum warehouseNum = warehouseNumService.selectOne(new EntityWrapper<WarehouseNum>().eq("flag", "E").eq("stockname", stock.getStockname()));
                    SparepartCode sparepartCode = sparepartCodeService.selectOne(new EntityWrapper<SparepartCode>().eq("code", stock.getWzcode()));
                    Transferlist TlNew = new Transferlist();
                    /*通用字段赋值*/
                    User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                    String timeString = GetCurrentTime();
                    TlNew.setId(getUUID());
                    TlNew.setFlag("E");
                    TlNew.setCreatorid(loginUser.getId());
                    TlNew.setCreator(loginUser.getUsername());
                    TlNew.setCreatetime(timeString);
                    TlNew.setUpdaterid(loginUser.getId());
                    TlNew.setUpdater(loginUser.getUsername());
                    TlNew.setUpdatetime(timeString);

                    TlNew.setApplytransfercode(applyTransfer.getApplytransfercode());
                    TlNew.setApplytransfercodeid(applyTransfer.getId());
                    TlNew.setDcck(warehouseNum.getStockname());
                    TlNew.setDcckcode(warehouseNum.getStockcode());
                    TlNew.setDcckid(warehouseNum.getId());
                    TlNew.setFlag("E");
                    TlNew.setModelspcification(sparepartCode.getModelspecification());

                    TlNew.setNote("");
                    TlNew.setSbunit(applyTransfer.getSbunit());
                    TlNew.setSbunitid(applyTransfer.getSbunitid());

                    TlNew.setSqnum("0.00");
                    TlNew.setUnit(sparepartCode.getUnit());
                    TlNew.setWzcode(sparepartCode.getCode());

                    TlNew.setWzid(sparepartCode.getId());
                    TlNew.setWzname(sparepartCode.getName());
                    TlNew.setPrice(stock.getPrice());
                    TlNew.setIscorrect("N");
                    TlNew.setRealprice(stock.getPrice());
                    TlNew.setRealnum("0.00");
                    TlNew.setLjnum("0.00");

                    transferlistService.insert(TlNew);
                    //写入日志表
                    Logs logs = new Logs();
                    logs.setFlagid(TlNew.getId());
                    logs.setName("com.hchenpan.controller.TransferlistController.create");
                    logs.setParams("com.hchenpan.pojo.Transferlist类");
                    logs.setDescription("新增调拨任务单基本信息");
                    logs.setUpdaterid(loginUser.getId());
                    logs.setIpaddress(getRomoteIP());
                    logs.setOptcontent(GetGsonString(TlNew));
                    logs.setCreatorid(loginUser.getId());
                    logs.setCreator(loginUser.getUsername());
                    logs.setCreatetime(timeString);
                    logs.setRealname(loginUser.getRealname());
                    logs.setUpdater(loginUser.getUsername());
                    logs.setUpdatetime(timeString);
                    logs.setId(getUUID());
                    logsService.insert(logs);

                }
                return SUCCESS;
            }
        }
        return ERROR;
    }

    /**
     * 功能：更新任务调拨单明细
     */
    @ResponseBody
    @PostMapping("/transferlist/update")
    public String update() {
        if (checkuser()) {
            String arrayList = request.getParameter("arrayList");
            //将 arrayList 字符串转换成 json 对象
            //将 json 对象转换成 Transferlist 集合
            List<Transferlist> list = null;
            //遍历需要修改的 Transferlist 集合
            for (Transferlist TlNew : list) {
                Applytransfer applyTransfer = applytransferService.selectById(TlNew.getApplytransfercodeid());
                if ("已申请".equals(applyTransfer.getSbstatus()) || "已发货".equals(applyTransfer.getSbstatus())) {
                    return "已申请";
                } else {
                    Stock stock = stockService.selectOne(new EntityWrapper<Stock>().eq("Wzcode", TlNew.getWzcode()).eq("Dcckcode", TlNew.getDcckcode()).ge("bqend", "0").ne("bqend", "0.00").ne("bqend", "0").ge("bqend", "0.00").isNotNull("stockyearmon").isNotNull("tranflag"));
                    String kcnumNow = stock.getBqend();
                    BigDecimal kcnumNow1 = new BigDecimal(kcnumNow).setScale(2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal sqnumNew1 = new BigDecimal(TlNew.getSqnum()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    if (Integer.parseInt((kcnumNow1).subtract(sqnumNew1).setScale(0, BigDecimal.ROUND_HALF_UP).toString()) < 0) {
                        return "仓库  " + TlNew.getDcck() + " 物资编码 " + TlNew.getWzcode() + " 申请数量超出库存 " + kcnumNow1.toString();
                    }
                }
            }
            for (Transferlist TlNew : list) {

            }

        }
        return ERROR;
    }

    /**
     * 功能:删除任务调拨单明细
     */
    @ResponseBody
    @GetMapping("/transferlist/delete")
    public String delete(Transferlist transferlist) {
        return ERROR;
    }
}