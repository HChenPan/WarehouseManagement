package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.model.DictionaryschildVO;
import com.hchenpan.pojo.Dictionarys;
import com.hchenpan.pojo.Dictionaryschild;
import com.hchenpan.pojo.User;
import com.hchenpan.service.DictionaryschildService;
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
public class DictionaryschildController extends BaseController {
    private final DictionaryschildService dictionaryschildService;


    @Autowired
    public DictionaryschildController(DictionaryschildService dictionaryschildService) {
        this.dictionaryschildService = dictionaryschildService;
    }

    /**
     * 提供查询的分页数据
     */
    @ResponseBody
    @RequestMapping(value = "/dictionaryschild/search")
    public String search(Dictionaryschild dictionaryschild) {
        Page<DictionaryschildVO> page = getPage();

        dictionaryschildService.selectDicDictionaryschildVO(page, dictionaryschild.getDcode());
        return null;
    }
}