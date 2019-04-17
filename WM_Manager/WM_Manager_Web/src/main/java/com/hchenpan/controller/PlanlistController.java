package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.*;
import com.hchenpan.service.*;
import com.hchenpan.util.StringUtil;
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
 * ClassName : com.hchenpan.controller.PlanlistController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class PlanlistController extends BaseController {
    private final PlanlistService planlistService;
    private final PlanService planService;
    private final LogsService logsService;
    private final UserService userService;
    private final DictionaryschildService dictionaryschildService;

    @Autowired
    public PlanlistController(PlanlistService planlistService, PlanService planService, LogsService logsService, UserService userService, DictionaryschildService dictionaryschildService) {
        this.planlistService = planlistService;
        this.planService = planService;
        this.logsService = logsService;
        this.userService = userService;
        this.dictionaryschildService = dictionaryschildService;
    }


    /**
     * 功能:需求明细维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/planlist/search")
    public String search(Planlist planlist) {
        Page<Planlist> page = getPage();
        EntityWrapper<Planlist> ew = new EntityWrapper<>();
        ew.eq("flag", "E");
        if (StringUtil.notTrimEmpty(planlist.getPlancode())) {
            ew.like("plancodeid", planlist.getPlancode(), SqlLike.DEFAULT);
        }
        return jsonPage(planlistService.selectPage(page, ew));
    }


    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @PostMapping("/planlist/getbyid")
    public String getallbyid(Planlist planlist) {
        return GetGsonString(planlistService.selectById(planlist.getId()));
    }

    /**
     * 功能：新增需求明细
     */
    @ResponseBody
    @PostMapping("/planlist/create")
    public String create() {
        if (checkuser()) {
            String arrayList = request.getParameter("arrayList");
            String plancode = request.getParameter("plancode");
            String plancodeid = request.getParameter("plancodeid");
            String plantype = request.getParameter("plantype");
            String planname = request.getParameter("planname");
            //替换字符串中的'/'
            String str = arrayList.replaceAll("\\\\", "\"");
            //转为json对象
            JSONArray json = JSONArray.fromObject(str);
            List<Planlist> list = (List<Planlist>) JSONArray.toCollection(json, Planlist.class);


            String spcode = planService.selectOne(new EntityWrapper<Plan>().eq("id", plancodeid)).getSpcode();
            if (!"00".equals(spcode)) {
                return "审批中";
            } else {
                StringBuilder flag = new StringBuilder();
                int k = 0;
                for (Planlist planlist : list) {
                    int wznum = planlistService.selectCount(new EntityWrapper<Planlist>()
                            .eq("flag", "E")
                            .eq("wzcode", planlist.getWzcode())
                            .eq("plancodeid", plancodeid)
                    );
                    if (wznum > 0) {
                        k = k + 1;
                        flag.append("物资编码为：").append(planlist.getWzcode()).append(",");
                    }
                }
                if (k != 0) {
                    return flag.toString().substring(flag.length() - 1) + "已存在,如有需要请修改数量";
                } else {
                    /*通用字段赋值*/
                    User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                    String timeString = GetCurrentTime();
                    for (Planlist planlist : list) {
                        planlist.setId(getUUID());
                        planlist.setFlag("E");
                        planlist.setCreatorid(loginUser.getId());
                        planlist.setCreator(loginUser.getUsername());
                        planlist.setCreatetime(timeString);
                        planlist.setUpdaterid(loginUser.getId());
                        planlist.setUpdater(loginUser.getUsername());
                        planlist.setUpdatetime(timeString);

                        planlist.setPlancode(plancode);
                        planlist.setPlancodeid(plancodeid);
                        planlist.setPlanname(planname);
                        planlist.setPlantype(plantype);

                        planlistService.insert(planlist);

                        //写入日志表
                        Logs logs = new Logs();
                        logs.setId(getUUID());
                        logs.setFlagid(planlist.getId());
                        logs.setName("com.hchenpan.controller.PlanlistController.create");
                        logs.setParams("com.hchenpan.pojo.Planlist");
                        logs.setDescription("新增需求申请物资明细");
                        logs.setUpdaterid(loginUser.getId());
                        logs.setIpaddress(getRomoteIP());
                        logs.setOptcontent(GetGsonString(planlist));
                        /* 修改，需要保存修改前后的数据 */
                        logs.setOldcontent(null);
                        logs.setCreatorid(loginUser.getId());
                        logs.setCreator(loginUser.getUsername());
                        logs.setCreatetime(timeString);
                        logs.setUpdater(loginUser.getUsername());
                        logs.setRealname(loginUser.getRealname());
                        logs.setUpdatetime(timeString);
                        logsService.insert(logs);
                    }
                    return SUCCESS;
                }
            }

        }


        return ERROR;
    }

    /**
     * 功能：更新需求明细
     */
    @ResponseBody
    @PostMapping("/planlist/update")
    public String update() {
        if (checkuser()) {
            String arrayList = request.getParameter("arrayList");
            //替换字符串中的'/'
            String str = arrayList.replaceAll("\\\\", "\"");
            //转为json对象
            JSONArray json = JSONArray.fromObject(str);
            List<Planlist> list = (List<Planlist>) JSONArray.toCollection(json, Planlist.class);

            String spcode = planService.selectOne(new EntityWrapper<Plan>().eq("id", list.get(0).getPlancodeid())).getSpcode();
            if (!"00".equals(spcode)) {
                return "审批中";
            } else {
                /*通用字段赋值*/
                User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                String timeString = GetCurrentTime();

                for (Planlist planlist : list) {
                    Planlist d = planlistService.selectById(planlist.getId());
                    String oldcontent = GetGsonString(d);
                    String oldnum1 = d.getPlannum().trim();
                    String oldprice1 = d.getPrice().trim();
                    String oldxqplanmoney = planService.selectById(d.getPlancodeid()).getPlanmoney();

                    BigDecimal oldnum2 = new BigDecimal(oldnum1);
                    BigDecimal oldprice2 = new BigDecimal(oldprice1);
                    BigDecimal oldtotal1 = oldprice2.multiply(oldnum2).setScale(2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal oldxqplanmoney1 = new BigDecimal(oldxqplanmoney);
                    BigDecimal oldxqplanmoney2 = oldxqplanmoney1.subtract(oldtotal1).setScale(2, BigDecimal.ROUND_HALF_UP);


                    String newnum1 = planlist.getPlannum().trim();
                    String newprice1 = planlist.getPrice().trim();
                    BigDecimal newnum2 = new BigDecimal(newnum1);
                    BigDecimal newprice2 = new BigDecimal(newprice1);
                    BigDecimal newtotal = newprice2.multiply(newnum2).setScale(2, BigDecimal.ROUND_HALF_UP);

                    planlist.setPlanmoney(newtotal.toString());
                    planlist.setSpmoney(newtotal.toString());

                    planlist.setFlag("E");
                    BigDecimal b2 = oldxqplanmoney2.add(newtotal);
                    Plan p = planService.selectById(d.getPlancodeid());
                    p.setPlanmoney(b2.toString());
                    p.setPlanspmoney(b2.toString());
                    planService.updateById(p);
                    planlist.setUpdaterid(loginUser.getId());
                    planlist.setUpdater(loginUser.getUsername());
                    planlist.setUpdatetime(timeString);
                    planlistService.updateById(planlist);
                    //写入日志表
                    Logs logs = new Logs();
                    logs.setId(getUUID());
                    logs.setFlagid(planlist.getId());
                    logs.setName("com.hchenpan.controller.PlanlistController.update");
                    logs.setParams("com.hchenpan.pojo.Planlist");
                    logs.setDescription("修改需求物资明细大类");
                    logs.setUpdaterid(loginUser.getId());
                    logs.setIpaddress(getRomoteIP());
                    logs.setOptcontent(GetGsonString(planlist));
                    /* 修改，需要保存修改前后的数据 */
                    logs.setOldcontent(oldcontent);
                    logs.setCreatorid(loginUser.getId());
                    logs.setCreator(loginUser.getUsername());
                    logs.setCreatetime(timeString);
                    logs.setUpdater(loginUser.getUsername());
                    logs.setRealname(loginUser.getRealname());
                    logs.setUpdatetime(timeString);
                    logsService.insert(logs);
                }
                return SUCCESS;
            }
        }
        return ERROR;
    }

    /**
     * 功能:删除需求明细
     */
    @ResponseBody
    @GetMapping("/planlist/delete")
    public String delete(Planlist planlist) {
        if (checkuser()) {
            Planlist d = planlistService.selectById(planlist.getId());
            String oldcontent = GetGsonString(d);
            String spcode = planService.selectOne(new EntityWrapper<Plan>().eq("id", d.getPlancodeid())).getSpcode();
            if (!"00".equals(spcode)) {
                return "审批中";
            } else {
                /*通用字段赋值*/
                User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                String timeString = GetCurrentTime();
                d.setFlag("D");
                String a = d.getPlanmoney().trim();
                Plan p = planService.selectById(d.getPlancodeid());
                String xqplanmoney = p.getPlanmoney();
                BigDecimal a1 = new BigDecimal(a);
                BigDecimal b1 = new BigDecimal(xqplanmoney);
                BigDecimal b2 = b1.subtract(a1).setScale(2, BigDecimal.ROUND_HALF_UP);
                p.setPlanmoney(b2.toString());
                p.setPlanspmoney(b2.toString());
                planService.updateById(p);

                planlist.setUpdaterid(loginUser.getId());
                planlist.setUpdater(loginUser.getUsername());
                planlist.setUpdatetime(timeString);
                planlistService.deleteById(planlist.getId());
                //写入日志表
                Logs logs = new Logs();
                logs.setId(getUUID());
                logs.setFlagid(planlist.getId());
                logs.setName("com.hchenpan.controller.PlanlistController.delete");
                logs.setParams("com.hchenpan.pojo.Planlist");
                logs.setDescription("删除需求申请大类");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(planlist));
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
        }
        return ERROR;
    }

    /**
     * 功能:删除需求明细
     */
    @ResponseBody
    @GetMapping("/planlist/updateforsp")
    public String updateforsp(Planlist planlist) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            Planlist d = planlistService.selectById(planlist.getId());
            String oldcontent = GetGsonString(d);
            String oldprice1 = d.getSpprice().trim();
            String oldnum1 = d.getSpnum();
            BigDecimal oldnum2 = new BigDecimal(oldnum1);
            Plan p = planService.selectById(d.getPlancodeid());
            String oldxqplanmoney = p.getPlanmoney();

            BigDecimal oldprice2 = new BigDecimal(oldprice1);
            BigDecimal oldtotal1 = oldprice2.multiply(oldnum2);

            BigDecimal oldxqplanmoney1 = new BigDecimal(oldxqplanmoney);
            BigDecimal oldxqplanmoney2 = oldxqplanmoney1.subtract(oldtotal1);
            d.setSpnum(planlist.getSpnum().trim());
            d.setSynum(planlist.getSpnum().trim());
            d.setSpprice(planlist.getSpprice().trim());
            String newnum1 = d.getSpnum().trim();
            String newprice1 = d.getSpprice().trim();
            String num1 = planlist.getSpprice().trim();
            BigDecimal num2 = new BigDecimal(num1);
            BigDecimal newnum2 = new BigDecimal(newnum1);
            BigDecimal newprice2 = new BigDecimal(newprice1);
            BigDecimal newtotal = newprice2.multiply(newnum2);
            d.setSpmoney(newtotal.toString());

            BigDecimal b2 = oldxqplanmoney2.add(newtotal);

            p.setPlanspmoney(b2.toString());
            planService.updateById(p);
            planlist.setUpdater(loginUser.getUsername());
            planlist.setUpdatetime(timeString);
            planlistService.updateById(d);

            //写入日志表
            Logs logs = new Logs();
            logs.setId(getUUID());
            logs.setFlagid(planlist.getId());
            logs.setName("com.hchenpan.controller.PlanlistController.updateforsp");
            logs.setParams("com.hchenpan.pojo.Planlist");
            logs.setDescription("需求审批修改单价金额");
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
            logsService.insert(logs);

            return SUCCESS;
        }
        return ERROR;
    }

    /**
     * 功能:获取所有物资明细
     */
    @ResponseBody
    @GetMapping("/planlist/getallwzmx")
    public String getallwzmx(Planlist planlist) {
        return ListToGson(planlistService.selectWZList());
    }

    /**
     * 功能:获取所有物资
     */
    @ResponseBody
    @PostMapping("/planlist/getallwz")
    public String getallwz(Planlist planlist) {
        List<Dictionaryschild> list = dictionaryschildService.getdchildlistbydecode("BZ");
        String zjcode = list.get(0).getCode();
        String zjname = list.get(0).getName();
        String code = planlist.getWzcode().trim();
        String name = planlist.getWzname().trim();

        return GetGsonString(planlistService.selectWZ(zjcode, zjname, code, name));
    }
}