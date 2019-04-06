package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.ProjectNoManage;
import com.hchenpan.pojo.User;
import com.hchenpan.service.LogsService;
import com.hchenpan.service.ProjectNoManageService;
import com.hchenpan.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.ProjectNoManageController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class ProjectNoManageController extends BaseController {
    private final ProjectNoManageService projectNoManageService;
    private final LogsService logsService;

    @Autowired
    public ProjectNoManageController(ProjectNoManageService projectNoManageService, LogsService logsService) {
        this.projectNoManageService = projectNoManageService;
        this.logsService = logsService;
    }

    @GetMapping("/projectNoManage")
    public String projectNoManage() {
        return View("/basicdata/projectNoManage");
    }

    /**
     * 功能:提供所有 工程号信息,供下拉
     */
    @ResponseBody
    @PostMapping("/projectnomanage/getall")
    public String getall() {
        return GetGsonString(projectNoManageService.selectList(new EntityWrapper<ProjectNoManage>().eq("flag", "E").orderBy("createtime", true)));
    }

    /**
     * 功能:工程号维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/projectnomanage/search")
    public String search(ProjectNoManage projectNoManage) {
        Page<ProjectNoManage> page = getPage();
        EntityWrapper<ProjectNoManage> ew = new EntityWrapper<>();
        ew.eq("flag", "E");
        if (StringUtil.notTrimEmpty(projectNoManage.getProjectno())) {
            ew.like("projectno", projectNoManage.getProjectno());
        }
        if (StringUtil.notTrimEmpty(projectNoManage.getProjectname())) {
            ew.like("projectname", projectNoManage.getProjectname());
        }
        if (StringUtil.notTrimEmpty(projectNoManage.getCreateperson())) {
            ew.like("createperson", projectNoManage.getCreateperson());
        }
        return jsonPage(projectNoManageService.selectPage(page, ew));
    }

    /**
     * 功能：新增工程号
     */
    @ResponseBody
    @PostMapping("/projectnomanage/create")
    public String create(ProjectNoManage projectNoManage) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            projectNoManage.setId(getUUID());
            projectNoManage.setCreatorid(loginUser.getId());
            projectNoManage.setCreator(loginUser.getUsername());
            projectNoManage.setCreatetime(timeString);
            projectNoManage.setUpdaterid(loginUser.getId());
            projectNoManage.setUpdater(loginUser.getUsername());
            projectNoManage.setUpdatetime(timeString);
            projectNoManage.setCreateperson(loginUser.getRealname());
            projectNoManage.setUpdateperson(loginUser.getRealname());
            projectNoManage.setFlag("E");
            if (projectNoManageService.selectCount(new EntityWrapper<ProjectNoManage>().eq("flag", "E").eq("projectno", projectNoManage.getProjectno().trim())) > 0) {
                return "工程号编码已存在";
            } else if (projectNoManageService.selectCount(new EntityWrapper<ProjectNoManage>().eq("flag", "E").eq("projectname", projectNoManage.getProjectname().trim())) > 0) {
                return "工程号名称已存在";
            } else {
                projectNoManageService.insert(projectNoManage);
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(projectNoManage.getId());
                logs.setName("com.hchenpan.controller.ProjectNoManageController.create");
                logs.setParams("com.hchenpan.pojo.ProjectNoManage类");
                logs.setDescription("新增工程号基本信息");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(projectNoManage));
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
        }
        return ERROR;
    }

    /**
     * 功能：更新工程号
     */
    @ResponseBody
    @PostMapping("/projectnomanage/update")
    public String update(ProjectNoManage projectNoManage) {
        if (checkuser()) {
            ProjectNoManage old = projectNoManageService.selectById(projectNoManage.getId());
            String oldcontent = GetGsonString(old);
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            projectNoManage.setUpdaterid(loginUser.getId());
            projectNoManage.setUpdater(loginUser.getUsername());
            projectNoManage.setUpdatetime(timeString);
            projectNoManage.setUpdateperson(loginUser.getRealname());
            projectNoManage.setFlag("E");
            // 判断编码是否修改
            /* 编码是否相同 */
            if (old.getProjectno().trim().equals(projectNoManage.getProjectno().trim())) {
                // 编码相同
                /* 名称是否相同 */
                if (!old.getProjectname().trim().equals(projectNoManage.getProjectname().trim())) {
                    // 名称不同
                    /* 名称查重 */
                    if (projectNoManageService.selectCount(new EntityWrapper<ProjectNoManage>().eq("flag", "E").eq("projectname", projectNoManage.getProjectname().trim())) > 0) {
                        // 名称存在重复
                        return "工程号名称已存在";
                    }
                }
            } else {
                // 编码不同
                /* 编码查重 */
                if (projectNoManageService.selectCount(new EntityWrapper<ProjectNoManage>().eq("flag", "E").eq("projectno", projectNoManage.getProjectno().trim())) > 0) {
                    return "工程号编码已存在";
                } else {
                    // 不存在重复
                    /* 名称是否相同 */
                    if (!old.getProjectname().trim().equals(projectNoManage.getProjectname().trim())) {
                        // 名称不同
                        /* 名称查重 */
                        if (projectNoManageService.selectCount(new EntityWrapper<ProjectNoManage>().eq("flag", "E").eq("projectname", projectNoManage.getProjectname().trim())) > 0) {
                            // 名称存在重复
                            return "工程号名称已存在";
                        }
                    }
                }
            }
            projectNoManageService.updateById(projectNoManage);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(projectNoManage.getId());
            logs.setName("com.hchenpan.controller.ProjectNoManageController.update");
            logs.setParams("com.hchenpan.pojo.ProjectNoManage类");
            logs.setDescription("修改工程号基本信息");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(projectNoManage));
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
     * 功能:删除 工程号信息
     */
    @ResponseBody
    @GetMapping("/projectnomanage/delete")
    public String delete(ProjectNoManage projectNoManage) {
        if (checkuser()) {
            ProjectNoManage old = projectNoManageService.selectById(projectNoManage.getId());
            String oldcontent = GetGsonString(old);
            old.setFlag("D");
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            old.setUpdaterid(loginUser.getId());
            old.setUpdater(loginUser.getUsername());
            old.setUpdatetime(timeString);
            old.setUpdateperson(loginUser.getRealname());
            projectNoManageService.updateById(old);

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(projectNoManage.getId());
            logs.setName("com.hchenpan.controller.ProjectNoManageController.delete");
            logs.setParams("com.hchenpan.pojo.ProjectNoManage类");
            logs.setDescription("删除工程号基本信息");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(projectNoManage));
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
     * 根据主键取工程号记录
     */
    @ResponseBody
    @GetMapping("/projectnomanage/getbyid")
    public String getbyid(ProjectNoManage projectNoManage) {
        return GetGsonString(projectNoManageService.selectById(projectNoManage.getId()));
    }

    /**
     * 根据工程号获取工程名称
     */
    @ResponseBody
    @GetMapping("/projectnomanage/getNameByCode")
    public String getNameByCode(ProjectNoManage projectNoManage) {
        return GetGsonString(projectNoManageService.selectOne(new EntityWrapper<ProjectNoManage>().eq("flag", "E").eq("projectno", projectNoManage.getProjectno())).getProjectname());
    }
}