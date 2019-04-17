package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.Buyer;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.User;
import com.hchenpan.service.BuyerService;
import com.hchenpan.service.LogsService;
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
 * ClassName : com.hchenpan.controller.BuyerController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class BuyerController extends BaseController {
    private final BuyerService buyerService;
    private final LogsService logsService;
    private final UserService userService;

    @Autowired
    public BuyerController(BuyerService buyerService, LogsService logsService, UserService userService) {
        this.buyerService = buyerService;
        this.logsService = logsService;
        this.userService = userService;
    }

    @GetMapping("/buyer")
    public String buyer() {
        return View("/trade/buyer");
    }

    /**
     * 功能:购买商维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/buyer/search")
    public String search(Buyer buyer) {
        Page<Buyer> page = getPage();
        EntityWrapper<Buyer> ew = new EntityWrapper<>();
        ew.eq("flag", "E");
        if (StringUtil.notTrimEmpty(buyer.getBuyercode())) {
            ew.like("buyercode", buyer.getBuyercode(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(buyer.getBuyername())) {
            ew.like("buyname", buyer.getBuyername(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(buyer.getLegalrepresentative())) {
            ew.like("legalrepresentative", buyer.getLegalrepresentative(), SqlLike.DEFAULT);
        }
        return jsonPage(buyerService.selectPage(page, ew));
    }

    /**
     * 功能:提供所有购买商信息,供下拉
     */
    @ResponseBody
    @PostMapping("/buyer/getall")
    public String getall() {
        return ListToGson(buyerService.selectList(new EntityWrapper<Buyer>().eq("flag", "E").orderBy("buyername")));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/buyer/getbyid")
    public String getbyid(Buyer buyer) {
        return GetGsonString(buyerService.selectById(buyer.getId()));
    }

    /**
     * 功能：新增购买商
     */
    @ResponseBody
    @PostMapping("/buyer/create")
    public String create(Buyer buyer) {
        if (checkuser()) {
            //判断编码是否存在
            if (0 < buyerService.selectCount(new EntityWrapper<Buyer>().eq("flag", "E").eq("buyercode", buyer.getBuyercode()))) {
                return "购买商编码已存在";
            }
            //判断名称是否存在
            if (0 < buyerService.selectCount(new EntityWrapper<Buyer>().eq("flag", "E").eq("buyername", buyer.getBuyername()))) {
                return "购买商名称已存在";
            }
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            buyer.setId(getUUID());
            buyer.setFlag("E");
            buyer.setCreatorid(loginUser.getId());
            buyer.setCreator(loginUser.getUsername());
            buyer.setCreatetime(timeString);
            buyer.setUpdaterid(loginUser.getId());
            buyer.setUpdater(loginUser.getUsername());
            buyer.setUpdatetime(timeString);
            buyerService.insert(buyer);
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(buyer.getId());
            logs.setName("com.hchenpan.controller.BuyerController.create");
            logs.setParams("com.hchenpan.pojo.Buyer类");
            logs.setDescription("新增购买商");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(buyer));
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
     * 功能：更新购买商
     */
    @ResponseBody
    @PostMapping("/buyer/update")
    public String update(Buyer buyer) {
        if (checkuser()) {
            Buyer old = buyerService.selectById(buyer.getId());
            String oldcontent = GetGsonString(old);
            //判断编码是否修改
            if (old.getBuyercode().equals(buyer.getBuyercode())) {
                //判断名称是否修改
                if (!old.getBuyername().equals(buyer.getBuyername())) {
                    //判断名称是否重复
                    if (0 < buyerService.selectCount(new EntityWrapper<Buyer>().eq("flag", "E").eq("buyername", buyer.getBuyername()))) {
                        return "购买商名称已存在";
                    }  //编码未变名称不重复 更新
                }  //编码和名称都未修改 更新
            } else {
                //编码修改，判断是否编码重复
                if (0 < buyerService.selectCount(new EntityWrapper<Buyer>().eq("flag", "E").eq("buyercode", buyer.getBuyercode()))) {
                    return "购买商编码已存在";
                } else {
                    //判断名称是否修改
                    if (!old.getBuyername().equals(buyer.getBuyername())) {
                        //判断名称是否重复
                        if (0 < buyerService.selectCount(new EntityWrapper<Buyer>().eq("flag", "E").eq("buyername", buyer.getBuyername()))) {
                            return "购买商名称已存在";
                        }  //编码和名称均修改且不重复 更新
                    }  //编码不重复名称未修改 更新
                }
            }
            //更新
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            buyer.setFlag("E");
            buyer.setUpdaterid(loginUser.getId());
            buyer.setUpdater(loginUser.getUsername());
            buyer.setUpdatetime(timeString);
            buyerService.updateById(buyer);
            //写日志
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(buyer.getId());
            logs.setName("com.hchenpan.controller.BuyerController.update");
            logs.setParams("com.hchenpan.pojo.Buyer类");
            logs.setDescription("修改购买商");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(buyer));
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
     * 功能:删除购买商
     */
    @ResponseBody
    @GetMapping("/buyer/delete")
    public String delete(Buyer buyer) {
        if (checkuser()){
            Buyer old = buyerService.selectById(buyer.getId());
            String oldcontent=GetGsonString(old);
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            buyer.setFlag("D");
            buyer.setUpdaterid(loginUser.getId());
            buyer.setUpdater(loginUser.getUsername());
            buyer.setUpdatetime(timeString);
            buyerService.deleteById(buyer.getId());
            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(buyer.getId());
            logs.setName("com.hchenpan.controller.BuyerController.delete");
            logs.setParams("com.hchenpan.pojo.Buyer类");
            logs.setDescription("修改购买商");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(buyer));
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