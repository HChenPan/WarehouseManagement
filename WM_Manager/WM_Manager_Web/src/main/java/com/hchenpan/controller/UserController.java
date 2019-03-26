package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.User;
import com.hchenpan.service.UserService;
import com.hchenpan.util.LoginViewModel;
import com.hchenpan.util.RedisPage;
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


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
        int current = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("rows"));
        /*SQL 排序 ORDER BY 字段，例如： id DESC（根据id倒序查询）*/
        String orderByField = request.getParameter("sort");
        /*是否为升序 ASC（ 默认： true ）*/
        boolean isAsc = !"desc".equals(request.getParameter("order"));
        RedisPage<User> page1;
        if (StringUtil.notEmpty(orderByField)) {
            page1 = userService.selectPageRedis(current, size, orderByField, isAsc, user);
        } else {
            page1 = userService.selectPageRedis(current, size, "updatetime", true, user);
        }
        return jsonRedisPage(page1);
    }
}