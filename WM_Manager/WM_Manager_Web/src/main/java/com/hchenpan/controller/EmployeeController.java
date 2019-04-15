package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.Employee;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.User;
import com.hchenpan.service.EmployeeService;
import com.hchenpan.service.LogsService;
import com.hchenpan.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.EmployeeController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class EmployeeController extends BaseController {
    private final EmployeeService employeeService;
    private final LogsService logsService;
    private final UserService userService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, LogsService logsService, UserService userService) {
        this.employeeService = employeeService;
        this.logsService = logsService;
        this.userService = userService;
    }

    @GetMapping("/employee")
    public String employee() {
        return View("/employee/employee");
    }

    /**
     * 功能:员工维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/employee/search")
    public String search(Employee employee) {
        Page<Map<String, Object>> page = getPage();
        Map<String, Object> params = new HashMap<>(10);
        /*模糊搜索*/
        if (StringUtils.isNotEmpty(employee.getEmployeename())) {
            params.put("employeename", employee.getEmployeename());
        }
        if (StringUtils.isNotEmpty(employee.getEmployeenum())) {
            params.put("employeenum", employee.getEmployeenum());
        }
        if (StringUtils.isNotEmpty(employee.getPhonenum())) {
            params.put("phonenum", employee.getPhonenum());

        }
        return jsonPage(employeeService.getlistPage(page, params));


    }

    /**
     * 功能:提供所有员工信息,供下拉
     */
    @ResponseBody
    @PostMapping("/employee/getall")
    public String getall() {
        return ERROR;
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/employee/getallbyid")
    public String getallbyid(Employee employee) {
        return GetGsonString(employeeService.selectById(employee.getId()));
    }

    /**
     * 功能：新增员工
     */
    @ResponseBody
    @PostMapping("/employee/create")
    public String create(Employee employee) {
        if (checkuser()) {
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            int num = employeeService.selectCount(new EntityWrapper<Employee>()
                    .eq("flag", "E")
                    .eq("employeenum", employee.getEmployeenum())
            );
            if (num > 0) {
                return "员工编码已存在";
            } else {
                employee.setEmployeenum(employee.getEmployeenum().trim());
                employee.setEmployeename(employee.getEmployeename().trim());
                employee.setPhonenum(employee.getPhonenum().trim());
                employee.setDepartid(employee.getDepartid().trim());
                employee.setId(getUUID());
                employee.setFlag("E");
                employee.setCreatorid(loginUser.getId());
                employee.setCreator(loginUser.getUsername());
                employee.setCreatetime(timeString);
                employee.setUpdaterid(loginUser.getId());
                employee.setUpdater(loginUser.getUsername());
                employee.setUpdatetime(timeString);
                employee.setCreatetime(timeString);
                employeeService.insert(employee);

                // 写入日志表
                //写入日志表
                Logs logs = new Logs();
                logs.setId(getUUID());
                logs.setFlagid(employee.getId());
                logs.setName("com.hchenpan.controller.EmployeeController.create");
                logs.setParams("com.hchenpan.pojo.Employee");
                logs.setDescription("新增员工");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(employee));
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
        }
        return ERROR;
    }

    /**
     * 功能：更新员工
     */
    @ResponseBody
    @PostMapping("/employee/update")
    public String update(Employee employee) {
        if (checkuser()) {
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            Employee d = employeeService.selectById(employee.getId());
            String oldcontent = GetGsonString(d);
            // 获取未修改前的员工编码
            String a = d.getEmployeenum();
            // 未修改仓库编码的情况
            if (a.equals(employee.getEmployeenum())) {
                d.setEmployeenum(employee.getEmployeenum().trim());
                d.setEmployeename(employee.getEmployeename().trim());
                d.setPhonenum(employee.getPhonenum().trim());
                d.setDepartid(employee.getDepartid().trim());

                d.setUpdatetime(timeString);
                employeeService.updateById(d);

                // 写入日志表
                //写入日志表
                Logs logs = new Logs();
                logs.setId(getUUID());
                logs.setFlagid(employee.getId());
                logs.setName("com.hchenpan.controller.EmployeeController.update");
                logs.setParams("com.hchenpan.pojo.Employee");
                logs.setDescription("修改员工");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(employee));
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
            // 修改员工编码的情况
            else {
                // 判断仓库编码是否存在
                int num = employeeService.selectCount(new EntityWrapper<Employee>()
                        .eq("flag", "E")
                        .eq("employeenum", employee.getEmployeenum())
                );

                if (num > 0) {
                    return "员工编码已存在";
                } else {
                    d.setEmployeenum(employee.getEmployeenum().trim());
                    d.setEmployeename(employee.getEmployeename().trim());
                    d.setPhonenum(employee.getPhonenum().trim());
                    d.setDepartid(employee.getDepartid().trim());

                    d.setUpdatetime(timeString);
                    /*d.setNote(plan.getNote().trim());*/
                    employeeService.updateById(d);

                    // 写入日志表
                    //写入日志表
                    Logs logs = new Logs();
                    logs.setId(getUUID());
                    logs.setFlagid(employee.getId());
                    logs.setName("com.hchenpan.controller.EmployeeController.update");
                    logs.setParams("com.hchenpan.pojo.Employee");
                    logs.setDescription("修改员工");
                    logs.setUpdaterid(loginUser.getId());
                    logs.setIpaddress(getRomoteIP());
                    logs.setOptcontent(GetGsonString(employee));
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
            }
        }
        return ERROR;
    }

    /**
     * 功能:删除员工
     */
    @ResponseBody
    @GetMapping("/employee/delete")
    public String delete(Employee employee) {
        if (checkuser()) {
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            Employee d = employeeService.selectById(employee.getId());
            String oldcontent = GetGsonString(d);
            d.setFlag("D");

            d.setUpdatetime(timeString);
            employeeService.updateById(d);


            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(d.getId());
            logs.setName("com.hchenpan.controller.EmployeeController.delete");
            logs.setParams("com.hchenpan.pojo.Employee");
            logs.setDescription("删除员工");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(d));
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