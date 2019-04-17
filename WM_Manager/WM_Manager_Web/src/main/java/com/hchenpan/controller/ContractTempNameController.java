package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.ContractTempNameController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class ContractTempNameController extends BaseController {
    private final ContractTempNameService contractTempNameService;
    private final ContractTempContentService contractTempContentService;
    private final LogsService logsService;

    @Autowired
    public ContractTempNameController(ContractTempNameService contractTempNameService, ContractTempContentService contractTempContentService, LogsService logsService, UserService userService) {
        this.contractTempNameService = contractTempNameService;
        this.contractTempContentService = contractTempContentService;
        this.logsService = logsService;
    }

    @GetMapping("/contracttemp")
    public String contracttemp() {
        return View("/basicdata/contracttemp");
    }

    /**
     * 功能:合同模板维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/contracttempname/search")
    public String search(ContractTempName contractTempName) {
        Page<ContractTempName> page = getPage();
        return jsonPage(contractTempNameService.selectPage(page, new EntityWrapper<ContractTempName>().eq("flag", "E").like("contractempname", contractTempName.getContractempname(), SqlLike.DEFAULT)));
    }

    /**
     * 功能:提供所有合同模板信息,供下拉
     */
    @ResponseBody
    @PostMapping("/contracttempname/getall")
    public String getall() {
        return ListToGson(contractTempNameService.selectList(new EntityWrapper<ContractTempName>().eq("flag", "E").orderBy("updatetime")));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/contracttempname/getdictionarylistbyid")
    public String getdictionarylistbyid(ContractTempName contractTempName) {
        return GetGsonString(contractTempNameService.selectById(contractTempName.getId()));
    }

    /**
     * 功能：新增合同模板
     */
    @ResponseBody
    @PostMapping("/contracttempname/create")
    public String create(ContractTempName contractTempName) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            contractTempName.setId(getUUID());
            contractTempName.setFlag("E");
            contractTempName.setCreatorid(loginUser.getId());
            contractTempName.setCreator(loginUser.getUsername());
            contractTempName.setCreatetime(timeString);
            contractTempName.setUpdaterid(loginUser.getId());
            contractTempName.setUpdater(loginUser.getUsername());
            contractTempName.setUpdatetime(timeString);
            contractTempNameService.insert(contractTempName);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(contractTempName.getId());
            logs.setName("com.hchenpan.controller.ContractTempNameController.create");
            logs.setParams("com.hchenpan.pojo.ContractTempName类");
            logs.setDescription("新增合同模板信息");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(contractTempName));
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
     * 功能：更新合同模板
     */
    @ResponseBody
    @PostMapping("/contracttempname/update")
    public String update(ContractTempName contractTempName) {
        if (checkuser()) {
            String oldcontent = GetGsonString(contractTempNameService.selectById(contractTempName.getId()));
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            contractTempName.setFlag("E");
            contractTempName.setUpdaterid(loginUser.getId());
            contractTempName.setUpdater(loginUser.getUsername());
            contractTempName.setUpdatetime(timeString);
            contractTempNameService.updateById(contractTempName);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(contractTempName.getId());
            logs.setName("com.hchenpan.controller.ContractTempNameController.update");
            logs.setParams("com.hchenpan.pojo.ContractTempName类");
            logs.setDescription("修改合同模板信息");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(contractTempName));
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
     * 功能:删除合同模板
     */
    @ResponseBody
    @GetMapping("/contracttempname/delete")
    public String delete(ContractTempName contractTempName) {
        if (checkuser()) {
            ContractTempName old = contractTempNameService.selectById(contractTempName.getId());
            String oldcontent = GetGsonString(old);
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            old.setFlag("E");
            old.setUpdaterid(loginUser.getId());
            old.setUpdater(loginUser.getUsername());
            old.setUpdatetime(timeString);
            contractTempContentService.delete(new EntityWrapper<ContractTempContent>().eq("tempnameId", contractTempName.getId()));
            contractTempNameService.deleteById(contractTempName.getId());
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(old.getId());
            logs.setName("com.hchenpan.controller.ContractTempNameController.update");
            logs.setParams("com.hchenpan.pojo.ContractTempName类");
            logs.setDescription("删除合同模板");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(old));
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