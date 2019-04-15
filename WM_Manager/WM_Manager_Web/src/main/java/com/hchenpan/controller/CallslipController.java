package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hchenpan.common.BaseController;
import com.hchenpan.model.CommboxList;
import com.hchenpan.pojo.*;
import com.hchenpan.service.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.CallslipController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class CallslipController extends BaseController {
    private final CallslipService callslipService;
    private final CallslipGoodsService callslipGoodsService;
    private final LogsService logsService;
    private final UserService userService;
    private final StockService stockService;
    private final DictionaryschildService dictionaryschildService;
    private final ApprovalRecordService approvalRecordService;
    private final WorkFlowService workFlowService;
    private final SptypespLevelService sptypespLevelService;

    @Autowired
    public CallslipController(CallslipService callslipService, CallslipGoodsService callslipGoodsService, LogsService logsService, UserService userService, StockService stockService, DictionaryschildService dictionaryschildService, ApprovalRecordService approvalRecordService, WorkFlowService workFlowService, SptypespLevelService sptypespLevelService) {
        this.callslipService = callslipService;
        this.callslipGoodsService = callslipGoodsService;
        this.logsService = logsService;
        this.userService = userService;
        this.stockService = stockService;
        this.dictionaryschildService = dictionaryschildService;
        this.approvalRecordService = approvalRecordService;
        this.workFlowService = workFlowService;
        this.sptypespLevelService = sptypespLevelService;
    }

    @GetMapping("/callslip")
    public String callslip() {
        return View("/reserve/callslip");
    }

    @GetMapping("/callslipapproval")
    public String callslipapproval() {
        return View("/reserve/callslipapproval");
    }

    @GetMapping("/callslipout")
    public String callslipout() {
        return View("/reserve/callslipout");
    }

    /**
     * 功能:获得已完成出库的领料单
     */
    @ResponseBody
    @PostMapping("/callslip/getcallsliplist")
    public String getcallsliplist() {
        String tkrid = request.getParameter("tkrid");
        return GetGsonString(callslipService.getallOrderByApplication(tkrid));
    }

    /**
     * 功能:提供所有采购入库信息,供下拉
     */
    @ResponseBody
    @PostMapping("/callslip/getallOrderByApplication")
    public String getallOrderByApplication() {
        String tkrid = request.getParameter("tkrid");
        return GetGsonString(callslipService.getallOrderByApplication(tkrid));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/callslip/getbyid")
    public String getbyid(Callslip callslip) {
        return GetGsonString(callslipService.selectById(callslip.getId()));
    }

    /**
     * 功能：新增采购入库
     */
    @ResponseBody
    @PostMapping("/callslip/create")
    public String create(Callslip callslip) {
        if (checkuser()) {
            //非空时进入
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            callslip.setId(getUUID());
            callslip.setCreatorid(loginUser.getId());
            callslip.setCreator(loginUser.getUsername());
            callslip.setCreatetime(timeString);
            callslip.setUpdaterid(loginUser.getId());
            callslip.setUpdater(loginUser.getUsername());
            callslip.setUpdatetime(timeString);
            callslip.setFlag("E");

            callslip.setRealname(loginUser.getRealname());
            callslip.setUserid(loginUser.getId());
            callslip.setStatus("草稿");
            callslip.setSpcode("00");
            callslip.setMoney("1.00");
            String htInitials = callslip.getLlcode();

            String numberid = callslipService.createnumberid(htInitials);
            callslip.setCallslipcode(htInitials + numberid);

            callslipService.insert(callslip);


            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(callslip.getId());
            logs.setName("com.hchenpan.controller.CallslipController.create");
            logs.setParams("com.hchenpan.pojo.Callslip类");
            logs.setDescription("录入领料单信息");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(callslip));
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
     * 功能：领料单列表
     */
    @ResponseBody
    @PostMapping("/callslip/listck")
    public String listck(Callslip m) {
        /*通用字段赋值*/
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        Page<Map<String, Object>> page = getPage();
        Map<String, Object> params = new HashMap<>(10);
        if (StringUtils.isNotEmpty(m.getCallslipcode())) {
            params.put("callslipcode", m.getCallslipcode());
        }
        if (StringUtils.isNotEmpty(m.getCallsliptype())) {
            params.put("callsliptype", m.getCallsliptype());
        }
        if (StringUtils.isNotEmpty(m.getDatetime1())) {
            params.put("datetime1", m.getDatetime1());
        }
        if (StringUtils.isNotEmpty(m.getDatetime2())) {
            params.put("datetime2", m.getDatetime2());
        }
        params.put("userid", loginUser.getId());
        return jsonPage(callslipService.selectPagelistck(page, params));
    }

    /**
     * 功能：领料单列表
     */
    @ResponseBody
    @PostMapping("/callslip/list")
    public String list(Callslip m) {
        /*通用字段赋值*/
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        Page<Map<String, Object>> page = getPage();
        Map<String, Object> params = new HashMap<>(10);
        if (StringUtils.isNotEmpty(m.getCallslipcode())) {
            params.put("callslipcode", m.getCallslipcode());
        }
        if (StringUtils.isNotEmpty(m.getCallsliptype())) {
            params.put("callsliptype", m.getCallsliptype());
        }
        if (StringUtils.isNotEmpty(m.getDatetime1())) {
            params.put("datetime1", m.getDatetime1());
        }
        if (StringUtils.isNotEmpty(m.getDatetime2())) {
            params.put("datetime2", m.getDatetime2());
        }
        params.put("userid", loginUser.getId());
        return jsonPage(callslipService.selectPagelist(page, params));
    }

    /**
     * 功能：更新采购入库
     */
    @ResponseBody
    @PostMapping("/callslip/update")
    public String update(Callslip callslip) {
        if (checkuser()) {
            //非空时进入
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            Callslip m = callslipService.selectById(callslip.getId());
            String oldcontent = GetGsonString(m);
            callslip.setUpdaterid(loginUser.getId());
            callslip.setUpdater(loginUser.getUsername());
            callslip.setUpdatetime(timeString);

            callslipService.updateById(callslip);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(callslip.getId());
            logs.setName("com.hchenpan.controller.CallslipController.update");
            logs.setParams("com.hchenpan.pojo.Callslip类");
            logs.setDescription("修改领料单基本信息");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(callslip));
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

    /**
     * 功能:删除采购入库
     */
    @ResponseBody
    @GetMapping("/callslip/delete")
    public String delete(Callslip callslip) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            Callslip m = callslipService.selectById(callslip.getId());
            String oldcontent = GetGsonString(m);
            callslip.setUpdaterid(loginUser.getId());
            callslip.setUpdater(loginUser.getUsername());
            callslip.setUpdatetime(timeString);
            callslip.setFlag("D");
            callslip.setStatus("人工删除");
            callslipService.deleteById(callslip);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(callslip.getId());
            logs.setName("com.hchenpan.controller.CallslipController.delete");
            logs.setParams("com.hchenpan.pojo.Callslip类");
            logs.setDescription("删除领料单");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(callslip));
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

    /**
     * 功能:删除采购入库
     */
    @ResponseBody
    @PostMapping("/callslip/send")
    public String send(Callslip callslip) {
        if (checkuser()) {
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            Callslip t = callslipService.selectById(callslip.getId());
            String oldcontent = GetGsonString(t);
            t.setStatus("一级审批");
            t.setSpcode("11");
            t.setUpdaterid(loginUser.getId());
            t.setUpdater(loginUser.getUsername());
            t.setUpdatetime(timeString);

            callslipService.updateById(t);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(t.getId());
            logs.setName("com.hchenpan.controller.CallslipController.delete");
            logs.setParams("com.hchenpan.pojo.Callslip类");
            logs.setDescription("领料单提交");
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

        return ERROR;
    }

    /**
     * 功能:提供查询计划审批列表的分页数据
     */
    @ResponseBody
    @PostMapping("/callslip/searchsplist")
    public String searchsplist() {
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        //获取计划下的所有审批类型
        String sptypecodes = dictionaryschildService.getsptypecodesbynote("BILL");
        //获取当前登录用户的审批节点
        List<CommboxList> clist = sptypespLevelService.getusersplevelnode(sptypecodes, loginUser.getId());
        //获取固定资产审批列表

        Page<Map<String, Object>> page = getPage();
        StringBuilder rules = new StringBuilder(" where c.flag = 'E' ");
        if (clist.size() > 0) {
            rules.append(" and ((c.llcode ='").append(clist.get(0).getId()).append("' and c.spcode = '").append(clist.get(0).getText()).append("') ");
        }
        for (int j = 1; j < clist.size(); j++) {
            rules.append(" or (c.llcode ='").append(clist.get(j).getId()).append("' and c.spcode = '").append(clist.get(j).getText()).append("') ");
        }
        if (clist.size() > 0) {
            rules.append(")");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.id,d.name,c.applydate,e.name callsliptype,c.createtime,g.name department,c.flag,c.note,c.projectname,c.projectno,c.realname,c.storehouse,c.updatetime,c.userid,c.callslipcode,f.stockname,c.status,c.outtime,c.outusername,c.money ");
        sql.append("FROM wm_callslip c LEFT JOIN wm_dictionaryschild d ON d.id = c.application\tLEFT JOIN wm_dictionaryschild e ON e.id = c.callsliptype LEFT JOIN wm_warehousenum f ON c.storehouse = f.stockcode LEFT JOIN wm_department g ON c.department = g.id ");
        sql.append(rules);
        return jsonPage(callslipService.selectSPlist(page, sql.toString()));
    }

    /**
     * 功能：审批操作：同意 S 退回 F
     */
    @ResponseBody
    @GetMapping("/callslip/approve")
    public String approve(Callslip callslip) {
        //插入审批记录
        ApprovalRecord a = new ApprovalRecord();
        /*通用字段赋值*/
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        String timeString = GetCurrentTime();
        a.setId(getUUID());
        a.setCreatorid(loginUser.getId());
        a.setCreator(loginUser.getUsername());
        a.setCreatetime(timeString);
        a.setUpdaterid(loginUser.getId());
        a.setUpdater(loginUser.getUsername());
        a.setUpdatetime(timeString);

        a.setSpid(callslip.getId());
        a.setSptypecode(callslip.getLlcode());
        a.setSpnodecode(callslip.getSpcode());
        a.setSpadvice(callslip.getSpadvice());
        a.setSpresult(callslip.getSpresult());
        a.setSpuserid(callslip.getSpuserid());
        a.setSpuser(callslip.getSpuser());
        a.setSptime(timeString);
        approvalRecordService.insert(a);

        //更改计划表
        String spnodecode = callslip.getSpcode();
        String money = callslip.getMoney();
        String spresult = callslip.getSpresult();
        String spstatus = "";
        //获取下一级审批节点
        String nextspnode = workFlowService.getnextspnode(callslip.getLlcode(), spnodecode, money, spresult);
        System.out.println(nextspnode);
        //获取审批节点名称
        if ("同意".equals(spresult)) {
            spstatus = dictionaryschildService.getSpnodenamebycode("SPJB", nextspnode);
        } else {
            spstatus = dictionaryschildService.getSpnodenamebycode("SPJB", spnodecode) + "退回";
        }
        //判断是否更新计划plan表的审批节点、审批状态
        //查询该级审批对应的审批人员数
        int spusernum = sptypespLevelService.getspusernum(callslip.getLlcode(), spnodecode);
        //查询审批记录表中该级别同意的审批记录数
        int tyspnum = sptypespLevelService.gettyspnum(callslip.getLlcode(), spnodecode, callslip.getId());
        callslip.setSpcode(nextspnode);
        callslip.setStatus(spstatus);
        if ("退回".equals(spresult)) {
            callslipService.updateById(callslip);
        } else {
            if (spusernum == tyspnum) {
                callslipService.updateById(callslip);
            }
        }
        //审批结束后更改plan表中的审批结束时间
        if ("99".equals(nextspnode)) {
            callslip.setSpjsdate(timeString);
            callslipService.updateById(callslip);
        }
        return SUCCESS;
    }

    /**
     * 领料单物资出库
     */
    @ResponseBody
    @GetMapping("/callslip/out")
    public String out(Callslip callslip) {
        if (checkuser()) {
            //非空时进入
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            Callslip m = callslipService.selectById(callslip.getId());

            String status = callslipService.getstatus(m.getCallslipcode());

            List<CallslipGoods> goodslist = callslipGoodsService.getwzlist(m.getCallslipcode());

            //循环比较库存数量和领料单数量
            int k = 0;
            int j = 0;
            String flag = "";
            for (int i = 0; i < goodslist.size(); i++) {
                String kcnum = stockService.getkcnum(goodslist.get(i).getWzcode(), goodslist.get(i).getStockcode());
                BigDecimal kcnum1 = new BigDecimal(kcnum);
                BigDecimal sum1 = new BigDecimal(goodslist.get(i).getSum());
                //						  	result小于0表示库存不足
                int result = kcnum1.compareTo(sum1);
                if ("0.00".equals(sum1.toString())) {
                    j = j + 1;
                    flag += goodslist.get(i).getWzname() + "，";
                }
                if (result < 0) {
                    k = k + 1;
                    flag += goodslist.get(i).getWzname() + "，";
                }
            }
            if (k == 0 & j == 0) {
                // 循环计算一个物资的单价、总价并反填库存
                for (int i = 0; i < goodslist.size(); i++) {
                    CallslipGoods callslipgoods = callslipGoodsService.selectOne(new EntityWrapper<CallslipGoods>()
                            .eq("wzcode", goodslist.get(i).getWzcode())
                            .eq("stockcode", goodslist.get(i).getStockcode())
                            .eq("callslipcode", goodslist.get(i).getCallslipcode())
                            .eq("flag", "E"));
                    //获取实时移动平均价
                    String price = stockService.getprice(goodslist.get(i).getWzcode(), goodslist.get(i).getStockcode());
                    String sum = goodslist.get(i).getSum();
                    BigDecimal price1 = new BigDecimal(price);
                    BigDecimal sum1 = new BigDecimal(sum);
                    //单个物资总价
                    BigDecimal summoney = price1.multiply(sum1).setScale(2);
                    //向领料单物资表反填移动平均价和单个总价
                    callslipgoods.setPrice(price);
                    callslipgoods.setSummoney(summoney.toString());
                    //获取单个库存物资对象
                    Stock stock = stockService.selectOne(new EntityWrapper<Stock>()
                            .eq("wzcode", goodslist.get(i).getWzcode())
                            .eq("stockcode", goodslist.get(i).getStockcode())
                            .isNull("tranflag")
                            .isNull("stockyearmon")
                    );
                    //获取本期期末数量
                    String oldbqend = stock.getBqend();
                    BigDecimal oldbqend1 = new BigDecimal(oldbqend);
                    //本期期末数-要出库的数量=新的本期出库数
                    BigDecimal newbqend = oldbqend1.subtract(sum1).setScale(2);
                    stock.setBqend(newbqend.toString());
                    //获取本期出库总合
                    String oldbqout = stock.getBqout();
                    BigDecimal oldbqout1 = new BigDecimal(oldbqout);
                    //本期出库总合+要出库的数量=新的本期出库总合
                    BigDecimal newbqout = oldbqout1.add(sum1).setScale(2);
                    stock.setBqout(newbqout.toString());
                    //判断本期期末数量是否为0，如果为0，本期期末金额直接为0、单价为0
                    if (newbqend.toString().equals("0.00")) {
                        stock.setBqendmoney("0.00");
                        stock.setPrice("0.00");
                    } else {
                        String oldbqendmoney = stock.getBqendmoney();
                        //获取库存表中本期期末金额
                        BigDecimal oldbqendmoney1 = new BigDecimal(oldbqendmoney);
                        //库存表中本期期末金额-出库物资总额=新的本期期末金额
                        BigDecimal newbqendmoney = oldbqendmoney1.subtract(summoney).setScale(2);
                        stock.setBqendmoney(newbqendmoney.toString());
                    }
                    //获取本期出库金额
                    String oldbqoutmoney = stock.getBqoutmoney();
                    BigDecimal oldbqoutmoney1 = new BigDecimal(oldbqoutmoney);
                    //本期出库金额+物资总价=新的出库金额
                    BigDecimal newbqoutmoney = oldbqoutmoney1.add(summoney).setScale(2);
                    stock.setBqoutmoney(newbqoutmoney.toString());

                    callslipGoodsService.updateById(callslipgoods);
                    stockService.updateById(stock);
                }
                m.setStatus("已出库");
                m.setOuttime(timeString);
                m.setOutuserid(loginUser.getId());
                m.setOutusername(loginUser.getRealname());
                callslipService.updateById(m);
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(m.getId());
                logs.setName("com.hchenpan.controller.CallslipController.out");
                logs.setParams("com.hchenpan.pojo.Callslip类");
                logs.setDescription("领料单出库");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(m));
                /* 修改，需要保存修改前后的数据 */
                logs.setOldcontent("数据太多，不予保存");
                logs.setCreatorid(loginUser.getId());
                logs.setCreator(loginUser.getUsername());
                logs.setCreatetime(timeString);
                logs.setUpdater(loginUser.getUsername());
                logs.setRealname(loginUser.getRealname());
                logs.setUpdatetime(timeString);
                logs.setId(getUUID());
                logsService.insert(logs);
                return SUCCESS;

            } else if (k != 0) {
                if (!"success".equals(flag) && !"error".equals(flag) && !"正在出库".equals(flag)) {
                    flag = flag.substring(0, flag.length() - 1);
                    flag += "共" + k + "个物资库存不足，无法发货！";
                }
            } else if (j != 0) {
                if (!"success".equals(flag) && !"error".equals(flag) && !"正在出库".equals(flag)) {
                    flag = flag.substring(0, flag.length() - 1);
                    flag += "共" + j + "个物资输入数量为0，请调整后发货！";
                }
            }
            return flag;

        }


        return ERROR;
    }

}