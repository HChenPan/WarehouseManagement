package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.model.DepartJSON;
import com.hchenpan.model.DepartTree;
import com.hchenpan.model.PageContainer;
import com.hchenpan.pojo.Department;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.User;
import com.hchenpan.service.DepartmentService;
import com.hchenpan.service.LogsService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.UserController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class DepartmentController extends BaseController {
    private final DepartmentService departmentService;
    private final LogsService logsService;


    @Autowired
    public DepartmentController(DepartmentService departmentService, LogsService logsService) {
        this.departmentService = departmentService;
        this.logsService = logsService;
    }

    @GetMapping("/department")
    public String department() {
        return View("/department/department");
    }


    /**
     * description(描述) 获取部门树形结构
     *
     * @return string
     * @author Huangcp
     * @date 2019/3/24 04:42 下午
     **/
    @ResponseBody
    @RequestMapping(value = "/department/gettreedepart")
    public String gettreedepart() {
        List<DepartJSON> departlist = departmentService.getrootlist();
        List<DepartJSON> dlist = gettree(departlist);
        return toJson(dlist);
    }

    /**
     * description(描述) 递归调用函数
     *
     * @param dlist 参数
     * @return java.util.List<com.hchenpan.pojo.Department>
     * @author Huangcp
     * @date 2019/3/24 04:46 下午
     **/
    private List<DepartJSON> gettree(List<DepartJSON> dlist) {
        for (DepartJSON d : dlist) {
            List<DepartJSON> departlist = departmentService.getsonlist(d);
            if (departlist.size() > 0) {
                d.setChildren(gettree(departlist));
            }
        }
        return dlist;
    }

    /**
     * 功能:提供所有数据,供下拉
     */
    @ResponseBody
    @PostMapping("/department/getDepart")
    public String getDepart(Department department) {
        return ListToGson(departmentService.selectList(new EntityWrapper<Department>().eq("name", department.getName())));
    }


    @ResponseBody
    @PostMapping("/department/getall")
    public String getall() {
        return GetGsonString(departmentService.selectList(new EntityWrapper<Department>().orderBy("name")));
    }

    /**
     * 功能:提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/department/search")
    public String search() {
        Page<Department> page = departmentService.selectPage(new Page<>());
        List<Department> records = page.getRecords();
        List<DepartTree> departments = new ArrayList<>();
        for (Department department : records) {
            DepartTree depart = new DepartTree();
            depart.set_parentId(department.getParentid());
            depart.setName(department.getName());
            depart.setId(department.getId());
            depart.setCreator(department.getCreator());
            depart.setUpdaterid(department.getUpdaterid());
            depart.setUpdater(department.getUpdater());
            depart.setTel(department.getTel());
            depart.setDeptnumber(department.getDeptnumber());
            depart.setCreatorid(department.getCreatorid());
            depart.setCreatetime(department.getCreatetime());


            departments.add(depart);
        }
        PageContainer<DepartTree> pageContainer = new PageContainer<>();
        pageContainer.setRows(departments);
        pageContainer.setTotal((int) page.getTotal());
        return GetGsonString(pageContainer);
    }

    /**
     * 功能:创建根节点组织机构
     */
    @ResponseBody
    @GetMapping("/department/createroot")
    public String createroot() {
        Department department = new Department();
        /*通用字段赋值*/
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        String timeString = GetCurrentTime();
        department.setId(getUUID());
        department.setCreatorid(loginUser.getId());
        department.setCreator(loginUser.getUsername());
        department.setCreatetime(timeString);
        department.setUpdaterid(loginUser.getId());
        department.setUpdater(loginUser.getUsername());
        department.setUpdatetime(timeString);
        department.setName("马钢集团公司");

        departmentService.insert(department);

        //写入日志表
        Logs logs = new Logs();
        logs.setId(getUUID());
        logs.setFlagid(department.getId());
        logs.setName("com.hchenpan.controller.DepartmentController.createroot");
        logs.setParams("com.hchenpan.pojo.Department");
        logs.setDescription("新增根节点组织机构");
        logs.setUpdaterid(loginUser.getId());
        logs.setIpaddress(getRomoteIP());
        logs.setOptcontent(GetGsonString(department));
        /* 修改，需要保存修改前后的数据 */
        logs.setOldcontent(null);
        logs.setCreatorid(loginUser.getId());
        logs.setCreator(loginUser.getUsername());
        logs.setCreatetime(timeString);
        logs.setUpdater(loginUser.getUsername());
        logs.setRealname(loginUser.getRealname());
        logs.setUpdatetime(timeString);
        logsService.insert(logs);
        return GetGsonString(department);
    }

    /**
     * 功能：新增部门
     */
    @ResponseBody
    @PostMapping("/department/create")
    public String create(Department department) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            department.setId(getUUID());
            department.setCreatorid(loginUser.getId());
            department.setCreator(loginUser.getUsername());
            department.setCreatetime(timeString);
            department.setUpdaterid(loginUser.getId());
            department.setUpdater(loginUser.getUsername());
            department.setUpdatetime(timeString);
            Object parentid = request.getParameter("_parentId");
            department.setParentid(parentid.toString());

            departmentService.insert(department);

            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(department.getId());
            logs.setName("com.hchenpan.controller.DepartmentController.create");
            logs.setParams("com.hchenpan.pojo.Department");
            logs.setDescription("新增部门");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(department));
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
     * 功能：更新部门
     */
    @ResponseBody
    @PostMapping("/department/update")
    public String update(Department department) {
        if (checkuser()) {
            Department p = departmentService.selectById(department.getId());
            String oldcontent = GetGsonString(p);

            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            department.setUpdaterid(loginUser.getId());
            department.setUpdater(loginUser.getUsername());
            department.setUpdatetime(timeString);


            departmentService.updateById(department);

            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(department.getId());
            logs.setName("com.hchenpan.controller.DepartmentController.create");
            logs.setParams("com.hchenpan.pojo.Department");
            logs.setDescription("修改部门");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(department));
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
     * 功能:删除部门
     */
    @ResponseBody
    @GetMapping("/department/delete")
    public String delete(Department department) {
        if (checkuser()) {
            Department p = departmentService.selectById(department.getId());
            String oldcontent = GetGsonString(p);

            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            p.setUpdaterid(loginUser.getId());
            p.setUpdater(loginUser.getUsername());
            p.setUpdatetime(timeString);

            departmentService.deleteById(department);


            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(p.getId());
            logs.setName("com.hchenpan.controller.DepartmentController.create");
            logs.setParams("com.hchenpan.pojo.Department");
            logs.setDescription("删除部门");
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