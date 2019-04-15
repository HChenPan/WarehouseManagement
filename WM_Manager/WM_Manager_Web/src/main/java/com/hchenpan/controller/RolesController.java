package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.*;
import com.hchenpan.service.*;
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
 * ClassName : com.hchenpan.controller.RolesController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class RolesController extends BaseController {
    private final RolesService rolesService;
    private final PermissionService permissionService;
    private final LogsService logsService;
    private final RolesPermissionsService rolesPermissionsService;
    private final UserService userService;

    @Autowired
    public RolesController(RolesService rolesService, PermissionService permissionService, LogsService logsService, RolesPermissionsService rolesPermissionsService, UserService userService) {

        this.rolesService = rolesService;
        this.permissionService = permissionService;
        this.logsService = logsService;
        this.rolesPermissionsService = rolesPermissionsService;
        this.userService = userService;
    }

    @GetMapping("/role")
    public String role() {
        return View("/role/role");
    }

    /**
     * 功能:角色维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/role/search")
    public String search() {
        Page<Roles> page = getPage();
        return jsonPage(rolesService.selectPage(page));
    }

    /**
     * 功能:取得包含pid值的角色对象
     */
    @ResponseBody
    @PostMapping("/role/getpidlistbyid")
    public String getpidlistbyid(Roles roles) {
        Roles r = rolesService.selectById(roles.getId());
        List<String> pidlist = new ArrayList<>();
        List<Permission> pl = new ArrayList<>();
        for (RolesPermissions rolesPermissions : rolesPermissionsService.selectList(new EntityWrapper<RolesPermissions>().eq("rid", r.getId()))) {
            pidlist.add(rolesPermissions.getPid());
            pl.add(permissionService.selectOne(new EntityWrapper<Permission>().eq("id", rolesPermissions.getPid())));
        }
        r.setPermissions(pl);
        r.setPid(pidlist);
        return GetGsonString(r);
    }

    /**
     * 功能:获取所有权限数据,供表单使用
     */
    @ResponseBody
    @PostMapping("/role/getall")
    public String getall() {
        List<Roles> rolesList = rolesService.selectList(new EntityWrapper<>());
        List<Roles> roles = new ArrayList<>();
        for (Roles r : rolesList) {
            List<String> pidlist = new ArrayList<>();
            List<Permission> pl = new ArrayList<>();
            for (RolesPermissions rolesPermissions : rolesPermissionsService.selectList(new EntityWrapper<RolesPermissions>().eq("rid", r.getId()))) {
                pidlist.add(rolesPermissions.getPid());
                pl.add(permissionService.selectOne(new EntityWrapper<Permission>().eq("id", rolesPermissions.getPid())));
            }
            r.setPermissions(pl);
            r.setPid(pidlist);
            roles.add(r);
        }
        return GetGsonString(roles);
    }

    /**
     * 功能:获取所有权限数据,供表单使用
     */
    @ResponseBody
    @GetMapping("/role/getrolelist")
    public String getrolelist() {
        List<Roles> rolesList = rolesService.selectList(new EntityWrapper<Roles>().eq("type", "业务角色"));
        List<Roles> roles = new ArrayList<>();
        for (Roles r : rolesList) {
            List<String> pidlist = new ArrayList<>();
            List<Permission> pl = new ArrayList<>();
            for (RolesPermissions rolesPermissions : rolesPermissionsService.selectList(new EntityWrapper<RolesPermissions>().eq("rid", r.getId()))) {
                pidlist.add(rolesPermissions.getPid());
                pl.add(permissionService.selectOne(new EntityWrapper<Permission>().eq("id", rolesPermissions.getPid())));
            }
            r.setPermissions(pl);
            r.setPid(pidlist);
            roles.add(r);
        }
        return GetGsonString(roles);
    }

    /**
     * 功能：新增角色
     */
    @ResponseBody
    @PostMapping("/role/create")
    public String create(Roles role) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            role.setId(getUUID());
            role.setCreatorid(loginUser.getId());
            role.setCreator(loginUser.getUsername());
            role.setCreatetime(timeString);
            role.setUpdaterid(loginUser.getId());
            role.setUpdater(loginUser.getUsername());
            role.setUpdatetime(timeString);
            //插入role表
            rolesService.insert(role);
            //插入 role_permission 表
            List<Permission> pl = new ArrayList<>();
            if (role.getPid() != null) {
                for (int i = 0; i < role.getPid().size(); i++) {
                    Permission p = permissionService.selectById(role.getPid().get(i));
                    pl.add(p);
                    RolesPermissions rolesPermissions = new RolesPermissions();
                    rolesPermissions.setPid(p.getId());
                    rolesPermissions.setRid(role.getId());
                    rolesPermissions.setId(getUUID());
                    rolesPermissions.setCreatorid(loginUser.getId());
                    rolesPermissions.setCreator(loginUser.getUsername());
                    rolesPermissions.setCreatetime(timeString);
                    rolesPermissions.setUpdaterid(loginUser.getId());
                    rolesPermissions.setUpdater(loginUser.getUsername());
                    rolesPermissions.setUpdatetime(timeString);
                    rolesPermissionsService.insert(rolesPermissions);
                }
                role.setPermissions(pl);
            }

            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(role.getId());
            logs.setName("com.hchenpan.controller.RolesController.create");
            logs.setParams("com.hchenpan.pojo.Roles");
            logs.setDescription("新增角色");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(role));
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
     * 功能：更新角色
     */
    @ResponseBody
    @PostMapping("/role/update")
    public String update(Roles role) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            Roles r = rolesService.selectById(role.getId());
            String oldcontent = GetGsonString(r);
            List<Permission> pl = new ArrayList<Permission>();
            if (role.getPid() != null) {
                rolesPermissionsService.delete(new EntityWrapper<RolesPermissions>()
                        .eq("rid", role.getId())
                );
                for (int i = 0; i < role.getPid().size(); i++) {
                    Permission p = permissionService.selectById(role.getPid().get(i));
                    pl.add(p);
                    RolesPermissions rolesPermissions = new RolesPermissions();
                    rolesPermissions.setPid(p.getId());
                    rolesPermissions.setRid(role.getId());
                    rolesPermissions.setId(getUUID());
                    rolesPermissions.setCreatorid(loginUser.getId());
                    rolesPermissions.setCreator(loginUser.getUsername());
                    rolesPermissions.setCreatetime(timeString);
                    rolesPermissions.setUpdaterid(loginUser.getId());
                    rolesPermissions.setUpdater(loginUser.getUsername());
                    rolesPermissions.setUpdatetime(timeString);
                    rolesPermissionsService.insert(rolesPermissions);
                }
                role.setPermissions(pl);
            } else {
                role.setPermissions(null);
            }
            rolesService.updateById(role);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(role.getId());
            logs.setName("com.hchenpan.controller.RolesController.update");
            logs.setParams("com.hchenpan.pojo.Roles");
            logs.setDescription("修改角色");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(role));
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
     * 功能:删除角色
     */
    @ResponseBody
    @GetMapping("/role/delete")
    public String delete(Roles role) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            rolesPermissionsService.delete(new EntityWrapper<RolesPermissions>()
                    .eq("rid", role.getId())
            );

            rolesService.deleteById(role);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(role.getId());
            logs.setName("com.hchenpan.controller.RolesController.delete");
            logs.setParams("com.hchenpan.pojo.Roles");
            logs.setDescription("删除角色");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(role));
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
}