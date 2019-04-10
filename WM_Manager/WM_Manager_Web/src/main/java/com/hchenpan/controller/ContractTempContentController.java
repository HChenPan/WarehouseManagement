package com.hchenpan.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.ContractTempContent;
import com.hchenpan.pojo.ContractTempName;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.User;
import com.hchenpan.service.ContractTempContentService;
import com.hchenpan.service.ContractTempNameService;
import com.hchenpan.service.LogsService;
import com.hchenpan.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.ContractTempContentController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class ContractTempContentController extends BaseController {
    private final ContractTempContentService contractTempContentService;
    private final ContractTempNameService contractTempNameService;
    private final LogsService logsService;
    private final UserService userService;

    @Autowired
    public ContractTempContentController(ContractTempContentService contractTempContentService, ContractTempNameService contractTempNameService, LogsService logsService, UserService userService) {
        this.contractTempContentService = contractTempContentService;
        this.contractTempNameService = contractTempNameService;
        this.logsService = logsService;
        this.userService = userService;
    }

    /**
     * 功能:合同条款维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/contracttempcontent/search")
    public String search(ContractTempContent contractTempContent) {
        Page<Map<String, Object>> page = getPage();
        return jsonPage(contractTempContentService.selectPage(page, contractTempContent.getId()));
    }


    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/contracttempcontent/getcontentlistbyid")
    public String getcontentlistbyid(ContractTempContent contractTempContent) {
        ContractTempContent content = contractTempContentService.selectById(contractTempContent.getId());
        ContractTempName name = contractTempNameService.selectById(content.getTempnameId());
        content.setTempname(name);
        return GetGsonString(content);
    }

    /**
     * 功能：新增合同条款
     */
    @ResponseBody
    @PostMapping("/contracttempcontent/create")
    public String create(ContractTempContent contractTempContent) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            contractTempContent.setId(getUUID());
            contractTempContent.setFlag("E");
            contractTempContent.setCreatorid(loginUser.getId());
            contractTempContent.setCreator(loginUser.getUsername());
            contractTempContent.setCreatetime(timeString);
            contractTempContent.setUpdaterid(loginUser.getId());
            contractTempContent.setUpdater(loginUser.getUsername());
            contractTempContent.setUpdatetime(timeString);
            contractTempContentService.insert(contractTempContent);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(contractTempContent.getId());
            logs.setName("com.hchenpan.controller.ContractTempContentController.create");
            logs.setParams("com.hchenpan.pojo.ContractTempContent类");
            logs.setDescription("新增合同条款");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(contractTempContent));
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
     * 功能：更新合同条款
     */
    @ResponseBody
    @PostMapping("/contracttempcontent/update")
    public String update(ContractTempContent contractTempContent) {
        if (checkuser()) {
            ContractTempContent old = contractTempContentService.selectById(contractTempContent.getId());
            String oldcontent = GetGsonString(old);
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            contractTempContent.setFlag("E");
            contractTempContent.setUpdaterid(loginUser.getId());
            contractTempContent.setUpdater(loginUser.getUsername());
            contractTempContent.setUpdatetime(timeString);
            contractTempContentService.updateById(contractTempContent);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(contractTempContent.getId());
            logs.setName("com.hchenpan.controller.ContractTempContentController.update");
            logs.setParams("com.hchenpan.pojo.ContractTempContent类");
            logs.setDescription("修改合同条款");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(contractTempContent));
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
     * 功能:删除合同条款
     */
    @ResponseBody
    @GetMapping("/contracttempcontent/delete")
    public String delete(ContractTempContent contractTempContent) {
        if (checkuser()) {
            ContractTempContent old = contractTempContentService.selectById(contractTempContent.getId());
            String oldcontent = GetGsonString(old);
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            contractTempContent.setFlag("D");
            contractTempContent.setUpdaterid(loginUser.getId());
            contractTempContent.setUpdater(loginUser.getUsername());
            contractTempContent.setUpdatetime(timeString);
            contractTempContentService.deleteById(contractTempContent);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(contractTempContent.getId());
            logs.setName("com.hchenpan.controller.ContractTempContentController.delete");
            logs.setParams("com.hchenpan.pojo.ContractTempContent类");
            logs.setDescription("删除合同条款");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(contractTempContent));
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