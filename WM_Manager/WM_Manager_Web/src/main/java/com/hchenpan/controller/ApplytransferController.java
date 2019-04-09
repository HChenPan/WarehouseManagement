package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
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
 * ClassName : com.hchenpan.controller.ApplytransferController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class ApplytransferController extends BaseController {
    private final ApplytransferService applytransferService;
    private final TransferlistService transferlistService;
    private final LogsService logsService;
    private final StockService stockService;
    private final WarehouseNumUserService warehouseNumUserService;
    private final WarehouseNumService warehouseNumService;
    private final DictionaryschildServiceImpl dictionaryschildService;

    @Autowired
    public ApplytransferController(ApplytransferService applytransferService, TransferlistService transferlistService, LogsService logsService, StockService stockService, WarehouseNumUserService warehouseNumUserService, WarehouseNumService warehouseNumService, DictionaryschildServiceImpl dictionaryschildService) {
        this.applytransferService = applytransferService;
        this.transferlistService = transferlistService;
        this.logsService = logsService;
        this.stockService = stockService;
        this.warehouseNumUserService = warehouseNumUserService;
        this.warehouseNumService = warehouseNumService;
        this.dictionaryschildService = dictionaryschildService;
    }

    @GetMapping("/applytransfer")
    public String applytransfer() {
        return View("/innerdeal/applytransfer");
    }

    /**
     * 功能:任务调拨单维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/applytransfer/search")
    public String search(Applytransfer applytransfer) {
        Page<Applytransfer> page = getPage();
        EntityWrapper<Applytransfer> ew = new EntityWrapper<>();
        ew.eq("flag", "E");
        ew.ne("sbstatus", "已发货");
        if (StringUtil.notTrimEmpty(applytransfer.getApplytransfercode())) {
            ew.like("applytransfercode", applytransfer.getApplytransfercode(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(applytransfer.getDatetime1())) {
            ew.ge("sbdate", applytransfer.getDatetime1());
        }
        if (StringUtil.notTrimEmpty(applytransfer.getDatetime2())) {
            ew.le("sbdate", applytransfer.getDatetime2());
        }
        return jsonPage(applytransferService.selectPage(page, ew));
    }

    /**
     * 功能:提供所有任务调拨单信息
     */
    @ResponseBody
    @PostMapping("/applytransfer/searchsq")
    public String searchsq(Applytransfer applytransfer) {
        Page<Applytransfer> page = getPage();
        //获取满足条件的调拨单
        EntityWrapper<Applytransfer> ew = new EntityWrapper<>();
        ew.eq("flag", "E");
        ew.in("sbstatus", "已申请,已发货,正在发货");
        //获取本次登陆用户的仓库id
        List<WarehouseNumUser> warehouseNumUsers = warehouseNumUserService.selectList(new EntityWrapper<WarehouseNumUser>().eq("userid", applytransfer.getSqrid()));
        StringBuilder warehouseid = new StringBuilder();
        for (WarehouseNumUser warehouseNumUser : warehouseNumUsers) {
            warehouseid.append(warehouseNumUser.getWarehouseid()).append(",");
        }
        if (warehouseid.length() != 0) {
            String warehouseids = warehouseid.substring(0, warehouseid.length() - 1);
            System.out.println("warehouseids=" + warehouseids);
            //获取仓库id对应的仓库编码集合
            List<WarehouseNum> warehouseNumList = warehouseNumService.selectList(new EntityWrapper<WarehouseNum>().in("id", warehouseids));
            StringBuilder stockcode = new StringBuilder();
            for (WarehouseNum warehouseNum : warehouseNumList) {
                stockcode.append(warehouseNum.getStockcode()).append(",");
            }
            if (stockcode.length() != 0) {
                String stockcodes = stockcode.substring(0, stockcode.length() - 1);
                System.out.println("stockcodes = " + stockcodes);
                ew.in("dcckcode", stockcodes);
                if (StringUtil.notTrimEmpty(applytransfer.getApplytransfercode())) {
                    ew.like("applytransfercode", applytransfer.getApplytransfercode(), SqlLike.DEFAULT);
                }
                if (StringUtil.notTrimEmpty(applytransfer.getDatetime1())) {
                    ew.ge("sbdate", applytransfer.getDatetime1());
                }
                if (StringUtil.notTrimEmpty(applytransfer.getDatetime2())) {
                    ew.le("sbdate", applytransfer.getDatetime2());
                }
                return jsonPage(applytransferService.selectPage(page, ew));
            }

        }
        return ERROR;
    }

    /**
     * 功能:取单条数据编辑
     */
    @ResponseBody
    @PostMapping("/applytransfer/getallbyid")
    public String getallbyid(Applytransfer applytransfer) {
        return GetGsonString(applytransferService.selectById(applytransfer.getId()));
    }


    /**
     * 功能：新增任务调拨单
     */
    @ResponseBody
    @PostMapping("/applytransfer/create")
    public String create(Applytransfer applytransfer) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            applytransfer.setId(getUUID());
            applytransfer.setFlag("E");
            applytransfer.setCreatorid(loginUser.getId());
            applytransfer.setCreator(loginUser.getUsername());
            applytransfer.setCreatetime(timeString);
            applytransfer.setUpdaterid(loginUser.getId());
            applytransfer.setUpdater(loginUser.getUsername());
            applytransfer.setUpdatetime(timeString);
            /*设置编码*/
            applytransfer.setApplytransfercode(applytransferService.getCode());
            applytransfer.setSbstatus("草稿");
            applytransfer.setSqrid(loginUser.getId());
            applytransfer.setSqrname(loginUser.getRealname());
            List<Dictionaryschild> list = dictionaryschildService.getdchildlistbydecode("BZ");
            applytransfer.setZjname(list.get(0).getName().trim());
            applytransfer.setZjcode(list.get(0).getCode().trim());
            applytransfer.setSbmoney("0.00");
            applytransfer.setRealmoney("0.00");
            applytransferService.insert(applytransfer);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(applytransfer.getId());
            logs.setName("com.hchenpan.controller.ApplytransferController.create");
            logs.setParams("com.hchenpan.pojo.Applytransfer类");
            logs.setDescription("新增调拨任务单基本信息");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(applytransfer));
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
     * 功能：更新任务调拨单
     */
    @ResponseBody
    @PostMapping("/applytransfer/update")
    public String update(Applytransfer applytransfer) {
        if (checkuser()) {
            Applytransfer old = applytransferService.selectById(applytransfer.getId());
            String oldcontent = GetGsonString(old);
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            applytransfer.setUpdaterid(loginUser.getId());
            applytransfer.setUpdater(loginUser.getUsername());
            applytransfer.setUpdatetime(timeString);
            applytransfer.setFlag("E");
            applytransferService.updateById(applytransfer);
            System.out.println("----------------------------------------------------------");
            transferlistService.updateForSet("APPLYTRANSFERCODE = '" + applytransfer.getApplytransfercode() + "',SBUNIT = '" + applytransfer.getSbunit() + "', SBUNITID = '" + applytransfer.getSbunitid() + ",", new EntityWrapper<Transferlist>().eq("applytransfercodeid", applytransfer.getId()));
            System.out.println("----------------------------------------------------------");
            transferlistService.updateMXById(applytransfer.getId(), applytransfer.getApplytransfercode(), applytransfer.getSbunit(), applytransfer.getSbunitid());
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(applytransfer.getId());
            logs.setName("com.hchenpan.controller.ApplytransferController.update");
            logs.setParams("com.hchenpan.pojo.Applytransfer类");
            logs.setDescription("修改调拨任务单基本信息");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(applytransfer));
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
     * 功能:删除任务调拨单
     */
    @ResponseBody
    @GetMapping("/applytransfer/delete")
    public String delete(Applytransfer applytransfer) {
        if (checkuser()) {
            Applytransfer old = applytransferService.selectById(applytransfer.getId());
            String oldcontent = GetGsonString(old);
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            applytransfer.setUpdaterid(loginUser.getId());
            applytransfer.setUpdater(loginUser.getUsername());
            applytransfer.setUpdatetime(timeString);
            applytransfer.setFlag("D");
            transferlistService.delete(new EntityWrapper<Transferlist>().eq("applytransfercodeid", applytransfer.getId()));
            applytransferService.deleteById(applytransfer.getId());
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(applytransfer.getId());
            logs.setName("com.hchenpan.controller.ApplytransferController.delete");
            logs.setParams("com.hchenpan.pojo.Applytransfer类");
            logs.setDescription("删除调拨任务单基本信息");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(applytransfer));
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
     * 功能：任务调拨单 上报
     */
    @ResponseBody
    @PostMapping("/applytransfer/send")
    public String send(Applytransfer applytransfer) {
        if (checkuser()) {
            Applytransfer applytransferSend = applytransferService.selectById(applytransfer.getId());
            String oldcontent = GetGsonString(applytransferSend);
            List<Transferlist> transferlists = transferlistService.selectList(new EntityWrapper<Transferlist>().eq("applytransfercode", applytransferSend.getApplytransfercode()).eq("flag", "E"));
            if (transferlists.size() == 0) {
                return " 物资为空 请修改！ ";
            }
            for (Transferlist transferlist : transferlists) {
                if ("0".equals(transferlist.getSqnum()) || "0.00".equals(transferlist.getSqnum())) {
                    return "仓库  " + transferlist.getDcck() + " 物资编码 " + transferlist.getWzcode() + " 申请数量为 0 请修改！ ";
                }
            }
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            applytransferSend.setUpdaterid(loginUser.getId());
            applytransferSend.setUpdater(loginUser.getUsername());
            applytransferSend.setUpdatetime(timeString);
            applytransferSend.setFlag("E");
            applytransferSend.setSbstatus("已申请");
            applytransferService.updateById(applytransfer);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(applytransfer.getId());
            logs.setName("com.hchenpan.controller.ApplytransferController.send");
            logs.setParams("com.hchenpan.pojo.Applytransfer类");
            logs.setDescription("上传 内部交易 任务调拨单 ");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(applytransfer));
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
     * 功能:任务调拨单 退回申请
     */
    @ResponseBody
    @PostMapping("/applytransfer/sendback")
    public String sendback(Applytransfer applytransfer) {
        if (checkuser()) {
            Applytransfer applytransferSend = applytransferService.selectById(applytransfer.getId());
            String oldcontent = GetGsonString(applytransferSend);
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            applytransferSend.setUpdaterid(loginUser.getId());
            applytransferSend.setUpdater(loginUser.getUsername());
            applytransferSend.setUpdatetime(timeString);
            applytransferSend.setFlag("E");
            applytransferSend.setSbstatus("已退回");
            applytransferService.updateById(applytransfer);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(applytransfer.getId());
            logs.setName("com.hchenpan.controller.ApplytransferController.sendback");
            logs.setParams("com.hchenpan.pojo.Applytransfer类");
            logs.setDescription("退回 内部交易 任务调拨单 ");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(applytransfer));
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
     * 功能:任务调拨单 发货
     */
    @ResponseBody
    @PostMapping("/applytransfer/agreesend")
    public String agreesend(Applytransfer applytransfer) {
        if (checkuser()) {
            List<Transferlist> transferlists = transferlistService.selectList(new EntityWrapper<Transferlist>().like("applytransfercodeid", applytransfer.getId(), SqlLike.DEFAULT));
            //遍历发货明细 检查是否满足发货条件
            for (Transferlist transferlist : transferlists) {
                Stock stock = stockService.selectOne(new EntityWrapper<Stock>().eq("Wzcode", transferlist.getWzcode()).eq("Dcckcode", transferlist.getDcckcode()).ge("bqend", "0").ne("bqend", "0.00").ne("bqend", "0").ge("bqend", "0.00").isNotNull("stockyearmon").isNotNull("tranflag"));
                //判断库存是否为空
                if (stock != null) {
                    BigDecimal kcnumNow = new BigDecimal(stock.getBqend()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal realnum = new BigDecimal(transferlist.getRealnum()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    String kcnum = (kcnumNow).subtract(realnum).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
                    //判断库存数量是否满足发货
                    if (Integer.parseInt(kcnum) < 0) {
                        return "发货数量超出库存";
                    }
                } else {
                    return "物资编码为 " + transferlist.getWzcode() + " 的物资库存不足,请核实后重试！";
                }
            }
            //获取任务调拨信息
            Applytransfer old = applytransferService.selectById(applytransfer.getId());
            String oldcontent = GetGsonString(old);
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            old.setUpdaterid(loginUser.getId());
            old.setUpdater(loginUser.getUsername());
            old.setUpdatetime(timeString);
            old.setFlag("E");
            old.setSbstatus("已发货");


            BigDecimal price;
            BigDecimal num;
            BigDecimal wzmoneyNew = new BigDecimal(old.getSbmoney()).setScale(2, BigDecimal.ROUND_HALF_UP);

            //循环遍历 明细清单 进行发货
            for (Transferlist transferlist : transferlists) {
                String oldcontent1 = GetGsonString(transferlist);
                Stock stock = stockService.selectOne(new EntityWrapper<Stock>().eq("Wzcode", transferlist.getWzcode()).eq("Dcckcode", transferlist.getDcckcode()).ge("bqend", "0").ne("bqend", "0.00").ne("bqend", "0").ge("bqend", "0.00").isNotNull("stockyearmon").isNotNull("tranflag"));

                stockService.Cutkcnumber(transferlist.getDcckcode(), transferlist.getWzcode(), transferlist.getRealnum());
                stockService.Addkcnumber(transferlist, old);
                BigDecimal sqnum = new BigDecimal(transferlist.getSqnum()).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal ljnum = new BigDecimal(transferlist.getLjnum()).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal bcnum = new BigDecimal(transferlist.getRealnum()).setScale(2, BigDecimal.ROUND_HALF_UP);
                ljnum = ljnum.add(bcnum);
                if (sqnum.compareTo(ljnum) > 0) {
                    old.setSbstatus("正在发货");
                }
                transferlist.setLjnum(ljnum.toString());
                transferlist.setRealnum(sqnum.subtract(ljnum).toString());
                transferlistService.updateById(transferlist);
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(applytransfer.getId());
                logs.setName("com.hchenpan.controller.ApplytransferController.agreesend");
                logs.setParams("com.hchenpan.pojo.Transferlist类");
                logs.setDescription(" 内部交易 任务调拨单 发货 累计发货数量 ");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(applytransfer));
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
                price = new BigDecimal(stock.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
                num = bcnum.setScale(2, BigDecimal.ROUND_HALF_UP);
                wzmoneyNew = wzmoneyNew.add(price.multiply(num)).setScale(2, BigDecimal.ROUND_HALF_UP);
            }


            old.setSbmoney(wzmoneyNew.toString());
            old.setRealmoney(wzmoneyNew.toString());

            applytransferService.updateById(applytransfer);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(applytransfer.getId());
            logs.setName("com.hchenpan.controller.ApplytransferController.agreesend");
            logs.setParams("com.hchenpan.pojo.Applytransfer类");
            logs.setDescription("内部交易 任务调拨单 发货");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(applytransfer));
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
     * 功能:任务调拨单 发货
     */
    @ResponseBody
    @PostMapping("/applytransfer/sendfh")
    public String sendfh(Applytransfer applytransfer) {
        if (checkuser()) {
            Applytransfer old = applytransferService.selectById(applytransfer.getId());
            String oldcontent = GetGsonString(old);
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            applytransfer.setUpdaterid(loginUser.getId());
            applytransfer.setUpdater(loginUser.getUsername());
            applytransfer.setUpdatetime(timeString);
            applytransfer.setSbstatus("已发货");
            applytransferService.updateById(old);

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(applytransfer.getId());
            logs.setName("com.hchenpan.controller.ApplytransferController.sendfh");
            logs.setParams("com.hchenpan.pojo.Applytransfer类");
            logs.setDescription("内部交易 任务调拨单 结束发货");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(applytransfer));
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
}