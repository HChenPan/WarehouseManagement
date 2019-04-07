package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.Supplier;
import com.hchenpan.pojo.User;
import com.hchenpan.service.LogsService;
import com.hchenpan.service.SupplierService;
import com.hchenpan.service.UserService;
import com.hchenpan.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.SupplierController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class SupplierController extends BaseController {
    private final SupplierService supplierService;
    private final LogsService logsService;
    private final UserService userService;

    @Autowired
    public SupplierController(SupplierService supplierService, LogsService logsService, UserService userService) {
        this.supplierService = supplierService;
        this.logsService = logsService;
        this.userService = userService;
    }

    @GetMapping("/supplier")
    public String supplier() {
        return View("/trade/supplier");
    }

    /**
     * 功能:供应商维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/supplier/search")
    public String search(Supplier supplier) {
        Page<Supplier> page = getPage();
        EntityWrapper<Supplier> ew = new EntityWrapper<>();
        ew.eq("flag", "E");
        if (StringUtil.notTrimEmpty(supplier.getSuppliercode())) {
            ew.like("suppliercode", supplier.getSuppliercode(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(supplier.getSuppliername())) {
            ew.like("suppliername", supplier.getSuppliername(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(supplier.getLegalrepresentative())) {
            ew.like("legalrepresentative", supplier.getLegalrepresentative(), SqlLike.DEFAULT);
        }
        return jsonPage(supplierService.selectPage(page, ew));
    }

    /**
     * 功能:提供所有供应商信息,供下拉
     */
    @ResponseBody
    @PostMapping("/supplier/getall")
    public String getall() {
        return GetGsonString(supplierService.selectList(new EntityWrapper<Supplier>().eq("flag", "E").orderBy("suppliername")));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/supplier/getbyid")
    public String getbyid(Supplier supplier) {
        return GetGsonString(supplierService.selectById(supplier.getId()));
    }

    /**
     * 功能：新增供应商
     */
    @ResponseBody
    @PostMapping("/supplier/create")
    public String create(Supplier supplier) {
        if (checkuser()) {
            //判断编码是否存在
            if (0 < supplierService.selectCount(new EntityWrapper<Supplier>().eq("flag", "E").eq("suppliercode", supplier.getSuppliercode()))) {
                return "供应商编码已存在";
            }
            //判断名称是否存在
            if (0 < supplierService.selectCount(new EntityWrapper<Supplier>().eq("flag", "E").eq("suppliername", supplier.getSuppliername()))) {
                return "供应商名称已存在";
            }
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            supplier.setId(getUUID());
            supplier.setFlag("E");
            supplier.setCreatorid(loginUser.getId());
            supplier.setCreator(loginUser.getUsername());
            supplier.setCreatetime(timeString);
            supplier.setUpdaterid(loginUser.getId());
            supplier.setUpdater(loginUser.getUsername());
            supplier.setUpdatetime(timeString);
            supplierService.insert(supplier);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(supplier.getId());
            logs.setName("com.hchenpan.controller.SupplierController.create");
            logs.setParams("com.hchenpan.pojo.Supplier类");
            logs.setDescription("新增供应商");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(supplier));
            /* 修改，需要保存修改前后的数据 */
            logs.setOldcontent(null);
            logs.setCreatorid(loginUser.getId());
            logs.setCreator(loginUser.getUsername());
            logs.setCreatetime(timeString);
            logs.setUpdater(loginUser.getUsername());
            logs.setRealname(loginUser.getRealname());
            logs.setUpdatetime(timeString);
            logsService.insert(logs);
            return SUCCESS;
        }
        return ERROR;
    }

    /**
     * 功能：更新供应商
     */
    @ResponseBody
    @PostMapping("/supplier/update")
    public String update(Supplier supplier) {
        if (checkuser()) {
            Supplier old = supplierService.selectById(supplier.getId());
            String oldcontent = GetGsonString(old);
            //判断编码是否修改
            if (old.getSuppliercode().equals(supplier.getSuppliercode())) {
                //判断名称是否修改
                if (!old.getSuppliername().equals(supplier.getSuppliername())) {
                    //判断名称是否重复
                    if (0 < supplierService.selectCount(new EntityWrapper<Supplier>().eq("flag", "E").eq("suppliername", supplier.getSuppliername()))) {
                        return "供应商名称已存在";
                    }  //编码未变名称不重复 更新
                }  //编码和名称都未修改 更新
            } else {
                //编码修改，判断是否编码重复
                if (0 < supplierService.selectCount(new EntityWrapper<Supplier>().eq("flag", "E").eq("suppliercode", supplier.getSuppliercode()))) {
                    return "供应商编码已存在";
                } else {
                    //判断名称是否修改
                    if (!old.getSuppliername().equals(supplier.getSuppliername())) {
                        //判断名称是否重复
                        if (0 < supplierService.selectCount(new EntityWrapper<Supplier>().eq("flag", "E").eq("suppliername", supplier.getSuppliername()))) {
                            return "供应商名称已存在";
                        }  //编码和名称均修改且不重复 更新
                    }  //编码不重复名称未修改 更新
                }
            }
            //更新
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            supplier.setFlag("E");
            supplier.setUpdaterid(loginUser.getId());
            supplier.setUpdater(loginUser.getUsername());
            supplier.setUpdatetime(timeString);
            supplierService.updateById(supplier);
            //写日志
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(supplier.getId());
            logs.setName("com.hchenpan.controller.SupplierController.update");
            logs.setParams("com.hchenpan.pojo.Supplier类");
            logs.setDescription("修改供应商");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(supplier));
            /* 修改，需要保存修改前后的数据 */
            logs.setOldcontent(oldcontent);
            logs.setCreatorid(loginUser.getId());
            logs.setCreator(loginUser.getUsername());
            logs.setCreatetime(timeString);
            logs.setUpdater(loginUser.getUsername());
            logs.setRealname(loginUser.getRealname());
            logs.setUpdatetime(timeString);
            logsService.insert(logs);
            return SUCCESS;
        }
        return ERROR;
    }

    /**
     * 功能:删除供应商
     */
    @ResponseBody
    @GetMapping("/supplier/delete")
    public String delete(Supplier supplier) {
        if (checkuser()) {
            Supplier old = supplierService.selectById(supplier.getId());
            String oldcontent = GetGsonString(old);
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            supplier.setFlag("D");
            supplier.setUpdaterid(loginUser.getId());
            supplier.setUpdater(loginUser.getUsername());
            supplier.setUpdatetime(timeString);
            supplierService.deleteById(supplier.getId());
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(supplier.getId());
            logs.setName("com.hchenpan.controller.SupplierController.delete");
            logs.setParams("com.hchenpan.pojo.Supplier类");
            logs.setDescription("修改供应商");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(supplier));
            /* 修改，需要保存修改前后的数据 */
            logs.setOldcontent(oldcontent);
            logs.setCreatorid(loginUser.getId());
            logs.setCreator(loginUser.getUsername());
            logs.setCreatetime(timeString);
            logs.setUpdater(loginUser.getUsername());
            logs.setRealname(loginUser.getRealname());
            logs.setUpdatetime(timeString);
            logsService.insert(logs);
            return SUCCESS;
        }
        return ERROR;
    }
}