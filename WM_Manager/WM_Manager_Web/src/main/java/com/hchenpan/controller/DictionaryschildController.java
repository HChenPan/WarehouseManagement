package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.Dictionaryschild;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.User;
import com.hchenpan.service.DictionaryschildService;
import com.hchenpan.service.LogsService;
import com.hchenpan.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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
    private final LogsService logsService;

    @Autowired
    public DictionaryschildController(DictionaryschildService dictionaryschildService, LogsService logsService) {
        this.dictionaryschildService = dictionaryschildService;
        this.logsService = logsService;
    }

    /**
     * 提供查询的分页数据
     */
    @ResponseBody
    @RequestMapping(value = "/dictionaryschild/search")
    public String search(Dictionaryschild dictionaryschild) {
        Page<Map<String, Object>> page = getPage();
        Map<String, Object> params = new HashMap<>(10);

        if (StringUtil.notTrimEmpty(dictionaryschild.getDcode())) {
            params.put("dcode", dictionaryschild.getDcode());
        }

        if (StringUtil.notTrimEmpty(dictionaryschild.getName())) {
            params.put("name", dictionaryschild.getName());
        }
        return jsonPage(dictionaryschildService.getChildPage(page, params));
    }

    /**
     * 数据新增
     **/
    @ResponseBody
    @PostMapping(value = "/dictionaryschild/create")
    public String create(Dictionaryschild dictionaryschild) {
        if (checkuser()) {
            if (dictionaryschildService.selectCount(new EntityWrapper<Dictionaryschild>().eq("dcode", dictionaryschild.getDcode()).eq("code", dictionaryschild.getCode()).eq("flag", "E")) > 0) {
                return "子类编码已存在";
            } else {
                //判断描述是否重复
                EntityWrapper<Dictionaryschild> ew = new EntityWrapper<>();
                ew.eq("dcode", dictionaryschild.getDcode());
                ew.eq("name", dictionaryschild.getName());
                ew.eq("flag", "E");
                if (dictionaryschildService.selectCount(new EntityWrapper<Dictionaryschild>().eq("dcode", dictionaryschild.getDcode()).eq("name", dictionaryschild.getName()).eq("flag", "E")) > 0) {
                    return "子类描述已经存在";
                } else {
                    if ("BZ".equals(dictionaryschild.getDcode())) {
                        if (dictionaryschildService.selectCount(new EntityWrapper<Dictionaryschild>().eq("dcode", dictionaryschild.getDcode()).eq("flag", "E")) > 0) {
                            return "唯一币种已经存在无法添加";
                        } else {
                            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                            dictionaryschild.setId(getUUID());
                            dictionaryschild.setFlag("E");
                            String timeString = GetCurrentTime();
                            dictionaryschild.setCreatorid(loginUser.getId());
                            dictionaryschild.setUpdater(loginUser.getUsername());
                            dictionaryschild.setCreatetime(timeString);
                            dictionaryschild.setCreator(loginUser.getUsername());
                            dictionaryschild.setUpdaterid(loginUser.getId());
                            dictionaryschild.setUpdatetime(timeString);
                            dictionaryschildService.insert(dictionaryschild);
                            //写入日志表
                            Logs logs = new Logs();
                            logs.setFlagid(dictionaryschild.getId());
                            logs.setName("com.hchenpan.controller.DictionaryschildController.create");
                            logs.setParams("com.hchenpan.pojo.Dictionaryschild类");
                            logs.setDescription("新增数据字典子类");
                            logs.setUpdaterid(loginUser.getId());
                            logs.setIpaddress(getRomoteIP());
                            logs.setOptcontent(GetGsonString(dictionaryschild));
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
                    } else {
                        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                        dictionaryschild.setId(getUUID());
                        dictionaryschild.setFlag("E");
                        String timeString = GetCurrentTime();
                        dictionaryschild.setCreatorid(loginUser.getId());
                        dictionaryschild.setUpdater(loginUser.getUsername());
                        dictionaryschild.setCreatetime(timeString);
                        dictionaryschild.setCreator(loginUser.getUsername());
                        dictionaryschild.setUpdaterid(loginUser.getId());
                        dictionaryschild.setUpdatetime(timeString);
                        dictionaryschildService.insert(dictionaryschild);
                        //写入日志表
                        Logs logs = new Logs();
                        logs.setFlagid(dictionaryschild.getId());
                        logs.setName("com.hchenpan.controller.DictionaryschildController.create");
                        logs.setParams("com.hchenpan.pojo.Dictionaryschild类");
                        logs.setDescription("新增数据字典子类");
                        logs.setUpdaterid(loginUser.getId());
                        logs.setIpaddress(getRomoteIP());
                        logs.setOptcontent(GetGsonString(dictionaryschild));
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
                }
            }
        }
        return ERROR;
    }

    /**
     * 数据字典子类修改
     */
    @ResponseBody
    @PostMapping(value = "/dictionaryschild/update")
    public String update(Dictionaryschild dictionaryschild) {
        if (checkuser()) {
            Dictionaryschild dcOld = dictionaryschildService.selectById(dictionaryschild.getId());
            String oldcontent = GetGsonString(dcOld);
            if (dcOld.getCode().trim().equals(dictionaryschild.getCode().trim()) && dcOld.getName().trim().equals(dictionaryschild.getName().trim())) {
                User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                dictionaryschild.setFlag("E");
                String timeString = GetCurrentTime();
                dictionaryschild.setUpdaterid(loginUser.getId());
                dictionaryschild.setUpdater(loginUser.getUsername());
                dictionaryschild.setUpdatetime(timeString);
                dictionaryschildService.updateById(dictionaryschild);
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(dictionaryschild.getId());
                logs.setName("com.hchenpan.controller.DictionaryschildController.update");
                logs.setParams("com.hchenpan.pojo.Dictionaryschild类");
                logs.setDescription("修改数据字典子类");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(dictionaryschild));
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
            } else if (!dcOld.getCode().trim().equals(dictionaryschild.getCode().trim()) && dcOld.getName().trim().equals(dictionaryschild.getName().trim())) {
                //判断编码是否重复
                if (dictionaryschildService.selectCount(new EntityWrapper<Dictionaryschild>().eq("dcode", dictionaryschild.getDcode()).eq("code", dictionaryschild.getCode()).eq("flag", "E")) > 0) {
                    return "子类编码已存在";
                } else {
                    User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                    dictionaryschild.setFlag("E");
                    String timeString = GetCurrentTime();
                    dictionaryschild.setUpdaterid(loginUser.getId());
                    dictionaryschild.setUpdater(loginUser.getUsername());
                    dictionaryschild.setUpdatetime(timeString);
                    dictionaryschildService.updateById(dictionaryschild);
                    //写入日志表
                    Logs logs = new Logs();
                    logs.setFlagid(dictionaryschild.getId());
                    logs.setName("com.hchenpan.controller.DictionaryschildController.update");
                    logs.setParams("com.hchenpan.pojo.Dictionaryschild类");
                    logs.setDescription("修改数据字典子类");
                    logs.setUpdaterid(loginUser.getId());
                    logs.setIpaddress(getRomoteIP());
                    logs.setOptcontent(GetGsonString(dictionaryschild));
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
            } else if (!dcOld.getName().trim().equals(dictionaryschild.getName().trim()) && dcOld.getCode().trim().equals(dictionaryschild.getCode().trim())) {
                if (dictionaryschildService.selectCount(new EntityWrapper<Dictionaryschild>().eq("dcode", dictionaryschild.getDcode()).eq("name", dictionaryschild.getName()).eq("flag", "E")) > 0) {
                    return "子类描述已经存在";
                } else {
                    User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                    dictionaryschild.setFlag("E");
                    String timeString = GetCurrentTime();
                    dictionaryschild.setUpdaterid(loginUser.getId());
                    dictionaryschild.setUpdater(loginUser.getUsername());
                    dictionaryschild.setUpdatetime(timeString);
                    dictionaryschildService.updateById(dictionaryschild);
                    //写入日志表
                    Logs logs = new Logs();
                    logs.setFlagid(dictionaryschild.getId());
                    logs.setName("com.hchenpan.controller.DictionaryschildController.update");
                    logs.setParams("com.hchenpan.pojo.Dictionaryschild类");
                    logs.setDescription("修改数据字典子类");
                    logs.setUpdaterid(loginUser.getId());
                    logs.setIpaddress(getRomoteIP());
                    logs.setOptcontent(GetGsonString(dictionaryschild));
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
            }
        }
        return ERROR;
    }

    /**
     * 数据字典子类删除
     */
    @ResponseBody
    @GetMapping(value = "/dictionaryschild/delete")
    public String delete(Dictionaryschild dictionaryschild) {
        if (checkuser()) {
            Dictionaryschild dcOld = dictionaryschildService.selectById(dictionaryschild.getId());
            String oldcontent = GetGsonString(dcOld);
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            dcOld.setFlag("D");
            String timeString = GetCurrentTime();
            dcOld.setUpdaterid(loginUser.getId());
            dcOld.setUpdater(loginUser.getUsername());
            dcOld.setUpdatetime(timeString);
            dictionaryschildService.updateById(dcOld);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(dictionaryschild.getId());
            logs.setName("com.hchenpan.controller.DictionaryschildController.delete");
            logs.setParams("com.hchenpan.pojo.Dictionaryschild类");
            logs.setDescription("删除数据字典子类");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(dcOld));
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

    /**
     * 根据id数据字典子类
     **/
    @ResponseBody
    @RequestMapping(value = "/dictionaryschild/getdictionarychildlistbyid")
    public String getdictionarychildlistbyid(Dictionaryschild dictionaryschild) {
        return GetGsonString(dictionaryschildService.selectchildList(dictionaryschild.getId()));
    }

    /**
     * 根据数据字典大类编码查找设备种类
     **/
    @ResponseBody
    @RequestMapping(value = "/dictionaryschild/getsbzltypelistbydecode")
    public String getsbzltypelistbydecode() {
        return GetGsonString(dictionaryschildService.selectList(new EntityWrapper<Dictionaryschild>().eq("dcode", "SBZL").eq("flag", "E")));
    }

    /**
     * 功能:根据数据字典大类编码查找使审批类型
     */
    @ResponseBody
    @PostMapping("/dictionaryschild/getSptypelistbydecode")
    public String getSptypelistbydecode() {
        return GetGsonString(dictionaryschildService.selectList(new EntityWrapper<Dictionaryschild>().eq("dcode", "ST2001").eq("flag", "E")));
    }

    /**
     * 功能:根据数据字典大类编码查找审批级别
     */
    @ResponseBody
    @PostMapping("/dictionaryschild/getSpjblistbydecode")
    public String getSpjblistbydecode() {
        return GetGsonString(dictionaryschildService.selectList(new EntityWrapper<Dictionaryschild>().eq("dcode", "SPJB").eq("flag", "E")));
    }

    /**
     * 功能:根据数据字典大类编码查找仓库编码
     */
    @ResponseBody
    @PostMapping("/dictionaryschild/getcklistbydecode")
    public String getcklistbydecode() {
        return GetGsonString(dictionaryschildService.selectList(new EntityWrapper<Dictionaryschild>().eq("dcode", "ST4001").eq("flag", "E")));
    }

    /**
     * 功能:根据数据字典大类编码查找采购计划大类
     */
    @ResponseBody
    @PostMapping("/dictionaryschild/getbuytypelistbydecode")
    public String getbuytypelistbydecode() {
        return GetGsonString(dictionaryschildService.selectList(new EntityWrapper<Dictionaryschild>().eq("dcode", "ST2001").eq("note", "BUY").eq("flag", "E")));
    }

    /**
     * 功能:根据数据字典大类编码查找合同类型
     */
    @ResponseBody
    @PostMapping("/dictionaryschild/getcontracttypelistbydecode")
    public String getcontracttypelistbydecode() {
        return GetGsonString(dictionaryschildService.selectList(new EntityWrapper<Dictionaryschild>().eq("dcode", "ST3010").eq("flag", "E")));
    }

    /**
     * 功能:根据数据字典大类编码查找合同审批类型
     */
    @ResponseBody
    @PostMapping("/dictionaryschild/getSplistbydecode")
    public String getSplistbydecode() {
        return GetGsonString(dictionaryschildService.selectList(new EntityWrapper<Dictionaryschild>().eq("dcode", "ST2001").eq("note", "CT").eq("flag", "E")));
    }
}