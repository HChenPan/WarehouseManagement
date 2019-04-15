package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.Permission;
import com.hchenpan.pojo.RolesPermissions;
import com.hchenpan.pojo.User;
import com.hchenpan.service.LogsService;
import com.hchenpan.service.PermissionService;
import com.hchenpan.service.RolesPermissionsService;
import com.hchenpan.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.PermissionController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class PermissionController extends BaseController {
    private final PermissionService permissionService;
    private final LogsService logsService;
    private final RolesPermissionsService rolesPermissionsService;
    private final UserService userService;

    @Autowired
    public PermissionController(PermissionService permissionService, LogsService logsService, RolesPermissionsService rolesPermissionsService, UserService userService) {
        this.permissionService = permissionService;
        this.logsService = logsService;
        this.rolesPermissionsService = rolesPermissionsService;
        this.userService = userService;
    }

    @GetMapping("/permission")
    public String permission() {
        return View("/permission/permission");
    }

    /**
     * 功能:权限维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/permission/search")
    public String search(Permission permission) {
        Page<Permission> page = getPage();
        return jsonPage(permissionService.selectPage(page));
    }

    /**
     * 功能:提供所有权限信息,供下拉
     */
    @ResponseBody
    @PostMapping("/permission/getall")
    public String getall() {
        return GetGsonString(permissionService.selectList(new EntityWrapper<>()));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/permission/getbyid")
    public String getbyid(Permission permission) {
        return ERROR;
    }

    /**
     * 功能：新增权限
     */
    @ResponseBody
    @PostMapping("/permission/create")
    public String create(Permission permission) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            permission.setId(getUUID());
            permission.setCreatorid(loginUser.getId());
            permission.setCreator(loginUser.getUsername());
            permission.setCreatetime(timeString);
            permission.setUpdaterid(loginUser.getId());
            permission.setUpdater(loginUser.getUsername());
            permission.setUpdatetime(timeString);
            permission.setNameValue(permission.getNameValue().toUpperCase().trim());
            permissionService.insert(permission);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(permission.getId());
            logs.setName("com.hchenpan.controller.PermissionController.create");
            logs.setParams("com.hchenpan.pojo.Permission");
            logs.setDescription("新增权限");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(permission));
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
     * 功能：更新权限
     */
    @ResponseBody
    @PostMapping("/permission/update")
    public String update(Permission permission) {
        if (checkuser()) {
            Permission p = permissionService.selectById(permission.getId());
            String oldcontent = GetGsonString(p);
            permission.setNameValue(permission.getNameValue().toUpperCase().trim());
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            permission.setUpdaterid(loginUser.getId());
            permission.setUpdater(loginUser.getUsername());
            permission.setUpdatetime(timeString);


            permissionService.updateById(permission);

            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(permission.getId());
            logs.setName("com.hchenpan.controller.PermissionController.create");
            logs.setParams("com.hchenpan.pojo.Permission");
            logs.setDescription("修改权限");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(permission));
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
     * 功能:删除权限
     */
    @ResponseBody
    @GetMapping("/permission/delete")
    public String delete(Permission permission) {
        if (checkuser()) {
            Permission p = permissionService.selectById(permission.getId());
            String oldcontent = GetGsonString(p);
            p.setNameValue(permission.getNameValue().toUpperCase().trim());
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            p.setUpdaterid(loginUser.getId());
            p.setUpdater(loginUser.getUsername());
            p.setUpdatetime(timeString);
            rolesPermissionsService.delete(new EntityWrapper<RolesPermissions>().eq("pid", permission.getId()));
            permissionService.deleteById(permission);


            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(p.getId());
            logs.setName("com.hchenpan.controller.PermissionController.create");
            logs.setParams("com.hchenpan.pojo.Permission");
            logs.setDescription("删除权限");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(p));
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