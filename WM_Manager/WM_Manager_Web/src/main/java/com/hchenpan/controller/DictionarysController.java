package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.Dictionarys;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.User;
import com.hchenpan.service.DictionarysService;
import com.hchenpan.service.LogsService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class DictionarysController extends BaseController {
    private final DictionarysService dictionarysService;
    private final LogsService logsService;


    @Autowired
    public DictionarysController(DictionarysService dictionarysService, LogsService logsService) {
        this.dictionarysService = dictionarysService;
        this.logsService = logsService;
    }

    @GetMapping("/dictionary")
    public String dictionary() {
        return View("/basicdata/dictionary");
    }

    /**
     * 提供查询的分页数据
     */
    @ResponseBody
    @RequestMapping(value = "/dictionarys/search")
    public String search(Dictionarys dictionarys) {
        Page<Dictionarys> page = getPage();
        EntityWrapper<Dictionarys> ew = new EntityWrapper<>(dictionarys);
        ew.eq("FLAG", "E");
        return jsonPage(dictionarysService.selectPage(page, ew));
    }

    /**
     * 新增数据字典大类
     */
    @RequestMapping(value = "/dictionarys/create")
    public String create(Dictionarys dictionarys) {
        if (checkuser()) {
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            dictionarys.setId(getUUID());
            dictionarys.setFlag("E");
            String timeString = GetCurrentTime();
            dictionarys.setCreatorid(loginUser.getId());
            dictionarys.setCreator(loginUser.getUsername());
            dictionarys.setCreatetime(timeString);
            dictionarys.setUpdaterid(loginUser.getId());
            dictionarys.setUpdater(loginUser.getUsername());
            dictionarys.setUpdatetime(timeString);
            dictionarysService.insert(dictionarys);

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(dictionarys.getId());
            logs.setName("com.hchenpan.controller.DictionarysController.create");
            logs.setParams("com.hchenpan.pojo.Dictionarys类");
            logs.setDescription("新增数据字典大类");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(dictionarys));
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
        return ERROR;
    }
}