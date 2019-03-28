package com.hchenpan.controller;

import com.hchenpan.common.BaseController;
import com.hchenpan.service.DictionaryschildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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


}