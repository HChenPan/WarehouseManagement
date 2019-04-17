package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.*;
import com.hchenpan.service.LogsService;
import com.hchenpan.service.UserService;
import com.hchenpan.service.WarehouseNumService;
import com.hchenpan.service.WarehouseNumUserService;
import com.hchenpan.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.WarehouseNumController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class WarehouseNumController extends BaseController {
    private final WarehouseNumService warehouseNumService;
    private final LogsService logsService;
    private final UserService userService;
    private final WarehouseNumUserService warehouseNumUserService;

    @Autowired
    public WarehouseNumController(WarehouseNumService warehouseNumService, LogsService logsService, UserService userService, WarehouseNumUserService warehouseNumUserService) {
        this.warehouseNumService = warehouseNumService;
        this.logsService = logsService;
        this.userService = userService;
        this.warehouseNumUserService = warehouseNumUserService;
    }

    @GetMapping("/warehousenum")
    public String warehousenum() {
        return View("/warehousenum/warehousenum");
    }

    /**
     * 功能:工程号维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/warehousenum/search")
    public String search(WarehouseNum warehouseNum) {
        Page<Map<String, Object>> page = getPage();
        Map<String, Object> params = new HashMap<>(10);
        if (StringUtil.notTrimEmpty(warehouseNum.getStockcode())) {
            params.put("stockcode", warehouseNum.getStockcode());
        }
        if (StringUtil.notTrimEmpty(warehouseNum.getStockname())) {
            params.put("stockname", warehouseNum.getStockname());
        }
        return jsonPage(warehouseNumService.getPage(page, params));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @PostMapping("/warehousenum/getallbyid")
    public String getallbyid(WarehouseNum warehouseNum) {
        return GetGsonString(warehouseNumService.selectById(warehouseNum.getId()));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @PostMapping("/warehousenum/getall")
    public String getall(WarehouseNum warehouseNum) {
        return ListToGson(warehouseNumService.selectList(new EntityWrapper<WarehouseNum>()
                        .eq("flag", "E")
                        .orderBy("stocktype")


                )
        );
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @PostMapping("/warehousenum/getckbyfhrid")
    public String getckbyfhrid(WarehouseNum warehouseNum) {
        return ListToGson(warehouseNumService.selectCKbyfhrid(warehouseNum.getFhrid()));
    }

    /**
     * 功能：新增仓库
     */
    @ResponseBody
    @PostMapping("/warehousenum/create")
    public String create(WarehouseNum warehouseNum) {
        if (checkuser()) {
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            warehouseNum.setId(getUUID());
            warehouseNum.setFlag("E");
            warehouseNum.setCreatorid(loginUser.getId());
            warehouseNum.setCreator(loginUser.getUsername());
            warehouseNum.setCreatetime(timeString);
            warehouseNum.setUpdaterid(loginUser.getId());
            warehouseNum.setUpdater(loginUser.getUsername());
            warehouseNum.setUpdatetime(timeString);

            //判断仓库名称是否存在
            if (0 < warehouseNumService.selectCount(new EntityWrapper<WarehouseNum>().eq("flag", "E").eq("stockname", warehouseNum.getStockname()))) {
                return "仓库名称已存在";
            } else {
                //判断仓库编码是否存在
                if (0 < warehouseNumService.selectCount(new EntityWrapper<WarehouseNum>().eq("flag", "E").eq("stockcode", warehouseNum.getStockcode()))) {
                    return "仓库编码已存在";
                }
            }
            if (StringUtil.isEmpty(warehouseNum.getFhr())) {
                //判断发货人是否为空
                warehouseNum.setSpusers(null);
                warehouseNum.setFhrzw("");
            } else {
                String fhrstemp = warehouseNum.getFhr().trim();
                String[] fhrs = fhrstemp.replace(" ", "").split(",");
                List<User> spuserList = userService.selectBatchIds(Arrays.asList(fhrs));
                StringBuilder fhrzw = new StringBuilder();
                for (User user : spuserList) {
                    fhrzw.append(user.getDepartment()).append("--").append(user.getRealname()).append(",");
                }
                String fhrzwzh = fhrzw.substring(0, fhrzw.length() - 1);
                warehouseNum.setFhrzw(fhrzwzh);
                Set<User> set = new HashSet<>(spuserList);
                warehouseNum.setSpusers(set);
                warehouseNum.setFhr(fhrstemp.replace(" ", ""));
            }
            warehouseNumService.insert(warehouseNum);

            if (!StringUtil.isEmpty(warehouseNum.getFhr())) {
                String fhrstemp = warehouseNum.getFhr().trim();
                String[] fhrs = fhrstemp.replace(" ", "").split(",");
                List<User> spuserList = userService.selectBatchIds(Arrays.asList(fhrs));
                for (User user : spuserList) {
                    WarehouseNumUser warehouseNumUser = new WarehouseNumUser();
                    warehouseNumUser.setId(getUUID());
                    warehouseNumUser.setUserid(user.getId());
                    warehouseNumUser.setWarehouseid(warehouseNum.getId());
                    warehouseNumUserService.insert(warehouseNumUser);
                }

            }

            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(warehouseNum.getId());
            logs.setName("com.hchenpan.controller.WarehouseNumController.create");
            logs.setParams("com.hchenpan.pojo.WarehouseNum类");
            logs.setDescription("新增仓库");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(warehouseNum));
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
     * 功能：更新仓库
     */
    @ResponseBody
    @PostMapping("/warehousenum/update")
    public String update(WarehouseNum warehouseNum) {
        if (checkuser()) {
            WarehouseNum Old = warehouseNumService.selectById(warehouseNum.getId());
            String oldcontent = GetGsonString(Old);
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            warehouseNum.setFlag("E");
            warehouseNum.setUpdaterid(loginUser.getId());
            warehouseNum.setUpdater(loginUser.getUsername());
            warehouseNum.setUpdatetime(timeString);
            // 判断仓库编码是否发生改变
            if (Old.getStockcode().equals(warehouseNum.getStockcode())) {
                //判断仓库名称是否发生改变
                if (!Old.getStockname().equals(warehouseNum.getStockname())) {
                    //判断仓库名称是否重复
                    if (warehouseNumService.selectCount(new EntityWrapper<WarehouseNum>().eq("flag", "E").eq("stocknamr", warehouseNum.getStockname())) > 0) {
                        return "仓库名称已存在";
                    }

                }
            } else {
                //判断仓库编码是否存在
                if (warehouseNumService.selectCount(new EntityWrapper<WarehouseNum>().eq("flag", "E").eq("stockcode", warehouseNum.getStockcode())) > 0) {
                    return "仓库编码已存在";
                } else {
                    //判断仓库名称是否发生改变
                    if (!Old.getStockname().equals(warehouseNum.getStockname())) {
                        //判断仓库名称是否重复
                        if (warehouseNumService.selectCount(new EntityWrapper<WarehouseNum>().eq("flag", "E").eq("stocknamr", warehouseNum.getStockname())) > 0) {
                            return "仓库名称已存在";
                        }

                    }
                }

            }
            //删除已有的仓库发货人
            warehouseNumUserService.delete(new EntityWrapper<WarehouseNumUser>().eq("warehouseid", warehouseNum.getId()));
            //判断发货人是否为空
            if (StringUtil.isTrimEmpty(warehouseNum.getFhr())) {
                warehouseNum.setSpusers(null);
                warehouseNum.setFhrzw("");
            } else {
                //添加修改后的仓库管理员
                String fhrstemp = warehouseNum.getFhr().trim();
                String[] fhrs = fhrstemp.replace(" ", "").split(",");
                List<User> spuserList = userService.selectBatchIds(Arrays.asList(fhrs));
                StringBuilder fhrzw = new StringBuilder();
                for (User user : spuserList) {
                    fhrzw.append(user.getDepartment()).append("--").append(user.getRealname()).append(",");
                    WarehouseNumUser warehouseNumUser = new WarehouseNumUser();
                    warehouseNumUser.setId(getUUID());
                    warehouseNumUser.setUserid(user.getId());
                    warehouseNumUser.setWarehouseid(warehouseNum.getId());
                    warehouseNumUserService.insert(warehouseNumUser);
                }
                String fhrzwzh = fhrzw.substring(0, fhrzw.length() - 1);
                warehouseNum.setFhrzw(fhrzwzh);
                Set<User> set = new HashSet<>(spuserList);
                warehouseNum.setSpusers(set);
                warehouseNum.setFhr(fhrstemp.replace(" ", ""));
            }
            //更新仓库
            warehouseNumService.updateById(warehouseNum);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(warehouseNum.getId());
            logs.setName("com.hchenpan.controller.WarehouseNumController.update");
            logs.setParams("com.hchenpan.pojo.WarehouseNum类");
            logs.setDescription("修改仓库");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(warehouseNum));
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
     * 功能:删除仓库
     */
    @ResponseBody
    @GetMapping("/warehousenum/delete")
    public String delete(WarehouseNum warehouseNum) {
        if (checkuser()) {
            WarehouseNum Old = warehouseNumService.selectById(warehouseNum.getId());
            String oldcontent = GetGsonString(Old);
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            warehouseNum.setFlag("D");
            warehouseNum.setUpdaterid(loginUser.getId());
            warehouseNum.setUpdater(loginUser.getUsername());
            warehouseNum.setUpdatetime(timeString);
            //删除已有的仓库发货人
            warehouseNumUserService.delete(new EntityWrapper<WarehouseNumUser>().eq("warehouseid", warehouseNum.getId()));
            //删除仓库
            warehouseNumService.deleteById(warehouseNum.getId());
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(warehouseNum.getId());
            logs.setName("com.hchenpan.controller.WarehouseNumController.delete");
            logs.setParams("com.hchenpan.pojo.WarehouseNum类");
            logs.setDescription("删除仓库");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(warehouseNum));
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