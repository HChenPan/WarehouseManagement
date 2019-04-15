package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.*;
import com.hchenpan.service.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.WhtwarehousinglistController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class WhtwarehousinglistController extends BaseController {
    private final WhtwarehousinglistService whtwarehousinglistService;
    private final DictionaryschildService dictionaryschildService;
    private final SparepartCodeService sparepartCodeService;
    private final WhtwarehousingService whtwarehousingService;
    private final LogsService logsService;
    private final UserService userService;

    @Autowired
    public WhtwarehousinglistController(WhtwarehousinglistService whtwarehousinglistService, DictionaryschildService dictionaryschildService, SparepartCodeService sparepartCodeService, WhtwarehousingService whtwarehousingService, LogsService logsService, UserService userService) {
        this.whtwarehousinglistService = whtwarehousinglistService;
        this.dictionaryschildService = dictionaryschildService;
        this.sparepartCodeService = sparepartCodeService;
        this.whtwarehousingService = whtwarehousingService;
        this.logsService = logsService;
        this.userService = userService;
    }


    /**
     * 功能:采购入库维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/whtwarehousinglist/search")
    public String search(Whtwarehousinglist whtwarehousinglist) {
        Page<Whtwarehousinglist> page = getPage();
        EntityWrapper<Whtwarehousinglist> ew = new EntityWrapper<>();
        if (StringUtils.isNotEmpty(whtwarehousinglist.getRkcode())) {
            ew.like("rkcode", whtwarehousinglist.getRkcode(), SqlLike.DEFAULT);
        }
        ew.eq("flag", "E");

        return jsonPage(whtwarehousinglistService.selectPage(page, ew));
    }

    /**
     * 功能:提供所有采购入库信息,供下拉
     */
    @ResponseBody
    @PostMapping("/whtwarehousinglist/getallbyidzjx")
    public String getallbyidzjx(Whtwarehousinglist whtwarehousinglist) {
        return GetGsonString(whtwarehousinglistService.selectById(whtwarehousinglist.getId()));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/whtwarehousinglist/getallbyid")
    public String getallbyid(Whtwarehousinglist whtwarehousinglist) {
        return GetGsonString(whtwarehousinglistService.selectById(whtwarehousinglist.getId()));
    }

    /**
     * 功能：新增采购入库
     */
    @ResponseBody
    @PostMapping("/whtwarehousinglist/create")
    public String create() {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            String rkcode = request.getParameter("rkcode").trim();

            String arrayList = request.getParameter("arrayList");
            //将 arrayList 字符串转换成 json 对象
            //将 json 对象转换成 stock 集合

            List<Whtwarehousinglist> list = new ArrayList<>(10);
            String rkstatus = whtwarehousingService.getrkstatus(rkcode);
            if ("已入库".equals(rkstatus)) {
                return "已入库";
            } else {
                int k = 0;
                String flag = "";
                for (int i = 0; i < list.size(); i++) {
                    int wznum = whtwarehousinglistService.getwznum(list.get(i).getWzcode().trim(), "E", rkcode);
                    if (wznum > 0) {
                        k = k + 1;
                        flag += " 物资编码为：" + list.get(i).getWzcode() + ",";

                    }
                }
                if (k != 0) {
                    flag = flag.substring(0, flag.length() - 1);
                    flag += "已存在，如有需要请修改数量";
                    return flag;
                } else {
                    for (Whtwarehousinglist value : list) {
                        value.setRkcode(rkcode);
                        value.setPlanprice("0.00");
                        value.setSjnum("0.00");
                        value.setSjmoney("0.00");
                        value.setFlag("E");
                        List<Dictionaryschild> list2 = dictionaryschildService.getdchildlistbydecode("BZ");
                        value.setZjname(list2.get(0).getName().trim());
                        value.setZjcode(list2.get(0).getCode().trim());
                        value.setId(getUUID());
                        value.setCreatorid(loginUser.getId());
                        value.setCreator(loginUser.getUsername());
                        value.setCreatetime(timeString);
                        value.setUpdaterid(loginUser.getId());
                        value.setUpdater(loginUser.getUsername());
                        value.setUpdatetime(timeString);

                        whtwarehousinglistService.insert(value);
                        //写入日志表
                        Logs logs = new Logs();
                        logs.setFlagid(value.getId());
                        logs.setName("com.hchenpan.controller.WhtwarehousinglistController.create");
                        logs.setParams("com.hchenpan.pojo.Whtwarehousinglist类");
                        logs.setDescription("新增无合同入库物资明细申请");
                        logs.setUpdaterid(loginUser.getId());
                        logs.setIpaddress(getRomoteIP());
                        logs.setOptcontent(GetGsonString(value));
                        logs.setCreatorid(loginUser.getId());
                        logs.setCreator(loginUser.getUsername());
                        logs.setCreatetime(timeString);
                        logs.setRealname(loginUser.getRealname());
                        logs.setUpdater(loginUser.getUsername());
                        logs.setUpdatetime(timeString);
                        logs.setId(getUUID());
                        logsService.insert(logs);
                    }
                    return SUCCESS;
                }


            }
        }
        return ERROR;
    }

    /**
     * 功能：更新采购入库
     */
    @ResponseBody
    @PostMapping("/whtwarehousinglist/update")
    public String update(Whtwarehousinglist whtwarehousinglist) {
        if (checkuser()) {
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            String arrayList = request.getParameter("arrayList");
            //将 arrayList 字符串转换成 json 对象
            //将 json 对象转换成 stock 集合
            List<Whtwarehousinglist> list = new ArrayList<>();
            String rkstatus = whtwarehousingService.getrkstatus(list.get(0).getRkcode().trim());
            if ("已入库".equals(rkstatus)) {
                return "已入库";
            } else {
                for (Whtwarehousinglist value : list) {
                    String a = value.getSjnum().trim();

                    Whtwarehousinglist d = whtwarehousinglistService.getallbyidzjx(value.getId());
                    String md = d.getModelspcification().trim();
                    String oldcontent = GetGsonString(d);
                    String sjnum1 = value.getSjnum().trim();
                    BigDecimal sjnum2 = new BigDecimal(sjnum1);
                    String price = value.getPlanprice().trim();
                    BigDecimal price1 = new BigDecimal(price);
                    BigDecimal total = price1.multiply(sjnum2).setScale(2, BigDecimal.ROUND_HALF_UP);
                    value.setSjmoney(total.toString());
                    value.setUpdatetime(timeString);
                    value.setModelspcification(md);
                    value.setUpdaterid(loginUser.getId());
                    value.setUpdater(loginUser.getUsername());
                    value.setUpdatetime(timeString);
                    whtwarehousinglistService.updateById(value);


                    //写入日志表
                    Logs logs = new Logs();
                    logs.setFlagid(value.getId());
                    logs.setName("com.hchenpan.controller.WhtwarehousinglistController.create");
                    logs.setParams("com.hchenpan.pojo.Whtwarehousinglist类");
                    logs.setDescription("新增无合同入库物资明细申请");
                    logs.setUpdaterid(loginUser.getId());
                    logs.setIpaddress(getRomoteIP());
                    logs.setOptcontent(GetGsonString(value));
                    logs.setCreatorid(loginUser.getId());
                    logs.setOldcontent(oldcontent);
                    logs.setCreator(loginUser.getUsername());
                    logs.setCreatetime(timeString);
                    logs.setRealname(loginUser.getRealname());
                    logs.setUpdater(loginUser.getUsername());
                    logs.setUpdatetime(timeString);
                    logs.setId(getUUID());
                    logsService.insert(logs);
                }
                return SUCCESS;
            }
        }
        return ERROR;
    }

    /**
     * 功能:删除采购入库
     */
    @ResponseBody
    @GetMapping("/whtwarehousinglist/delete")
    public String delete(Whtwarehousinglist whtwarehousinglist) {
        if (checkuser()) {
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            Whtwarehousinglist d = whtwarehousinglistService.selectById(whtwarehousinglist.getId());
            String oldcontent = GetGsonString(d);
            Whtwarehousing w = whtwarehousingService.getbyrkcode(d.getRkcode().trim());
            String rkstatus = w.getRkstatus().trim();
            if ("未入库".equals(rkstatus)) {
                d.setFlag("D");
                d.setUpdaterid(loginUser.getId());
                d.setUpdater(loginUser.getUsername());
                d.setUpdatetime(timeString);

                whtwarehousinglistService.updateById(d);
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(d.getId());
                logs.setName("com.hchenpan.controller.WhtwarehousinglistController.delete");
                logs.setParams("com.hchenpan.pojo.Whtwarehousinglist类");
                logs.setDescription("删除无合同入库申请物资明细大类");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(d));
                logs.setCreatorid(loginUser.getId());
                logs.setOldcontent(oldcontent);
                logs.setCreator(loginUser.getUsername());
                logs.setCreatetime(timeString);
                logs.setRealname(loginUser.getRealname());
                logs.setUpdater(loginUser.getUsername());
                logs.setUpdatetime(timeString);
                logs.setId(getUUID());
                logsService.insert(logs);


                return SUCCESS;

            } else {
                return "已入库";
            }
        }
        return ERROR;
    }

    @ResponseBody
    @GetMapping("/whtwarehousinglist/getallwz")
    public String getallwz(Whtwarehousinglist whtwarehousinglist) {
        List<Dictionaryschild> list = dictionaryschildService.getdchildlistbydecode("BZ");
        String zjcode = list.get(0).getCode();
        String zjname = list.get(0).getName();
        String code = whtwarehousinglist.getWzcode().trim();
        String name = whtwarehousinglist.getWzname().trim();
        List<SparepartCode> sparepartCodes = sparepartCodeService.selectList(new EntityWrapper<SparepartCode>()
                .eq("description", "物资")
                .like("code", code, SqlLike.DEFAULT)
                .like("name", name, SqlLike.DEFAULT).orderBy("code")
        );
        List<Whtwarehousinglist> whtwarehousinglists = new ArrayList<>();
        for (SparepartCode sparepartCode : sparepartCodes) {
            Whtwarehousinglist d = new Whtwarehousinglist();
            d.setWzid(sparepartCode.getId());
            d.setWzcode(sparepartCode.getCode());
            d.setWzname(sparepartCode.getName());
            d.setModelspcification(sparepartCode.getModelspecification());
            d.setUnit(sparepartCode.getUnit());
            d.setZjcode(zjcode);
            d.setZjname(zjname);
            whtwarehousinglists.add(d);
        }
        return GetGsonString(whtwarehousinglists);
    }
}