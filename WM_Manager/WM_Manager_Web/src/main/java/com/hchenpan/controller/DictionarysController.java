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
     * 根据ID查找对象
     */
    @ResponseBody
    @RequestMapping(value = "/dictionarys/getdictionarylistbyid")
    public String getdictionarylistbyid(Dictionarys dictionarys) {
        return GetGsonString(dictionarysService.selectById(dictionarys.getId()));
    }

    /**
     * 新增数据字典大类
     */
    @ResponseBody
    @RequestMapping(value = "/dictionarys/create")
    public String create(Dictionarys dictionarys) {
        if (checkuser()) {
            EntityWrapper<Dictionarys> ew = new EntityWrapper<>();
            ew.eq("dcode", dictionarys.getDcode());
            ew.eq("flag", "E");
            int count = dictionarysService.selectCount(ew);
            if (count == 0) {
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
        return ERROR;
    }

    /**
     * 修改对象
     **/
    @ResponseBody
    @RequestMapping(value = "/dictionarys/update")
    public String update(Dictionarys dictionarys) {
        if (checkuser()) {
            Dictionarys dictionarysOld = dictionarysService.selectById(dictionarys.getId());
            String oldcontent = GetGsonString(dictionarysOld);
            EntityWrapper<Dictionarys> ew = new EntityWrapper<>();
            ew.eq("flag", "E");
            ew.eq("dcode", dictionarys.getDcode());
            int count = dictionarysService.selectCount(ew);
            if (count == 1) {
                User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");

                dictionarys.setFlag("E");
                String timeString = GetCurrentTime();


                dictionarys.setUpdaterid(loginUser.getId());
                dictionarys.setUpdater(loginUser.getUsername());
                dictionarys.setUpdatetime(timeString);
                dictionarysService.updateById(dictionarys);

                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(dictionarys.getId());
                logs.setName("com.hchenpan.controller.DictionarysController.update");
                logs.setParams("com.hchenpan.pojo.Dictionarys类");
                logs.setDescription("修改数据字典大类");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(dictionarys));
                /* 修改，需要保存修改前后的数据 */
                logs.setOldcontent(oldcontent);
                logs.setCreatorid(loginUser.getId());
                logs.setCreator(loginUser.getUsername());
                logs.setCreatetime(timeString);
                logs.setUpdater(loginUser.getUsername());
                logs.setRealname(loginUser.getRealname());
                logs.setUpdatetime(timeString);
                logs.setId(getUUID());
                logsService.insert(logs);
                return SUCCESS;
            }
            return ERROR;
        }
        return ERROR;
    }

    /**
     * 删除对象
     ***/
    @ResponseBody
    @RequestMapping(value = "/dictionarys/delete")
    public String delete(Dictionarys dictionarys) {
        if (checkuser()) {
            Dictionarys deleteDictionarys = dictionarysService.selectById(dictionarys.getId());
            String oldcontent = GetGsonString(deleteDictionarys);
            deleteDictionarys.setFlag("D");
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            deleteDictionarys.setUpdaterid(loginUser.getId());
            deleteDictionarys.setUpdatetime(timeString);
            deleteDictionarys.setUpdater(loginUser.getUsername());
            dictionarysService.deleteById(deleteDictionarys);


            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(dictionarys.getId());
            logs.setName("com.hchenpan.controller.DictionarysController.delete");
            logs.setParams("com.hchenpan.pojo.Dictionarys类");
            logs.setDescription("删除数据字典大类");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(dictionarys));
            /* 修改，需要保存修改前后的数据 */
            logs.setCreator(loginUser.getUsername());
            logs.setOldcontent(oldcontent);
            logs.setCreatorid(loginUser.getId());
            logs.setCreatetime(timeString);
            logs.setUpdatetime(timeString);
            logs.setUpdater(loginUser.getUsername());
            logs.setRealname(loginUser.getRealname());
            logs.setId(getUUID());
            logsService.insert(logs);
            return SUCCESS;
        }
        return ERROR;
    }
}