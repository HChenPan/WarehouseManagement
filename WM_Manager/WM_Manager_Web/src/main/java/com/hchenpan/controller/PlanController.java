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

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.PlanController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class PlanController extends BaseController {
    private final PlanService planService;
    private final ApprovalRecordService approvalRecordService;
    private final PlanlistService planlistService;
    private final LogsService logsService;
    private final WorkFlowService workFlowService;
    private final UserService userService;
    private final SptypespLevelService sptypespLevelService;
    private final DictionaryschildService dictionaryschildService;

    @Autowired
    public PlanController(PlanService planService, ApprovalRecordService approvalRecordService, PlanlistService planlistService, LogsService logsService, WorkFlowService workFlowService, UserService userService, SptypespLevelService sptypespLevelService, DictionaryschildService dictionaryschildService) {
        this.planService = planService;
        this.approvalRecordService = approvalRecordService;
        this.planlistService = planlistService;
        this.logsService = logsService;
        this.workFlowService = workFlowService;
        this.userService = userService;
        this.sptypespLevelService = sptypespLevelService;
        this.dictionaryschildService = dictionaryschildService;
    }

    @GetMapping("/plan")
    public String plan() {
        return View("/Buymanage/plan");
    }

    @GetMapping("/planapproval")
    public String planapproval() {
        return View("/Buymanage/planapproval");
    }

    /**
     * 功能:计划需求大类维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/plan/search")
    public String search(Plan plan) {
        Page<Plan> page = getPage();
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        EntityWrapper<Plan> ew = new EntityWrapper<>();
        ew.eq("flag", "E");
        ew.eq("userid", loginUser.getId());
        if (StringUtil.notTrimEmpty(plan.getPlancode())) {
            ew.like("plancode", plan.getPlancode(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(plan.getDatetime1())) {
            ew.ge("sbdate", plan.getDatetime1());
        }
        if (StringUtil.notTrimEmpty(plan.getDatetime2())) {
            ew.le("sbdate", plan.getDatetime2());
        }
        return jsonPage(planService.selectPage(page, ew));
    }

    /**
     * 功能:取单条数据编辑
     */
    @ResponseBody
    @PostMapping("/plan/getallbyid")
    public String getallbyid(Plan plan) {
        return GetGsonString(planService.selectById(plan.getId()));
    }

    /**
     * 功能:提供查询计划审批列表的分页数据
     */
    @ResponseBody
    @PostMapping("/plan/searchsplist")
    public String searchsplist() {


        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        //获取计划下的所有审批类型
        String sptypecodes = dictionaryschildService.getsptypecodesbynote("PLAN");
        //获取当前登录用户的审批节点
        List<CommboxList> cList = sptypespLevelService.getusersplevelnode(sptypecodes, loginUser.getId());

        //获取固定资产审批列表
        Page<Plan> page = getPage();
        EntityWrapper<Plan> ew = new EntityWrapper<>();
        StringBuilder sql = new StringBuilder();

//        if (cList.size() > 0) {
//
//            sql.append("( plantype ='").append(cList.get(0).getId()).append("'and spcode = '").append(cList.get(0).getText()).append("')");
//        }
        sql.append(" AND (  ");
        for (int i = 1; i < cList.size(); i++) {
            CommboxList commboxList = cList.get(i);
            sql.append("  ( plantype ='").append(commboxList.getId()).append("'and spcode = '").append(commboxList.getText()).append("') OR");

        }
        sql.append(" flag='E' )");
        System.out.println(sql);

        ew.where("flag = {0}" + sql.toString(), "E");

        return GetGsonString(planService.selectPage(page, ew));


    }

    /**
     * 功能：审批操作：同意 S 退回 F
     */
    @ResponseBody
    @GetMapping("/plan/approve")
    public String approve(Plan plan) {
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

        a.setSpid(plan.getId());
        a.setSptypecode(plan.getPlantype());
        a.setSpnodecode(plan.getSpcode());
        a.setSpadvice(plan.getSpadvice());
        a.setSpresult(plan.getSpresult());
        a.setSpuserid(plan.getSpuserid());
        a.setSpuser(plan.getSpuser());
        a.setSptime(timeString);
        approvalRecordService.insert(a);

        //更改计划表
        String spnodecode = plan.getSpcode();
        String money = plan.getPlanmoney();
        String spresult = plan.getSpresult();
        String spstatus = "";
        //获取下一级审批节点
        String nextspnode = workFlowService.getnextspnode(plan.getPlantype(), spnodecode, money, spresult);
        System.out.println(nextspnode);
        //获取审批节点名称
        if ("同意".equals(spresult)) {
            spstatus = dictionaryschildService.getSpnodenamebycode("SPJB", nextspnode);
        } else {
            spstatus = dictionaryschildService.getSpnodenamebycode("SPJB", spnodecode) + "退回";
        }
        //判断是否更新计划plan表的审批节点、审批状态
        //查询该级审批对应的审批人员数
        int spusernum = sptypespLevelService.getspusernum(plan.getPlantype(), spnodecode);
        //查询审批记录表中该级别同意的审批记录数
        int tyspnum = sptypespLevelService.gettyspnum(plan.getPlantype(), spnodecode, plan.getId());
        plan.setSpcode(nextspnode);
        plan.setSpstatus(spstatus);
        if ("退回".equals(spresult)) {
            planService.updateById(plan);
        } else {
            if (spusernum == tyspnum) {
                planService.updateById(plan);
            }
        }
        //审批结束后更改plan表中的审批结束时间
        if ("99".equals(nextspnode)) {
            plan.setSpjsdate(timeString);
            planService.updateById(plan);
        }
        return SUCCESS;
    }

    @ResponseBody
    @PostMapping("/plan/getplanlist")
    public String getplanlist() {
//        List<Plan> planlist = planservice.getplanlist();
        return GetGsonString(planService.selectList(new EntityWrapper<Plan>().eq("flag", "E").eq("spcode", "99").orderBy("updatetime")));

    }

    @ResponseBody
    @PostMapping("/plan/getplanbyuserid")
    public String getplanbyuserid() {
        /*通用字段赋值*/
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        return GetGsonString(planService.selectList(new EntityWrapper<Plan>()
                .eq("userid", loginUser.getId())
                .eq("flag", "E")
                .eq("spcode", "99")
                .orderBy("updatetime", false)));
    }

    /**
     * 功能：新增计划需求大类
     */
    @ResponseBody
    @PostMapping("/plan/create")
    public String create(Plan plan) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            plan.setId(getUUID());
            plan.setCreatorid(loginUser.getId());
            plan.setCreator(loginUser.getUsername());
            plan.setCreatetime(timeString);
            plan.setUpdaterid(loginUser.getId());
            plan.setUpdater(loginUser.getUsername());
            plan.setUpdatetime(timeString);
            plan.setFlag("E");

            String plantype = plan.getPlantype().trim();
            String plancode1 = planService.createplancode(plantype);
            String plancode = plantype + plancode1;
            plan.setUserid(loginUser.getId());
            plan.setPlancode(plancode);
            plan.setSpstatus("草稿");
            plan.setSpcode("00");
            plan.setPlanmoney("0");
            plan.setPlanspmoney("0");
            plan.setSbstatus("未上报");
            plan.setSqrname(loginUser.getRealname());
            plan.setSqrid(loginUser.getId());

            plan.setNote(plan.getNote().trim());
            List<Dictionaryschild> list = dictionaryschildService.getdchildlistbydecode("BZ");
            plan.setZjname(list.get(0).getName().trim());
            plan.setZjcode(list.get(0).getCode().trim());
            planService.insert(plan);

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(plan.getId());
            logs.setName("com.hchenpan.controller.PlanController.create");
            logs.setParams("com.hchenpan.pojo.Plan类");
            logs.setDescription("新增需求申请");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(plan));
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
     * 功能：更新计划需求大类
     */
    @ResponseBody
    @PostMapping("/plan/update")
    public String update(Plan plan) {
        if (checkuser()) {
            Plan old = planService.selectById(plan.getId());
            String oldcontent = GetGsonString(old);
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            plan.setUpdaterid(loginUser.getId());
            plan.setUpdater(loginUser.getUsername());
            plan.setUpdatetime(timeString);

            String oldplantype = old.getPlantype().trim();
            if (!oldplantype.equals(plan.getPlantype())) {
                String plancode1 = planService.createplancode(plan.getPlantype());
                String plancode = plan.getPlantype() + plancode1;
                plan.setPlancode(plancode);
            }

            plan.setSqrname(loginUser.getRealname());
            plan.setSqrid(loginUser.getId());

            planService.updateById(plan);
            planlistService.updateForSet("planname='" + plan.getPlanname() + "' ,plancode='" + plan.getPlancode() + "' ,plantype='" + plan.getPlantype() + "'", new EntityWrapper<Planlist>().eq("plancodeid", plan.getId()));

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(plan.getId());
            logs.setName("com.hchenpan.controller.PlanController.update");
            logs.setParams("com.hchenpan.pojo.Plan类");
            logs.setDescription("修改需求申请大类");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(plan));
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
     * 功能:删除计划需求大类
     */
    @ResponseBody
    @GetMapping("/plan/delete")
    public String delete(Plan plan) {
        if (checkuser()) {
            Plan old = planService.selectById(plan.getId());
            String oldcontent = GetGsonString(old);
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            plan.setUpdaterid(loginUser.getId());
            plan.setUpdater(loginUser.getUsername());
            plan.setUpdatetime(timeString);
            plan.setFlag("D");
            planlistService.delete(new EntityWrapper<Planlist>().eq("plancodeid", plan.getId()));
            planService.deleteById(plan.getId());
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(plan.getId());
            logs.setName("com.hchenpan.controller.PlanController.update");
            logs.setParams("com.hchenpan.pojo.Plan类");
            logs.setDescription("删除需求申请大类");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(plan));
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
    @GetMapping("/plan/send")
    public String send(Plan plan) {
        if (checkuser()) {
            Plan old = planService.selectById(plan.getId());
            String oldcontent = GetGsonString(old);

            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            old.setUpdaterid(loginUser.getId());
            old.setUpdater(loginUser.getUsername());
            old.setUpdatetime(timeString);
            old.setSpcode("11");
            old.setSpstatus("一级审批");
            old.setSbstatus("已上报");
            planService.updateById(old);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(plan.getId());
            logs.setName("com.hchenpan.controller.PlanController.update");
            logs.setParams("com.hchenpan.pojo.Plan类");
            logs.setDescription("上报需求申请");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(plan));
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