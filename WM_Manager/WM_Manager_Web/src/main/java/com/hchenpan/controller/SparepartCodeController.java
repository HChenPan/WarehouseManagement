package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hchenpan.common.BaseController;
import com.hchenpan.model.PageContainer;
import com.hchenpan.model.Spart;
import com.hchenpan.pojo.Dictionaryschild;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.SparepartCode;
import com.hchenpan.pojo.User;
import com.hchenpan.service.LogsService;
import com.hchenpan.service.SparepartCodeService;
import com.hchenpan.service.impl.DictionaryschildServiceImpl;
import com.hchenpan.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.SparepartCodeController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class SparepartCodeController extends BaseController {
    private final SparepartCodeService sparepartCodeService;
    private final DictionaryschildServiceImpl dictionaryschildService;
    private final LogsService logsService;

    @Autowired
    public SparepartCodeController(SparepartCodeService sparepartCodeService, DictionaryschildServiceImpl dictionaryschildService, LogsService logsService) {
        this.sparepartCodeService = sparepartCodeService;
        this.dictionaryschildService = dictionaryschildService;
        this.logsService = logsService;
    }

    @GetMapping("/sparepartcode")
    public String sparepartCode() {
        return View("/basicdata/sparepartCodeTree");
    }

    /**
     * 获取根节点
     */
    @ResponseBody
    @RequestMapping(value = "/sparepartcode/getrootlist")
    public String getrootlist(SparepartCode sparepartCode) {
        if (StringUtil.isTrimEmpty(sparepartCode.getId())) {
            PageContainer<Spart> slist = sparepartCodeService.getroot();
            return GetGsonString(slist);
        } else {
            Spart spart = new Spart();
            spart.setId(sparepartCode.getId());
            List<Spart> slist = sparepartCodeService.getsons(spart);
            return GetGsonString(slist);
        }
    }

    /**
     * 取单条数据编辑
     */
    @ResponseBody
    @GetMapping(value = "/sparepartcode/getallbyid")
    public String getallbyid(SparepartCode sparepartCode) {
        return GetGsonString(sparepartCodeService.selectById(sparepartCode.getId()));
    }

    /**
     * 取单条数据编辑
     */
    @ResponseBody
    @PostMapping(value = "/sparepartcode/getallwz")
    public String getallwz() {
        List<SparepartCode> slist = sparepartCodeService.getallsparepart();
        return GetGsonString(slist);
    }

    /**
     * 功能取子节点数据
     */
    @ResponseBody
    @GetMapping(value = "/sparepartcode/getsonlist")
    public String getsonlist(SparepartCode sparepartCode) throws Exception {
        Spart spart = new Spart();
        spart.setId(sparepartCode.getId());
        List<Spart> slist = sparepartCodeService.getsons(spart);
        return GetGsonString(slist);
    }


    /**
     * 新建、保存物资编码
     ***/
    @ResponseBody
    @PostMapping(value = "/sparepartcode/create")
    public String create(SparepartCode sparepartCode) {
        if (checkuser()) {
            sparepartCode.setParentid(sparepartCode.get_parentid());
            sparepartCode.setName(sparepartCode.getName().trim());
            if ("物资".equals(sparepartCode.getDescription())) {
                String temp = sparepartCode.getParentcode() + sparepartCode.getDevicecode();
                sparepartCode.setCode(temp);
            }
            // 判断编码是否重复
            int codenum = sparepartCodeService.selectCount(new EntityWrapper<SparepartCode>().eq("code", sparepartCode.getCode().trim()).eq("description", sparepartCode.getDescription().trim()).eq("parentcode", sparepartCode.getParentcode().trim()));
            if (codenum > 0) {
                return "备件已经存在";
            } else {
                List<Dictionaryschild> list = dictionaryschildService.getdchildlistbydecode("BZ");
                sparepartCode.setCurrencyunit(list.get(0).getName().trim());
                sparepartCode.setCurrencytype(list.get(0).getCode().trim());
                /*通用字段赋值*/
                User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                String timeString = GetCurrentTime();
                sparepartCode.setId(getUUID());
                sparepartCode.setCreatorid(loginUser.getId());
                sparepartCode.setCreator(loginUser.getUsername());
                sparepartCode.setCreatetime(timeString);
                sparepartCode.setUpdaterid(loginUser.getId());
                sparepartCode.setUpdater(loginUser.getUsername());
                sparepartCode.setUpdatetime(timeString);
                sparepartCodeService.insert(sparepartCode);
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(sparepartCode.getId());
                logs.setName("com.hchenpan.controller.SparepartCodeController.create");
                logs.setParams("com.hchenpan.pojo.SparepartCode类");
                logs.setDescription("新增备件");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(sparepartCode));
                logs.setCreatorid(loginUser.getId());
                logs.setCreator(loginUser.getUsername());
                logs.setCreatetime(timeString);
                logs.setRealname(loginUser.getRealname());
                logs.setUpdater(loginUser.getUsername());
                logs.setUpdatetime(timeString);
                logs.setId(getUUID());
                logsService.insert(logs);
                return GetGsonString(sparepartCode);
            }
        }
        return ERROR;
    }

    /**
     * 功能:修改物资编码
     */
    @ResponseBody
    @PostMapping(value = "/sparepartcode/update")
    public String update(SparepartCode sparepartCode) {
        if (checkuser()) {
            sparepartCode.setParentid(sparepartCode.get_parentid());
            SparepartCode d = sparepartCodeService.selectById(sparepartCode.getId());
            String oldcode = d.getCode();
            String olddescription = d.getDescription();
            String oldcontent = GetGsonString(d);
            String temp;
            if (sparepartCode.getDescription().equals("物资")) {
                temp = sparepartCode.getParentcode() + sparepartCode.getDevicecode();
                sparepartCode.setCode(temp);
            }
            if (oldcode.equals(sparepartCode.getCode()) && olddescription.equals(sparepartCode.getDescription())) {

                List<Dictionaryschild> list = dictionaryschildService.getdchildlistbydecode("BZ");
                sparepartCode.setCurrencyunit(list.get(0).getName().trim());
                sparepartCode.setCurrencytype(list.get(0).getCode().trim());
                /*通用字段修改*/
                User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                String timeString = GetCurrentTime();
                sparepartCode.setUpdaterid(loginUser.getId());
                sparepartCode.setUpdater(loginUser.getUsername());
                sparepartCode.setUpdatetime(timeString);
                sparepartCodeService.updateById(sparepartCode);
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(sparepartCode.getId());
                logs.setName("com.hchenpan.controller.SparepartCodeController.update");
                logs.setParams("com.hchenpan.pojo.SparepartCode类");
                logs.setDescription("修改备件");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(sparepartCode));
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
                return GetGsonString(sparepartCode);
            } else {
                // 判断编码是否重复
                // 判断编码是否重复
                int codenum = sparepartCodeService.selectCount(new EntityWrapper<SparepartCode>().eq("code", sparepartCode.getCode().trim()).eq("description", sparepartCode.getDescription().trim()).eq("parentcode", sparepartCode.getParentcode().trim()));
                if (codenum > 0) {
                    return "备件已经存在";
                } else {
                    /*通用字段修改*/
                    User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                    String timeString = GetCurrentTime();
                    sparepartCode.setUpdaterid(loginUser.getId());
                    sparepartCode.setUpdater(loginUser.getUsername());
                    sparepartCode.setUpdatetime(timeString);
                    sparepartCodeService.updateById(sparepartCode);
                    //写入日志表
                    Logs logs = new Logs();
                    logs.setFlagid(sparepartCode.getId());
                    logs.setName("com.hchenpan.controller.SparepartCodeController.update");
                    logs.setParams("com.hchenpan.pojo.SparepartCode类");
                    logs.setDescription("修改备件");
                    logs.setUpdaterid(loginUser.getId());
                    logs.setIpaddress(getRomoteIP());
                    logs.setOptcontent(GetGsonString(sparepartCode));
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
                    return GetGsonString(sparepartCode);
                }
            }
        }
        return ERROR;
    }

    /**
     * 功能:删除物资编码
     */
    @ResponseBody
    @GetMapping(value = "/sparepartcode/delete")
    public String delete(SparepartCode sparepartCode) {
        if (checkuser()) {
            sparepartCode.setParentid(sparepartCode.get_parentid());
            SparepartCode sparepartCodeOld = sparepartCodeService.selectById(sparepartCode.getId());
            String oldcontent = GetGsonString(sparepartCodeOld);
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            sparepartCodeOld.setUpdaterid(loginUser.getId());
            sparepartCodeOld.setUpdatetime(timeString);
            sparepartCodeOld.setUpdater(loginUser.getUsername());
            String id = sparepartCode.get_parentid();
            sparepartCodeService.deleteById(sparepartCodeOld.getId());

            id = sparepartCodeService.getexpandid(id);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(sparepartCodeOld.getId());
            logs.setName("com.hchenpan.controller.SparepartCodeController.update");
            logs.setParams("com.hchenpan.pojo.SparepartCode类");
            logs.setDescription("删除备件");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(sparepartCodeOld));
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
            return GetGsonString(sparepartCode);
        }
        return ERROR;
    }
}