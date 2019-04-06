package com.hchenpan.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.gson.Gson;
import com.hchenpan.exception.CustomException;
import com.hchenpan.util.RedisPage;
import com.hchenpan.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.BaseController
 * Description :controller层继承本类获得 得到 web 基础组件值
 * public class ****Controller extends BaseController
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:18 下午
 **/
public class BaseController {
    protected static final String SUCCESS = "success";
    protected static final String ERROR = "error";
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected HttpSession session;
    @Autowired
    protected ServletContext application;

    public BaseController() {
    }

    /**
     * description(描述)
     *
     * @param ts 每页条数
     * @return 分页page
     * @author Huangcp
     * @date 2019/3/19 11:25 下午
     **/
    protected <T> Page<T> getPage(int... ts) {
        /*当前页数*/
        int current = Integer.parseInt(request.getParameter("page"));
        /*每页显示条数*/
        int size;
        if (ts.length == 1) {
            size = ts[0];
        } else {
            size = Integer.parseInt(request.getParameter("rows"));
        }
        /*SQL 排序 ORDER BY 字段，例如： id DESC（根据id倒序查询）*/
        String orderByField = request.getParameter("sort");
        /*是否为升序 ASC（ 默认： true ）*/
        boolean isAsc = !"desc".equals(request.getParameter("order"));

        if (StringUtil.notEmpty(orderByField)) {
            return new Page<>(current, size, orderByField, isAsc);
        } else {
            return new Page<>(current, size, "updatetime", true);
        }

    }

    protected static Map<String, String> ObjectErrorsToMap(List<ObjectError> errors) {
        Map<String, String> map = new HashMap<>(10);
        for (ObjectError error : errors) {
            map.put(((FieldError) error).getField(), error.getDefaultMessage());
        }
        return map;
    }

    /**
     * 验证表单令牌
     *
     * @param session
     * @param token
     * @throws CustomException
     */
    protected void validateAntiForgeryToken(HttpSession session, String token) throws CustomException {
        Object realToken = session.getAttribute("token");
        session.setAttribute("token", UUID.randomUUID().toString());
        if (realToken == null || !realToken.toString().equals(token)) {
            throw new CustomException("操作异常！");
        }
    }

    protected String View(String view) {
        return view;
    }

    protected String View(String view, Model model, Object object) {
        model.addAttribute("model", object);
        return view;
    }

    protected String RedirectTo(String url) {
        return "redirect:" + url;
    }

    protected String toJson(Object object) {
        return JSON.toJSONString(object, SerializerFeature.BrowserCompatible);
    }

    protected String jsonPage(Page<?> page) {
        JSONObject json = new JSONObject();
        json.put("total", page.getTotal());
        json.put("rows", page.getRecords());
        return toJson(json);
    }

    protected String jsonRedisPage(RedisPage<?> page) {
        JSONObject json = new JSONObject();
        json.put("total", page.getTotal());
        json.put("rows", page.getRecords());
        return toJson(json);
    }

    /**
     * 在所有Action入口判断是否登录用户
     */
    protected static boolean checkuser() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    /**
     * 生成主键
     */
    protected static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String GetCurrentTime() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return s.format(Calendar.getInstance().getTime());
    }

    /**
     * 对象转换成JSON字串
     */
    public String GetGsonString(Object obj) {
        return new Gson().toJson(obj);
    }

    /**
     * 获取发起请求的IP
     */
    protected String getRomoteIP() {
        String ip;
        ip = request.getHeader("x-forwarded-for");
        if (isNullIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isNullIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isNullIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isNullIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isNullIp(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        if ("0.0.0.0.0.0.0.1".equals(ip) || "0.0.0.0.0.0.0.1%0".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    private static boolean isNullIp(final String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }


}