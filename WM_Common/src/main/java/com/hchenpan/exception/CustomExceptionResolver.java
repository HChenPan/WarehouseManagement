package com.hchenpan.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.exception.CustomExceptionResolver
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/1 08:03 下午
 **/
public class CustomExceptionResolver implements HandlerExceptionResolver {
    public CustomExceptionResolver() {
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        CustomException customException;
        if (ex instanceof CustomException) {
            customException = (CustomException) ex;
        } else {
            customException = new CustomException("网站出现错误！请尽快联系管理员！");
        }
        /*String message = customException.getMessage();modelAndView modelAndView = new ModelAndView();modelAndView.addObject("message", message);modelAndView.setViewName("error");return modelAndView;**/
        return new ModelAndView("error", "message", customException.getMessage());
    }
}