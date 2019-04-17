package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.*;
import com.hchenpan.service.*;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.WarehousinglistController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class WarehousinglistController extends BaseController {
    private final WarehousinglistService warehousinglistService;
    private final DictionaryschildService dictionaryschildService;
    private final WarehousingService warehousingService;
    private final LogsService logsService;
    private final UserService userService;

    @Autowired
    public WarehousinglistController(WarehousinglistService warehousinglistService, DictionaryschildService dictionaryschildService, WarehousingService warehousingService, LogsService logsService, UserService userService) {
        this.warehousinglistService = warehousinglistService;
        this.dictionaryschildService = dictionaryschildService;
        this.warehousingService = warehousingService;
        this.logsService = logsService;
        this.userService = userService;
    }


    /**
     * 功能:采购入库明细维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/warehousinglist/search")
    public String search(Warehousinglist warehousinglist) {
        Page<Warehousinglist> page = getPage();
        EntityWrapper<Warehousinglist> ew = new EntityWrapper<>();
        if (StringUtils.isNotEmpty(warehousinglist.getRkcode())) {
            ew.eq("rkcode", warehousinglist.getRkcode());
        }
        return jsonPage(warehousinglistService.selectPage(page, ew));
    }

    /**
     * 功能:提供所有采购入库明细信息,供下拉
     */
    @ResponseBody
    @PostMapping("/warehousinglist/getallbyidzjx")
    public String getallbyidzjx(Warehousinglist warehousinglist) {
        return GetGsonString(warehousinglistService.selectById(warehousinglist.getId()));
    }

    @ResponseBody
    @PostMapping("/warehousinglist/searchformx")
    public String searchformx(Warehousinglist warehousinglist) {
        Page<Warehousinglist> page = getPage();
        EntityWrapper<Warehousinglist> ew = new EntityWrapper<>();
        String[] aa = warehousinglist.getContractbasicid().split(",");
        String contractbasicids = "";
        for (int i = 0; i < aa.length; i++) {
            contractbasicids = contractbasicids + "'" + aa[i] + "'" + ",";
        }
        contractbasicids = contractbasicids.substring(0, contractbasicids.length() - 1);
        ew.eq("flag", "E");
        ew.in("contractbasicid", contractbasicids);
        ew.gt("syrksum", "0.00");
        return jsonPage(warehousinglistService.selectPage(page, ew));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/warehousinglist/getallbyid")
    public String getallbyid(Warehousinglist warehousinglist) {
        return GetGsonString(warehousinglistService.selectById(warehousinglist.getId()));
    }

    /**
     * 功能：新增采购入库明细
     */
    @ResponseBody
    @PostMapping("/warehousinglist/create")
    public String create(Warehousinglist warehousinglist) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            String rkcode = request.getParameter("rkcode").trim();
            //String summoneystring=warehousingservice.getsummoney(warehousinglist.getRkcode().trim());

            String arrayList = request.getParameter("arrayList");
            //替换字符串中的'/'
            String str = arrayList.replaceAll("\\\\", "\"");
            //转为json对象
            JSONArray json = JSONArray.fromObject(str);
            List<Warehousinglist> list = (List<Warehousinglist>) JSONArray.toCollection(json, Warehousinglist.class);


            String rkstatus = warehousingService.getrkstatus(rkcode);
            if ("已入库".equals(rkstatus)) {
                return "已入库";
            } else {
                int k = 0;
                String flag = "";
                for (int i = 0; i < list.size(); i++) {

                    int wznum = warehousinglistService.getwznum(list.get(i).getWzcode().trim(), list.get(i).getContractbasicid().trim(), list.get(i).getBuycode().trim(), list.get(i).getPlancode().trim(), "E", rkcode);
                    if (wznum > 0) {
                        k = k + 1;
                        flag += "合同号为：" + list.get(i).getContractbasicid() + " 采购计划号为：" + list.get(i).getBuycode() + " 需求计划号为：" + list.get(i).getPlancode() + " 物资编码为：" + list.get(i).getWzcode() + ",";

                    }
                }
                if (k == 0) {
                    for (int i = 0; i < list.size(); i++) {
                        String price = list.get(i).getPlanprice().trim();
                        BigDecimal price1 = new BigDecimal(price);
                        String sjnum = list.get(i).getSjnum().trim();
                        BigDecimal sjnum1 = new BigDecimal(sjnum);
                        BigDecimal total = price1.multiply(sjnum1).setScale(2, BigDecimal.ROUND_HALF_UP);
                        list.get(i).setRkcode(rkcode);
                        list.get(i).setUpdatetime(timeString);
                        list.get(i).setSjmoney(total.toString());
                        list.get(i).setFlag("E");
                        list.get(i).setSycknum(list.get(i).getSjnum().trim());
                        List<Dictionaryschild> list1 = dictionaryschildService.getdchildlistbydecode("BZ");
                        list.get(i).setZjname(list1.get(0).getName().trim());
                        list.get(i).setZjcode(list1.get(0).getCode().trim());
                        list.get(i).setId(getUUID());
                        list.get(i).setCreatorid(loginUser.getId());
                        list.get(i).setCreator(loginUser.getUsername());
                        list.get(i).setCreatetime(timeString);
                        list.get(i).setUpdaterid(loginUser.getId());
                        list.get(i).setUpdater(loginUser.getUsername());
                        list.get(i).setUpdatetime(timeString);
                        list.get(i).setFlag("E");

                        warehousinglistService.insert(list.get(i));
                        //写入日志表
                        Logs logs = new Logs();
                        logs.setFlagid(list.get(i).getId());
                        logs.setName("com.hchenpan.controller.WarehousinglistController.create");
                        logs.setParams("com.hchenpan.pojo.Warehousinglist类");
                        logs.setDescription("新增采购入库物资明细申请");
                        logs.setUpdaterid(loginUser.getId());
                        logs.setIpaddress(getRomoteIP());
                        logs.setOptcontent(GetGsonString(list.get(i)));
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
                } else if (k != 0) {
                    flag = flag.substring(0, flag.length() - 1);
                    flag += "已存在，如有需要请修改数量";
                    return flag;
                }

            }
        }
        return ERROR;
    }

    /**
     * 功能：更新采购入库明细
     */
    @ResponseBody
    @PostMapping("/warehousinglist/update")
    public String update(Warehousinglist warehousinglist) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            String arrayList = request.getParameter("arrayList");
            //替换字符串中的'/'
            String str = arrayList.replaceAll("\\\\", "\"");
            //转为json对象
            JSONArray json = JSONArray.fromObject(str);
            List<Warehousinglist> list = (List<Warehousinglist>) JSONArray.toCollection(json, Warehousinglist.class);

            String rkstatus = warehousingService.getrkstatus(list.get(0).getRkcode().trim());
            if (rkstatus.equals("已入库")) {
                return "已入库";
            } else {
                int k = 0;
                String flag = "";
                for (int i = 0; i < list.size(); i++) {

                    String a = list.get(i).getSjnum().trim();
                    BigDecimal a1 = new BigDecimal(a);
                    String b = list.get(i).getPlanbum().trim();
                    BigDecimal b1 = new BigDecimal(b);
                    BigDecimal c = new BigDecimal("5");
                    BigDecimal b2 = b1.multiply(c).setScale(2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal d1 = new BigDecimal("100");
                    BigDecimal b3 = b2.divide(d1, 2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal b4 = b1.add(b3).setScale(2, BigDecimal.ROUND_HALF_UP);
                    int result = a1.compareTo(b4);
                    if (result > 0) {
                        k = k + 1;
                        flag += "合同号为：" + list.get(i).getContractbasicid() + " 采购计划号为：" + list.get(i).getBuycode() + " 需求计划号为：" + list.get(i).getPlancode() + " 物资编码为：" + list.get(i).getWzcode() + ",";

                    }
                }
                if (k == 0) {
                    for (int i = 0; i < list.size(); i++) {
                        Warehousinglist d = warehousinglistService.selectById(list.get(i).getId());
                        String md = d.getModelspcification().trim();
                        String oldcontent = GetGsonString(d);
                        String sjnum1 = list.get(i).getSjnum().trim();
                        BigDecimal sjnum2 = new BigDecimal(sjnum1);
                        String price = list.get(i).getPlanprice().trim();
                        BigDecimal price1 = new BigDecimal(price);
                        BigDecimal total = price1.multiply(sjnum2).setScale(2, BigDecimal.ROUND_HALF_UP);
                        list.get(i).setSjmoney(total.toString());
                        list.get(i).setSycknum(list.get(i).getSjnum().trim());
                        list.get(i).setUpdatetime(timeString);
                        list.get(i).setModelspcification(md);
                        warehousinglistService.updateById(list.get(i));
                        //写入日志表
                        Logs logs = new Logs();
                        logs.setFlagid(list.get(i).getId());
                        logs.setName("com.hchenpan.controller.WarehousinglistController.update");
                        logs.setParams("com.hchenpan.pojo.Warehousinglist类");
                        logs.setDescription("修改采购入库申请物资明细大类");
                        logs.setUpdaterid(loginUser.getId());
                        logs.setIpaddress(getRomoteIP());
                        logs.setOptcontent(GetGsonString(list.get(i)));
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

                    }
                    return SUCCESS;
                } else if (k != 0) {
                    flag = flag.substring(0, flag.length() - 1);
                    flag += "实际收货量大于规定收货量";
                    return flag;
                }
            }
        }
        return ERROR;
    }

    /**
     * 功能:删除采购入库明细
     */
    @ResponseBody
    @GetMapping("/warehousinglist/delete")
    public String delete(Warehousinglist warehousinglist) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            Warehousinglist d = warehousinglistService.selectById(warehousinglist.getId());
            String oldcontent = GetGsonString(d);
            Warehousing w = warehousingService.getbyrkcode(d.getRkcode().trim());
            String rkstatus = w.getRkstatus().trim();
            if ("未入库".equals(rkstatus)) {
                d.setFlag("D");
                d.setUpdaterid(loginUser.getId());
                d.setUpdater(loginUser.getUsername());
                d.setUpdatetime(timeString);
                warehousinglistService.updateById(d);
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(d.getId());
                logs.setName("com.hchenpan.controller.WarehousinglistController.update");
                logs.setParams("com.hchenpan.pojo.Warehousinglist类");
                logs.setDescription("删除采购入库申请物资明细大类");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(d));
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

            } else {
                return "已入库";
            }
        }
        return ERROR;
    }

    /**
     * 功能:根据需求新增物资
     *
     * @author zws
     */
    @ResponseBody
    @PostMapping("/warehousinglist/getlistgoods")
    public String getlistgoods(Warehousinglist warehousinglist) {
        return ListToGson(warehousinglistService.selectWZList(warehousinglist));
    }
}