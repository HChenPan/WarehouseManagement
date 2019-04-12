package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.model.CommboxList;
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
 * ClassName : com.hchenpan.controller.BuyController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class BuyController extends BaseController {
    private final BuyService buyService;
    private final BuylistService buylistService;
    private final WorkFlowService workFlowService;
    private final ApprovalRecordService approvalRecordService;
    private final PlanlistService planlistService;
    private final DictionaryschildService dictionaryschildService;
    private final SptypespLevelService sptypespLevelService;
    private final LogsService logsService;

    @Autowired
    public BuyController(BuyService buyService, BuylistService buylistService, WorkFlowService workFlowService, ApprovalRecordService approvalRecordService, PlanlistService planlistService, DictionaryschildService dictionaryschildService, SptypespLevelService sptypespLevelService, LogsService logsService, UserService userService) {
        this.buyService = buyService;
        this.buylistService = buylistService;
        this.workFlowService = workFlowService;
        this.approvalRecordService = approvalRecordService;
        this.planlistService = planlistService;
        this.dictionaryschildService = dictionaryschildService;
        this.sptypespLevelService = sptypespLevelService;
        this.logsService = logsService;
    }

    @GetMapping("/buy")
    public String buy() {
        return View("/Buymanage/buy");
    }

    @GetMapping("/buyapproval")
    public String buyapproval() {
        return View("/Buymanage/buyapproval");
    }

    /**
     * 功能:采购计划大类维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/buy/search")
    public String search(Buy buy) {
        Page<Buy> page = getPage();
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        EntityWrapper<Buy> ew = new EntityWrapper<>();
        ew.eq("flag", "E");
        ew.eq("userid", loginUser.getId());
        if (StringUtil.notTrimEmpty(buy.getBuycode())) {
            ew.like("buycode", buy.getBuycode(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(buy.getDatetime1())) {
            ew.ge("buydate", buy.getDatetime1());
        }
        if (StringUtil.notTrimEmpty(buy.getDatetime2())) {
            ew.le("buydate", buy.getDatetime2());
        }
        return jsonPage(buyService.selectPage(page, ew));
    }

    /**
     * 功能：提供查询计划审批列表的分页数据
     */
    @ResponseBody
    @PostMapping("/buy/searchsplist")
    public String searchsplist(Buy buy) {
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        //获取计划下的所有审批类型
        String sptypecodes = dictionaryschildService.getsptypecodesbynote("BUY");
        //获取当前登录用户的审批节点
        List<CommboxList> cList = sptypespLevelService.getusersplevelnode(sptypecodes, loginUser.getId());
        //获取固定资产审批列表
        Page<Buy> page = getPage();
        EntityWrapper<Buy> ew = new EntityWrapper<>();
        StringBuilder sql = new StringBuilder();
//        if (cList.size() > 0) {
//            sql.append(" AND (");
//            sql.append("( buytype ='").append(cList.get(0).getId()).append("'and spcode = '").append(cList.get(0).getText()).append("')");
//        }
        sql.append(" AND (  ");
        for (int i = 1; i < cList.size(); i++) {
            CommboxList commboxList = cList.get(i);
            sql.append("( buytype ='").append(commboxList.getId()).append("'and spcode = '").append(commboxList.getText()).append("') OR ");
        }
        sql.append(" flag='E' )");
        System.out.println(sql);
        ew.where("flag = {0}" + sql.toString(), "'E'");

        return GetGsonString(buyService.selectPage(page, ew));
    }

    /**
     * 功能：审批操作：同意 S 退回 F
     */
    @ResponseBody
    @GetMapping("/buy/approve")
    public String approve(Buy buy) {
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

        a.setSpid(buy.getId());
        a.setSptypecode(buy.getBuytype());
        a.setSpnodecode(buy.getSpcode());
        a.setSpadvice(buy.getSpadvice());
        a.setSpresult(buy.getSpresult());
        a.setSpuserid(buy.getSpuserid());
        a.setSpuser(buy.getSpuser());
        a.setSptime(timeString);
        approvalRecordService.insert(a);

        //更改计划表
        String spnodecode = buy.getSpcode();
        String money = buy.getBuysummoney();
        String spresult = buy.getSpresult();
        String spstatus = "";
        //获取下一级审批节点
        String nextspnode = workFlowService.getnextspnode(buy.getBuytype(), spnodecode, money, spresult);
        System.out.println(nextspnode);
        //获取审批节点名称
        if ("同意".equals(spresult)) {
            spstatus = dictionaryschildService.getSpnodenamebycode("SPJB", nextspnode);
        } else {
            spstatus = dictionaryschildService.getSpnodenamebycode("SPJB", spnodecode) + "退回";
        }
        //判断是否更新计划plan表的审批节点、审批状态
        //查询该级审批对应的审批人员数
        int spusernum = sptypespLevelService.getspusernum(buy.getBuytype(), spnodecode);
        //查询审批记录表中该级别同意的审批记录数
        int tyspnum = sptypespLevelService.gettyspnum(buy.getBuytype(), spnodecode, buy.getId());
        buy.setSpcode(nextspnode);
        buy.setSpstatus(spstatus);
        if ("退回".equals(spresult)) {
            buyService.updateById(buy);
        } else {
            if (spusernum == tyspnum) {
                buyService.updateById(buy);
            }
        }
        //审批结束后更改plan表中的审批结束时间
        if ("99".equals(nextspnode)) {
            buy.setSpjsdate(timeString);
            buyService.updateById(buy);
        }
        return SUCCESS;
    }

    /**
     * 功能：计划号获取下拉
     * 数据列表JSON 通过审核的计划号
     */
    @ResponseBody
    @GetMapping("/buy/getbuylist")
    public String getbuylist(Buy buy) {
        return GetGsonString(buyService.selectList(new EntityWrapper<Buy>().eq("spcode", "99").eq("flag", "E").orderBy("updatetime")));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/buy/getallbyid")
    public String getallbyid(Buy buy) {
        return GetGsonString(buyService.selectById(buy.getId()));
    }

    /**
     * 功能：新增采购计划大类
     */
    @ResponseBody
    @PostMapping("/buy/create")
    public String create(Buy buy) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            buy.setId(getUUID());
            buy.setCreatorid(loginUser.getId());
            buy.setCreator(loginUser.getUsername());
            buy.setCreatetime(timeString);
            buy.setUpdaterid(loginUser.getId());
            buy.setUpdater(loginUser.getUsername());
            buy.setUpdatetime(timeString);
            buy.setFlag("E");
            String buytype = buy.getBuytype().trim();
            String buycode1 = buyService.createbuycode(buytype);
            String buycode = buytype + buycode1;
            buy.setBuycode(buycode);
            buy.setSpstatus("草稿");
            buy.setSpcode("00");
            buy.setBuysummoney("0");
            buy.setUserid(loginUser.getId().trim());
            buy.setBuysqr(loginUser.getRealname());
            buy.setBuysqrid(loginUser.getId());
            List<Dictionaryschild> list = dictionaryschildService.getdchildlistbydecode("BZ");
            buy.setZjname(list.get(0).getName().trim());
            buy.setZjcode(list.get(0).getCode().trim());

            buyService.insert(buy);

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(buy.getId());
            logs.setName("com.hchenpan.controller.BuyController.create");
            logs.setParams("com.hchenpan.pojo.Buy类");
            logs.setDescription("新增采购计划申请");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(buy));
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
     * 功能：更新采购计划大类
     */
    @ResponseBody
    @PostMapping("/buy/update")
    public String update(Buy buy) {
        if (checkuser()) {
            Buy old = buyService.selectById(buy.getId());
            String oldcontent = GetGsonString(old);
            old.setFlag("E");
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            old.setUpdaterid(loginUser.getId());
            old.setUpdater(loginUser.getUsername());
            old.setUpdatetime(timeString);
            //todo 修改明细 表
            buyService.updateById(old);

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(buy.getId());
            logs.setName("com.hchenpan.controller.BuyController.update");
            logs.setParams("com.hchenpan.pojo.Buy类");
            logs.setDescription("修改采购计划申请大类");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(buy));
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
     * 功能:删除采购计划大类
     */
    @ResponseBody
    @GetMapping("/buy/delete")
    public String delete(Buy buy) {
        if (checkuser()) {
            Buy old = buyService.selectById(buy.getId());
            String oldcontent = GetGsonString(old);
            old.setFlag("D");
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            old.setUpdaterid(loginUser.getId());
            old.setUpdater(loginUser.getUsername());
            old.setUpdatetime(timeString);
            List<Buylist> buylists = buylistService.selectList(new EntityWrapper<Buylist>().eq("buycodeid", old.getId()));
            for (Buylist buylist : buylists) {
                Planlist planlist = planlistService.selectOne(new EntityWrapper<Planlist>().eq("plancode", buylist.getPlancode()).eq("wzcode", buylist.getWzcode()).eq("flag", "E"));
                BigDecimal synum = new BigDecimal(planlist.getSynum());
                BigDecimal buynum = new BigDecimal(buylist.getBuynum());
                BigDecimal total = synum.add(buynum);
                planlist.setSynum(total.toString());
                planlistService.updateById(planlist);
            }

            buylistService.delete(new EntityWrapper<Buylist>().eq("buycodeid", old.getId()));
            buyService.deleteById(old.getId());

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(buy.getId());
            logs.setName("com.hchenpan.controller.BuyController.delete");
            logs.setParams("com.hchenpan.pojo.Buy类");
            logs.setDescription("删除需求申请大类");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(buy));
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
     * 功能:固定资产上报
     */
    @ResponseBody
    @GetMapping("/buy/send")
    public String send(Buy buy) {
        if (checkuser()) {
            Buy old = buyService.selectById(buy.getId());
            String oldcontent = GetGsonString(old);

            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            old.setUpdaterid(loginUser.getId());
            old.setUpdater(loginUser.getUsername());
            old.setUpdatetime(timeString);

            old.setSpcode("11");
            old.setSpstatus("一级审批");
            buyService.updateById(old);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(buy.getId());
            logs.setName("com.hchenpan.controller.BuyController.update");
            logs.setParams("com.hchenpan.pojo.Buy类");
            logs.setDescription("上报采购计划申请");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(buy));
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