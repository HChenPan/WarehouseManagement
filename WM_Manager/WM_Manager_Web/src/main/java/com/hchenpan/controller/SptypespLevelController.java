package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.SptypespLevel;
import com.hchenpan.pojo.SptypespLevelUser;
import com.hchenpan.pojo.User;
import com.hchenpan.service.LogsService;
import com.hchenpan.service.SptypespLevelService;
import com.hchenpan.service.SptypespLevelUserService;
import com.hchenpan.service.UserService;
import com.hchenpan.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.SptypespLevelController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class SptypespLevelController extends BaseController {
    private final SptypespLevelService sptypespLevelService;
    private final LogsService logsService;
    private final UserService userService;
    private final SptypespLevelUserService sptypespLevelUserService;

    @Autowired
    public SptypespLevelController(SptypespLevelService sptypespLevelService, LogsService logsService, UserService userService, SptypespLevelUserService sptypespLevelUserService) {
        this.sptypespLevelService = sptypespLevelService;
        this.logsService = logsService;
        this.userService = userService;
        this.sptypespLevelUserService = sptypespLevelUserService;
    }

    @GetMapping("/sptypeSplevel")
    public String dictionary() {
        return View("/basicdata/sptypeSplevel");
    }

    /**
     * 功能:提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/sptypesplevel/search")
    public String search(SptypespLevel sptypeSplevel) {
        Page<SptypespLevel> page = getPage();
        return GetGsonString(sptypespLevelService.selectSPPage(page, sptypeSplevel));
    }

    /**
     * 新建、保存 审批 配置
     ***/
    @ResponseBody
    @PostMapping(value = "/sptypesplevel/create")
    public String create(SptypespLevel sptypespLevel) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            sptypespLevel.setId(getUUID());
            sptypespLevel.setFlag("E");
            sptypespLevel.setCreatorid(loginUser.getId());
            sptypespLevel.setCreator(loginUser.getUsername());
            sptypespLevel.setCreatetime(timeString);
            sptypespLevel.setUpdaterid(loginUser.getId());
            sptypespLevel.setUpdater(loginUser.getUsername());
            sptypespLevel.setUpdatetime(timeString);
            if (StringUtil.isEmpty(sptypespLevel.getSpusersid())) {
                sptypespLevel.setSpusers(null);
                sptypespLevel.setSpuserszw("");
                sptypespLevelService.insert(sptypespLevel);
            } else {
                String spusersIdstemp = sptypespLevel.getSpusersid().trim();
                String[] spusersIds = spusersIdstemp.replace(" ", "").split(",");

                List<User> spuserList = userService.selectBatchIds(Arrays.asList(spusersIds));
                StringBuilder fhrzw = new StringBuilder();
                for (User user : spuserList) {
                    fhrzw.append(user.getDepartment()).append("--").append(user.getRealname()).append(",");
                    SptypespLevelUser sptypespLevelUser = new SptypespLevelUser();
                    sptypespLevelUser.setId(getUUID());
                    sptypespLevelUser.setSptypesplevelid(sptypespLevel.getId());
                    sptypespLevelUser.setUserid(user.getId());
                    sptypespLevelUserService.insert(sptypespLevelUser);
                }
                String fhrzwzh = fhrzw.substring(0, fhrzw.length() - 1);
                sptypespLevel.setSpuserszw(fhrzwzh);
                Set<User> set = new HashSet<>(spuserList);
                sptypespLevel.setSpusers(set);
                sptypespLevel.setSpusersid(spusersIdstemp.replace(" ", ""));
                sptypespLevelService.insert(sptypespLevel);
            }
            return SUCCESS;
        }
        return ERROR;
    }

    /**
     * 功能:审批类型审批级别配置
     */
    @ResponseBody
    @PostMapping(value = "/sptypesplevel/update")
    public String update(SptypespLevel sptypespLevel) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            sptypespLevel.setFlag("E");
            sptypespLevel.setCreatorid(loginUser.getId());
            sptypespLevel.setCreator(loginUser.getUsername());
            sptypespLevel.setCreatetime(timeString);
            sptypespLevel.setUpdaterid(loginUser.getId());
            sptypespLevel.setUpdater(loginUser.getUsername());
            sptypespLevel.setUpdatetime(timeString);
            sptypespLevelUserService.delete(new EntityWrapper<SptypespLevelUser>().eq("sptypesplevelid", sptypespLevel.getId()));
            if (StringUtil.isEmpty(sptypespLevel.getSpusersid())) {
                sptypespLevel.setSpusers(null);
                sptypespLevel.setSpuserszw("");
                sptypespLevelService.updateById(sptypespLevel);
            } else {
                String spusersIdstemp = sptypespLevel.getSpusersid().trim();
                String[] spusersIds = spusersIdstemp.replace(" ", "").split(",");

                List<User> spuserList = userService.selectBatchIds(Arrays.asList(spusersIds));
                StringBuilder fhrzw = new StringBuilder();
                for (User user : spuserList) {
                    fhrzw.append(user.getDepartment()).append("--").append(user.getRealname()).append(",");
                }
                String fhrzwzh = fhrzw.substring(0, fhrzw.length() - 1);
                sptypespLevel.setSpuserszw(fhrzwzh);
                System.out.println(spuserList.get(0).getId());
                Set<User> set = new HashSet<>(spuserList);
                sptypespLevel.setSpusers(set);
                sptypespLevel.setSpusersid(spusersIdstemp.replace(" ", ""));
                sptypespLevelService.updateById(sptypespLevel);
                for (User user : spuserList) {
                    SptypespLevelUser sptypespLevelUser = new SptypespLevelUser();
                    sptypespLevelUser.setId(getUUID());
                    sptypespLevelUser.setSptypesplevelid(sptypespLevel.getId());
                    sptypespLevelUser.setUserid(user.getId());
                    sptypespLevelUserService.insert(sptypespLevelUser);
                }
            }
            return SUCCESS;
        }
        return ERROR;
    }

    /**
     * 功能:删除审批级别配置
     */
    @ResponseBody
    @PostMapping(value = "/sptypesplevel/delete")
    public String delete(SptypespLevel sptypespLevel) {
        if (checkuser()) {
            sptypespLevelUserService.delete(new EntityWrapper<SptypespLevelUser>().eq("sptypesplevelid", sptypespLevel.getId()));
            sptypespLevelService.deleteById(sptypespLevel.getId());
        }
        return ERROR;
    }
}