package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.Dictionaryschild;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.User;
import com.hchenpan.pojo.WorkFlow;
import com.hchenpan.service.DictionaryschildService;
import com.hchenpan.service.LogsService;
import com.hchenpan.service.UserService;
import com.hchenpan.service.WorkFlowService;
import com.hchenpan.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.WorkFlowController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class WorkFlowController extends BaseController {
    private final WorkFlowService workFlowService;
    private final DictionaryschildService dictionaryschildService;
    private final LogsService logsService;
    private final UserService userService;

    @Autowired
    public WorkFlowController(WorkFlowService workFlowService, DictionaryschildService dictionaryschildService, LogsService logsService, UserService userService) {
        this.workFlowService = workFlowService;
        this.dictionaryschildService = dictionaryschildService;
        this.logsService = logsService;
        this.userService = userService;
    }

    @GetMapping("/workflow")
    public String workflow() {
        return View("/basicdata/workflow");
    }

    /**
     * 功能:工作流程维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/workflow/search")
    public String search(WorkFlow workflow) {
        Page<WorkFlow> page = getPage();
        EntityWrapper<WorkFlow> ew = new EntityWrapper<>();
        if (StringUtil.notTrimEmpty(workflow.getSptypecode())) {
            ew.like("sptypecode", workflow.getSptypecode(), SqlLike.DEFAULT);
        }
        Page<WorkFlow> page1 = workFlowService.selectPage(page, ew);
        List<WorkFlow> records = page1.getRecords();
        List<WorkFlow> workFlows = new ArrayList<>();
        for (WorkFlow workFlow : records) {
            workFlow.setSpnode(dictionaryschildService.selectOne(new EntityWrapper<Dictionaryschild>().eq("code", workFlow.getSptypecode())).getName());
            workFlow.setSpnodename(dictionaryschildService.selectOne(new EntityWrapper<Dictionaryschild>().eq("code", workFlow.getSpnode())).getName());
            workFlows.add(workFlow);
        }
        page.setRecords(workFlows);
        return ERROR;
    }

    /**
     * 功能:提供所有工作流程信息,供下拉
     */
    @ResponseBody
    @PostMapping("/workflow/getall")
    public String getall() {
        return ERROR;
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/workflow/getworkflowlistbyid")
    public String getworkflowlistbyid(WorkFlow workflow) {
        return GetGsonString(workFlowService.selectById(workflow.getId()));
    }

    /**
     * 功能：新增工作流程
     */
    @ResponseBody
    @PostMapping("/workflow/create")
    public String create(WorkFlow workflow) {
        if (checkuser()) {

            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            workflow.setId(getUUID());
            workflow.setCreatorid(loginUser.getId());
            workflow.setCreator(loginUser.getUsername());
            workflow.setCreatetime(timeString);
            workflow.setUpdaterid(loginUser.getId());
            workflow.setUpdater(loginUser.getUsername());
            workflow.setUpdatetime(timeString);
            workFlowService.insert(workflow);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(workflow.getId());
            logs.setName("com.hchenpan.controller.WorkFlowController.create");
            logs.setParams("com.hchenpan.pojo.WorkFlow类");
            logs.setDescription("新增工作流");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(workflow));
            /* 修改，需要保存修改前后的数据 */
            logs.setOldcontent(null);
            logs.setCreatorid(loginUser.getId());
            logs.setCreator(loginUser.getUsername());
            logs.setCreatetime(timeString);
            logs.setUpdater(loginUser.getUsername());
            logs.setRealname(loginUser.getRealname());
            logs.setUpdatetime(timeString);
            logsService.insert(logs);
            return SUCCESS;
        }
        return ERROR;
    }

    /**
     * 功能：更新工作流程
     */
    @ResponseBody
    @PostMapping("/workflow/update")
    public String update(WorkFlow workflow) {
        if (checkuser()) {
            String oldcontent = GetGsonString(workFlowService.selectById(workflow.getId()));
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            workflow.setUpdaterid(loginUser.getId());
            workflow.setUpdater(loginUser.getUsername());
            workflow.setUpdatetime(timeString);
            workFlowService.updateById(workflow);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(workflow.getId());
            logs.setName("com.hchenpan.controller.WorkFlowController.update");
            logs.setParams("com.hchenpan.pojo.WorkFlow类");
            logs.setDescription("修改工作流");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(workflow));
            /* 修改，需要保存修改前后的数据 */
            logs.setOldcontent(oldcontent);
            logs.setCreatorid(loginUser.getId());
            logs.setCreator(loginUser.getUsername());
            logs.setCreatetime(timeString);
            logs.setUpdater(loginUser.getUsername());
            logs.setRealname(loginUser.getRealname());
            logs.setUpdatetime(timeString);
            logsService.insert(logs);
            return SUCCESS;
        }
        return ERROR;
    }

    /**
     * 功能:删除工作流程
     */
    @ResponseBody
    @GetMapping("/workflow/delete")
    public String delete(WorkFlow workflow) {
        if (checkuser()) {
            String oldcontent = GetGsonString(workFlowService.selectById(workflow.getId()));
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            workflow.setUpdaterid(loginUser.getId());
            workflow.setUpdater(loginUser.getUsername());
            workflow.setUpdatetime(timeString);
            workFlowService.deleteById(workflow);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(workflow.getId());
            logs.setName("com.hchenpan.controller.WorkFlowController.delete");
            logs.setParams("com.hchenpan.pojo.WorkFlow类");
            logs.setDescription("删除工作流");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(workflow));
            /* 修改，需要保存修改前后的数据 */
            logs.setOldcontent(oldcontent);
            logs.setCreatorid(loginUser.getId());
            logs.setCreator(loginUser.getUsername());
            logs.setCreatetime(timeString);
            logs.setUpdater(loginUser.getUsername());
            logs.setRealname(loginUser.getRealname());
            logs.setUpdatetime(timeString);
            logsService.insert(logs);
            return SUCCESS;
        }
        return ERROR;
    }
}