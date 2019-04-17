package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.model.CommboxList;
import com.hchenpan.pojo.User;
import com.hchenpan.pojo.UserRoles;
import com.hchenpan.service.DepartmentService;
import com.hchenpan.service.RolesService;
import com.hchenpan.service.UserRolesService;
import com.hchenpan.service.UserService;
import com.hchenpan.util.LoginViewModel;
import com.hchenpan.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
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
public class UserController extends BaseController {
    private final UserService userService;
    private final RolesService rolesService;
    private final DepartmentService departmentService;
    private final UserRolesService userRolesService;


    @Autowired
    public UserController(UserService userService, RolesService rolesService, DepartmentService departmentService, UserRolesService userRolesService) {
        this.userService = userService;
        this.rolesService = rolesService;
        this.departmentService = departmentService;
        this.userRolesService = userRolesService;
    }

    @GetMapping("/login")
    public String login() {
        return View("login");
    }

    @GetMapping("/user")
    public String user() {
        return View("/user/user");
    }

    @GetMapping("/main")
    public String main() {
        return View("main");
    }


    @PostMapping("/login")
    public String loginSubmit(@Valid LoginViewModel user, Model model, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            Subject currentUser = SecurityUtils.getSubject();
            if (!currentUser.isAuthenticated()) {
                // 把用户名和密码封装为 UsernamePasswordToken 对象
                UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername().trim(), user.getPassword().trim());
                // rememberme
                token.setRememberMe(true);
                Object result = new SimpleHash("MD5", user.getPassword(), user.getUsername(), 1024);

                if (currentUser.isAuthenticated()) {
                    currentUser.logout();
                }
                try {
                    // 执行登录.
                    currentUser.login(token);
                    //保存登录的用户信息到session
                    EntityWrapper<User> ew = new EntityWrapper<>();
                    ew.eq("username", user.getUsername());
                    User loginUser = userService.selectOne(ew);
                    currentUser.getSession().setAttribute("user", loginUser);
                    return RedirectTo("/main");
                } catch (UnknownAccountException uae) {
                    model.addAttribute("msg", "用户名不存在，请联系管理员！");
                } catch (IncorrectCredentialsException ice) {
                    model.addAttribute("msg", "密码错误请核对后重新输入");
                } catch (LockedAccountException lae) {
                    model.addAttribute("msg", "账户被锁定，请联系管理员！");
                } catch (AuthenticationException ae) {
                    System.out.println("登录失败: " + ae.getMessage());
                    model.addAttribute("msg", "用户名和密码不匹配,请重新输入");
                }
            }
        } else {
            model.addAttribute("errors", ObjectErrorsToMap(bindingResult.getAllErrors()));
        }
        user.setPassword("");
        return View("login", model, user);

    }


    @GetMapping("/logout")
    public String userlogout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return RedirectTo("/login");
    }

    /**
     * 为用户界面提供json数据
     */
    @ResponseBody
    @RequestMapping(value = "/user/search")
    public String search(User user) {
        Page<User> page = getPage();
        EntityWrapper<User> ew = new EntityWrapper<>();
        if (StringUtil.notTrimEmpty(user.getUsername())) {
            ew.like("username", user.getUsername(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(user.getRealname())) {
            ew.like("realname", user.getRealname(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(user.getPassword1())) {
            ew.eq("departmentid", user.getPassword1());
        }
        if (StringUtil.notTrimEmpty(user.getPassword2())) {
            //获取角色列表
            List<UserRoles> userRoles = userRolesService.selectList(new EntityWrapper<UserRoles>().eq("roleid", user.getPassword2()));
            StringBuilder uids = new StringBuilder();
            for (UserRoles userRole : userRoles) {
                uids.append(userRole.getUserid()).append(",");
            }
            ew.in("id", uids.substring(0, uids.length() - 1));
        }
        return jsonPage(userService.selectPage(page, ew));




      /*  int current = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("rows"));
        *//*SQL 排序 ORDER BY 字段，例如： id DESC（根据id倒序查询）*//*
        String orderByField = request.getParameter("sort");
        *//*是否为升序 ASC（ 默认： true ）*//*
        boolean isAsc = !"desc".equals(request.getParameter("order"));
        RedisPage<User> page1;
        if (StringUtil.notEmpty(orderByField)) {
            page1 = userService.selectPageRedis(current, size, orderByField, isAsc, user);
        } else {
            page1 = userService.selectPageRedis(current, size, "updatetime", true, user);
        }
        return jsonRedisPage(page1);*/
    }

    /**
     * 功能:取得完整包含rid值的用户对象
     */
    @ResponseBody
    @GetMapping("/user/getridlistbyid")
    public String getridlistbyid(User user) {
        User u = userService.selectById(user.getId());
        List<UserRoles> userRoles = userRolesService.selectList(new EntityWrapper<UserRoles>().eq("userid", user.getId()));
        List<String> ridlist = new ArrayList<>();
        for (UserRoles userRole : userRoles) {
            ridlist.add(userRole.getRoleid());
        }
        u.setRid(ridlist);
        return GetGsonString(u);
    }

    /**
     * 功能:根据专业分类取得用户
     */
    @ResponseBody
    @PostMapping("/user/getexpertbytype")
    public String getexpertbytype() {
        List<CommboxList> deptserlist = userService.getdeptuserlist();
        return GetGsonString(deptserlist);
    }

    /**
     * 功能:获取部门用户列表
     */
    @ResponseBody
    @PostMapping("/user/getdeptuserlist")
    public String getdeptuserlist() {
        List<CommboxList> deptserlist = userService.getdeptuserlist();
        return GetGsonString(deptserlist);
    }

    /**
     * 功能:设定用户密码
     */
    @ResponseBody
    @PostMapping("/user/setpassword")
    public String setpassword(User user) {
        if (checkuser()) {

            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            loginUser.setUpdaterid(loginUser.getId());
            loginUser.setUpdater(loginUser.getUsername());
            loginUser.setUpdatetime(timeString);
            loginUser.setPassword(user.getPassword1());
            userService.updateById(loginUser);
            return SUCCESS;
        }
        return ERROR;
    }

    /**
     * 功能：新增用户
     */
    @ResponseBody
    @PostMapping("/user/create")
    public String create(User user) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            user.setId(getUUID());
            user.setCreatorid(loginUser.getId());
            user.setCreator(loginUser.getUsername());
            user.setCreatetime(timeString);
            user.setUpdaterid(loginUser.getId());
            user.setUpdater(loginUser.getUsername());
            user.setUpdatetime(timeString);
            String md5 = "e10adc3949ba59abbe56e057f20f883e";
            String newPs = new SimpleHash("MD5", md5, user.getUsername(), 1024).toHex();
            user.setPassword(newPs);
            user.setDepartment(departmentService.selectById(user.getDepartmentid()).getName());
            if (!"1".equals(loginUser.getIssuper())) {
                user.setIssuper("0");
            }
            userService.insert(user);

            if (user.getRid() != null) {
                for (int i = 0; i < user.getRid().size(); i++) {
                    UserRoles ur = new UserRoles();
                    ur.setUserid(user.getId());
                    ur.setRoleid(user.getRid().get(i));
                    ur.setId(getUUID());
                    ur.setCreatorid(loginUser.getId());
                    ur.setCreator(loginUser.getUsername());
                    ur.setCreatetime(timeString);
                    ur.setUpdaterid(loginUser.getId());
                    ur.setUpdater(loginUser.getUsername());
                    ur.setUpdatetime(timeString);
                    userRolesService.insert(ur);
                }
            }
            return SUCCESS;
        }
        return ERROR;

    }

    /**
     * 功能：新增用户
     */
    @ResponseBody
    @PostMapping("/user/update")
    public String update(User user) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            userRolesService.delete(new EntityWrapper<UserRoles>().eq("userid", user.getId()));
            if (user.getRid() != null) {
                for (int i = 0; i < user.getRid().size(); i++) {
                    UserRoles ur = new UserRoles();
                    ur.setUserid(user.getId());
                    ur.setRoleid(user.getRid().get(i));
                    ur.setId(getUUID());
                    ur.setCreatorid(loginUser.getId());
                    ur.setCreator(loginUser.getUsername());
                    ur.setCreatetime(timeString);
                    ur.setUpdaterid(loginUser.getId());
                    ur.setUpdater(loginUser.getUsername());
                    ur.setUpdatetime(timeString);
                    userRolesService.insert(ur);
                }
            }
            user.setUpdaterid(loginUser.getId());
            user.setUpdater(loginUser.getUsername());
            user.setUpdatetime(timeString);
            user.setDepartment(departmentService.selectById(user.getDepartmentid()).getName());
            userService.updateById(user);
            return SUCCESS;
        }
        return ERROR;
    }

    /**
     * 功能： 删除用户
     */
    @ResponseBody
    @PostMapping("/user/delete")
    public String delete(User user) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            userRolesService.delete(new EntityWrapper<UserRoles>().eq("userid", user.getId()));

            userService.deleteById(user.getId());
            return SUCCESS;
        }
        return ERROR;
    }
}