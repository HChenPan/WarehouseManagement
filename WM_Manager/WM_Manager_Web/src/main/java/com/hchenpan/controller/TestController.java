package com.hchenpan.controller;

import com.alibaba.fastjson.JSONObject;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.Test;
import com.hchenpan.service.TestService;
import com.hchenpan.util.DataPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.TestController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:16 下午
 **/
@Controller
@RequestMapping("/Test")
public class TestController extends BaseController {
    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @ResponseBody
    @RequestMapping(value = "/insert", method = {RequestMethod.GET})
    public String insert(Test t) {
        t.setId("1");
        t.setName("12");
        t.setPassword("666");
        if (testService.insertTest(t)) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    @ResponseBody
    @RequestMapping("/list")
    public String list(Model model, Integer p) {
        p = p == null ? 1 : (p < 1 ? 1 : p);
        DataPage<Test> page = testService.selectPage(p, 10, 6);
        JSONObject json = new JSONObject();
        json.put("total", page.getPageCount());
        json.put("rows", page.getData());
        return toJson(json);
    }

}