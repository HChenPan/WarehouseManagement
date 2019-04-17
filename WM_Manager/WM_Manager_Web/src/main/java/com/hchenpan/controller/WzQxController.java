package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.User;
import com.hchenpan.pojo.WzQx;
import com.hchenpan.pojo.WzQxUser;
import com.hchenpan.service.LogsService;
import com.hchenpan.service.UserService;
import com.hchenpan.service.WzQxService;
import com.hchenpan.service.WzQxUserService;
import com.hchenpan.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.WzQxController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class WzQxController extends BaseController {
    private final WzQxService wzQxService;
    private final WzQxUserService wzQxUserService;
    private final LogsService logsService;
    private final UserService userService;

    @Autowired
    public WzQxController(WzQxService wzQxService, WzQxUserService wzQxUserService, LogsService logsService, UserService userService) {
        this.wzQxService = wzQxService;
        this.wzQxUserService = wzQxUserService;
        this.logsService = logsService;
        this.userService = userService;
    }

    @GetMapping("/wzqx")
    public String wzqx() {
        return View("/basicdata/wzqx");
    }

    /**
     * 功能:物资权限维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/wzqx/search")
    public String search(WzQx wzqx) {
        Page<WzQx> page = getPage();
        EntityWrapper<WzQx> ew = new EntityWrapper<>();
        ew.eq("flag", "E");
        if (StringUtil.notTrimEmpty(wzqx.getWzqz())) {
            ew.like("wzqz", wzqx.getWzqz(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(wzqx.getCzrzw())) {
            ew.like("czrzw", wzqx.getCzrzw(), SqlLike.DEFAULT);
        }

        return jsonPage(wzQxService.selectPage(page, ew));
    }

    /**
     * 功能:提供所有物资权限信息,供下拉
     */
    @ResponseBody
    @PostMapping("/wzqx/getall")
    public String getall() {
        return ListToGson(wzQxService.selectList(new EntityWrapper<WzQx>().eq("flag", "E").orderBy("wzqxname")));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/wzqx/getallbyid")
    public String getallbyid(WzQx wzqx) {
        return GetGsonString(wzQxService.selectById(wzqx.getId()));
    }

    /**
     * 功能：新增物资权限
     */
    @ResponseBody
    @PostMapping("/wzqx/create")
    public String create(WzQx wzqx) {
        if (checkuser()) {

            int num = wzQxService.selectCount(new EntityWrapper<WzQx>()
                    .eq("wzqz", wzqx.getWzqz())
                    .eq("flag", "E")
            );
            if (num > 0) {
                return "cz";
            } else {
                /*通用字段赋值*/
                User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                String timeString = GetCurrentTime();
                wzqx.setId(getUUID());
                wzqx.setFlag("E");
                wzqx.setCreatorid(loginUser.getId());
                wzqx.setCreator(loginUser.getUsername());
                wzqx.setCreatetime(timeString);
                wzqx.setUpdaterid(loginUser.getId());
                wzqx.setUpdater(loginUser.getUsername());
                wzqx.setUpdatetime(timeString);
                if (StringUtil.isEmpty(wzqx.getCzr())) {
                    wzqx.setSpusers(null);
                    wzqx.setCzrzw("");
                } else {
                    wzqx.setWzqz(wzqx.getWzqz().trim());
                    String spusersIdstemp = wzqx.getCzr().trim();
                    List<User> spuserList = userService.selectList(new EntityWrapper<User>()
                            .in("id", spusersIdstemp)
                    );

                    StringBuilder czrzw = new StringBuilder();
                    for (User user : spuserList) {
                        czrzw.append(user.getDepartment()).append("--").append(user.getRealname()).append(",");
                    }
                    String czrzwzh = czrzw.substring(0, czrzw.length() - 1);
                    wzqx.setCzrzw(czrzwzh);
                    Set<User> set = new HashSet<>(spuserList);
                    wzqx.setSpusers(set);
                    wzqx.setCzr(spusersIdstemp.replace(" ", ""));
                }
                wzQxService.insert(wzqx);
                if (!StringUtil.isEmpty(wzqx.getCzr())) {
                    wzqx.setWzqz(wzqx.getWzqz().trim());
                    String spusersIdstemp = wzqx.getCzr().trim();
                    List<User> spuserList = userService.selectList(new EntityWrapper<User>()
                            .in("id", spusersIdstemp)
                    );
                    for (User user : spuserList) {
                        WzQxUser wzQxUser = new WzQxUser();
                        wzQxUser.setId(getUUID());
                        wzQxUser.setCreatorid(loginUser.getId());
                        wzQxUser.setCreator(loginUser.getUsername());
                        wzQxUser.setCreatetime(timeString);
                        wzQxUser.setUpdaterid(loginUser.getId());
                        wzQxUser.setUpdater(loginUser.getUsername());
                        wzQxUser.setUpdatetime(timeString);
                        wzQxUser.setUserid(user.getId());
                        wzQxUser.setWarehouseid(wzqx.getId());
                        wzQxUserService.insert(wzQxUser);
                    }
                }

                //写入日志表
                Logs logs = new Logs();
                logs.setId(getUUID());
                logs.setFlagid(wzqx.getId());
                logs.setName("com.hchenpan.controller.WzQxController.create");
                logs.setParams("com.hchenpan.pojo.WzQx类");
                logs.setDescription("新增物资权限");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(wzqx));
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
        }

        return ERROR;
    }

    /**
     * 功能：更新物资权限
     */
    @ResponseBody
    @PostMapping("/wzqx/update")
    public String update(WzQx wzqx) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            WzQx d = wzQxService.selectById(wzqx.getId());
            String oldcontent = GetGsonString(d);
            //删除已有的中间表
            wzQxUserService.delete(new EntityWrapper<WzQxUser>().eq("warehouseid", wzqx.getId()));
            // 获取未修改前的仓库编码
            String a = d.getWzqz();
            // 未修改仓库编码的情况
            wzqx.setUpdaterid(loginUser.getId());
            wzqx.setUpdater(loginUser.getUsername());
            wzqx.setUpdatetime(timeString);
            if (!a.equals(wzqx.getWzqz())) {
                int num = wzQxService.selectCount(new EntityWrapper<WzQx>().eq("wzqz", wzqx.getWzqz()).eq("flag", "E"));
                if (num > 0) {
                    return "cz";
                }
            }
            if (StringUtil.isEmpty(wzqx.getCzr())) {
                d.setSpusers(null);
                d.setCzrzw("");
            } else {
                d.setWzqz(wzqx.getWzqz().trim());
                String spusersIdstemp = wzqx.getCzr().trim();
                List<User> spuserList = userService.selectList(new EntityWrapper<User>().in("id", spusersIdstemp));
                StringBuilder czrzw = new StringBuilder();
                for (User user : spuserList) {
                    czrzw.append(user.getDepartment()).append("--").append(user.getRealname()).append(",");
                }
                String czrzwzh = czrzw.substring(0, czrzw.length() - 1);
                d.setCzrzw(czrzwzh);
                Set<User> set = new HashSet<User>(spuserList);
                d.setSpusers(set);
                d.setCzr(spusersIdstemp.replace(" ", ""));
            }
            wzQxService.updateById(wzqx);
            if (!StringUtil.isEmpty(wzqx.getCzr())) {
                String spusersIdstemp = wzqx.getCzr().trim();
                List<User> spuserList = userService.selectList(new EntityWrapper<User>().in("id", spusersIdstemp));
                for (User user : spuserList) {
                    WzQxUser wzQxUser = new WzQxUser();
                    wzQxUser.setId(getUUID());
                    wzQxUser.setCreatorid(loginUser.getId());
                    wzQxUser.setCreator(loginUser.getUsername());
                    wzQxUser.setCreatetime(timeString);
                    wzQxUser.setUpdaterid(loginUser.getId());
                    wzQxUser.setUpdater(loginUser.getUsername());
                    wzQxUser.setUpdatetime(timeString);
                    wzQxUser.setUserid(user.getId());
                    wzQxUser.setWarehouseid(wzqx.getId());
                    wzQxUserService.insert(wzQxUser);
                }
            }

            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(wzqx.getId());
            logs.setName("com.hchenpan.controller.WzQxController.update");
            logs.setParams("com.hchenpan.pojo.WzQx类");
            logs.setDescription("修改物资权限");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(wzqx));
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
     * 功能:删除物资权限
     */
    @ResponseBody
    @GetMapping("/wzqx/delete")
    public String delete(WzQx wzqx) {

        if (checkuser()) {
            WzQx old = wzQxService.selectById(wzqx.getId());
            String oldcontent = GetGsonString(old);
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            wzqx.setFlag("D");
            wzqx.setUpdaterid(loginUser.getId());
            wzqx.setUpdater(loginUser.getUsername());
            wzqx.setUpdatetime(timeString);
            //删除已有的中间表
            wzQxUserService.delete(new EntityWrapper<WzQxUser>().eq("warehouseid", wzqx.getId()));
            wzQxService.deleteById(wzqx.getId());
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(wzqx.getId());
            logs.setName("com.hchenpan.controller.WzQxController.delete");
            logs.setParams("com.hchenpan.pojo.WzQx类");
            logs.setDescription("修改物资权限");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(wzqx));
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