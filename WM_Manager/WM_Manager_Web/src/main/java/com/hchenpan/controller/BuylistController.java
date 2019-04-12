package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.*;
import com.hchenpan.service.*;
import com.hchenpan.util.StringUtil;
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
 * ClassName : com.hchenpan.controller.BuylistController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class BuylistController extends BaseController {
    private final BuylistService buylistService;
    private final BuyService buyService;
    private final WzQxUserService wzQxUserService;
    private final WzQxService wzQxService;
    private final DictionaryschildService dictionaryschildService;
    private final PlanlistService planlistService;
    private final LogsService logsService;

    @Autowired
    public BuylistController(BuylistService buylistService, BuyService buyService, WzQxUserService wzQxUserService, WzQxService wzQxService, DictionaryschildService dictionaryschildService, PlanlistService planlistService, LogsService logsService, UserService userService) {
        this.buylistService = buylistService;
        this.buyService = buyService;
        this.wzQxUserService = wzQxUserService;
        this.wzQxService = wzQxService;
        this.dictionaryschildService = dictionaryschildService;
        this.planlistService = planlistService;
        this.logsService = logsService;
    }


    /**
     * 功能:采购计划明细维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/buylist/search")
    public String search(Buylist buylist) {
        Page<Buylist> page = getPage();
        EntityWrapper<Buylist> ew = new EntityWrapper<>();
        ew.eq("flag", "E");
        if (StringUtil.notTrimEmpty(buylist.getBuycode())) {
            ew.like("buycode", buylist.getBuycode(), SqlLike.DEFAULT);
        }
        return jsonPage(buylistService.selectPage(page, ew));
    }

    /**
     * 功能:提供所有采购计划明细信息,供下拉
     */
    @ResponseBody
    @PostMapping("/buylist/getallbyid")
    public String getallbyid(Buylist buylist) {
        return GetGsonString(buylistService.selectById(buylist.getId()));
    }

    /**
     * 功能:根据计划号获取物资信息列表
     */
    @ResponseBody
    @PostMapping("/buylist/getwzforbuycode")
    public String getwzforbuycode(Buylist buylist) {
        return GetGsonString(buylistService.selectList(new EntityWrapper<Buylist>().eq("flag", "E").eq("buycode", buylist.getBuycode()).orderBy("updatetime")));
    }

    /**
     *
     */
    @ResponseBody
    @PostMapping("/buylist/searchformx")
    public String searchformx(Buylist buylist) {
        // TODO 待完成 多表查询
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        List<WzQxUser> userid = wzQxUserService.selectList(new EntityWrapper<WzQxUser>().eq("userid", loginUser.getId()));
        //构造 wzqx 的 id 集合
        StringBuilder warehouseids = new StringBuilder();
        for (WzQxUser wzQxUser : userid) {
            warehouseids.append(wzQxUser.getWarehouseid()).append(",");
        }
        // 物资编码前缀集合
        List<WzQx> wzqz = wzQxService.selectList(new EntityWrapper<WzQx>().in("id", warehouseids));
        StringBuilder sql = new StringBuilder();
        for (WzQx wzQx : wzqz) {
            sql.append(" wzcode like '").append(wzQx.getWzqz()).append("%' or ");
        }
        if (wzqz.size() > 0) {
            sql.append(" wzcode like '").append(wzqz.get(wzqz.size() - 1).getWzqz()).append("%' ");
        }
        System.out.println(sql);
        EntityWrapper<Buylist> ew = new EntityWrapper<>();
        ew.in("plancode", buylist.getPlancode());
        ew.where("and (" + sql.toString() + " ) and Convert(decimal(18,2),synum)>0.00 and flag={0}", "E");
        Page<Buylist> page = getPage();
        return jsonPage(buylistService.selectPage(page, ew));

    }

    /**
     * 功能:根据计划号获取物资（批量）
     */
    @ResponseBody
    @PostMapping("/buylist/searchforbuycode")
    public String searchforbuycode(Buylist buylist) {
        Page<Buylist> page = getPage();
        EntityWrapper<Buylist> ew = new EntityWrapper<>();
        ew.in("buycode", buylist.getBuycode());
        ew.ge("cast(synum as decimal(38,2))", 0);
        ew.eq("flag", "E");
        return jsonPage(buylistService.selectPage(page, ew));
    }


    /**
     * 功能：新增采购计划明细
     */
    @ResponseBody
    @PostMapping("/buylist/create")
    public String create() {
        if (checkuser()) {
            String buycode = request.getParameter("buycode").trim();
            String buytype = request.getParameter("buytype").trim();
            String buyname = request.getParameter("buyname").trim();

            String arrayList = request.getParameter("arrayList");
            //将 arrayList 字符串转换成 json 对象
            //将 json 对象转换成 stock 集合
//            JSONArray json = JSONArray.fromObject(arrayList);
//            List<Buylist> list= (List<Buylist>)JSONArray.toCollection(json, Buylist.class);
            List<Buylist> list = new ArrayList<>();

            String spcode = buyService.selectOne(new EntityWrapper<Buy>().eq("buycode", buycode)).getSpcode();
            if (!"00".equals(spcode)) {
                return "审批中";
            } else {
                int k = 0;
                StringBuilder flag = null;
                for (Buylist buylist : list) {
                    int wznum = buylistService.selectCount(new EntityWrapper<Buylist>()
                            .eq("flag", "E")
                            .eq("wzcode", buylist.getWzcode())
                            .eq("plancode", buylist.getPlancode())
                            .eq("buycode", buycode)
                    );
                    if (wznum > 0) {
                        k++;
                        flag.append("需求计划号为：").append(buylist.getPlancode()).append(" 物资编码为：").append(buylist.getWzcode()).append(",");
                    }
                }
                if (k != 0) {
                    return flag.toString().substring(flag.length() - 1) + "已存在,如有需要请修改数量";
                } else {
                    /*通用字段赋值*/
                    User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                    String timeString = GetCurrentTime();
                    for (Buylist buylist : list) {

                        buylist.setId(getUUID());
                        buylist.setFlag("E");
                        buylist.setCreatorid(loginUser.getId());
                        buylist.setCreator(loginUser.getUsername());
                        buylist.setCreatetime(timeString);
                        buylist.setUpdaterid(loginUser.getId());
                        buylist.setUpdater(loginUser.getUsername());
                        buylist.setUpdatetime(timeString);

                        buylist.setBuycode(buycode);
                        buylist.setBuytype(buytype);
                        buylist.setBuyname(buyname);

                        //获取计划的剩余数量
                        String synum = planlistService.selectOne(new EntityWrapper<Planlist>()
                                .eq("plancode", buylist.getPlancode())
                                .eq("wzcode", buylist.getWzcode())
                                .eq("flag", "E")
                        ).getSynum();

                        BigDecimal synum1 = new BigDecimal(synum.trim());

                        String price1 = buylist.getBuyprice().trim();
                        String buynum1 = buylist.getBuynum().trim();

                        String xqmoney = buyService.selectOne(new EntityWrapper<Buy>().eq("buycode", buylist.getBuycode())).getBuysummoney();
                        buylist.setBuycodeid(buyService.selectOne(new EntityWrapper<Buy>().eq("buycode", buylist.getBuycode())).getId());

                        BigDecimal price2 = new BigDecimal(price1);
                        BigDecimal buynum2 = new BigDecimal(buynum1);
                        BigDecimal synumnew = synum1.subtract(buynum2);

                        Planlist pl = planlistService.selectOne(new EntityWrapper<Planlist>().eq("plancode", buylist.getPlancode()).eq("wzcode", buylist.getWzcode()).eq("flag", "E"));

                        pl.setSynum(synumnew.toString());

                        planlistService.updateById(pl);

                        BigDecimal total1 = price2.multiply(buynum2).setScale(2, BigDecimal.ROUND_HALF_UP);
                        buylist.setBuymoney(total1.toString());
                        BigDecimal xqmoney1 = new BigDecimal(xqmoney);
                        BigDecimal totalmoney = xqmoney1.add(total1).setScale(2, BigDecimal.ROUND_HALF_UP);

                        Buy p = buyService.selectOne(new EntityWrapper<Buy>().eq("buycode", buylist.getBuycode()).eq("flag", "E"));
                        p.setBuysummoney(totalmoney.toString());
                        buyService.updateById(p);

                        String a = buylist.getBuynum().trim();
                        buylist.setSynum(a);

                        List<Dictionaryschild> list1 = dictionaryschildService.getdchildlistbydecode("BZ");
                        buylist.setZjname(list1.get(0).getName().trim());
                        buylist.setZjcode(list1.get(0).getCode().trim());

                        buylistService.insert(buylist);

                        //写入日志表
                        Logs logs = new Logs();
                        logs.setId(getUUID());
                        logs.setFlagid(buylist.getId());
                        logs.setName("com.hchenpan.controller.BuylistController.create");
                        logs.setParams("com.hchenpan.pojo.Buylist");
                        logs.setDescription("新增采购计划物资明细");
                        logs.setUpdaterid(loginUser.getId());
                        logs.setIpaddress(getRomoteIP());
                        logs.setOptcontent(GetGsonString(buylist));
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
     * 功能：更新采购计划明细
     */
    @ResponseBody
    @PostMapping("/buylist/update")
    public String update() {
        if (checkuser()) {
            String arrayList = request.getParameter("arrayList");
            //将 arrayList 字符串转换成 json 对象
            //将 json 对象转换成 stock 集合
            List<Buylist> list = new ArrayList<>();
            String spcode = buyService.selectOne(new EntityWrapper<Buy>().eq("buycode", list.get(0).getBuycode())).getSpcode();
            if (!"00".equals(spcode)) {
                return "审批中";
            } else {
                int k = 0;
                StringBuilder flag = null;
                for (Buylist buylist : list) {
                    Buylist d = buylistService.selectById(buylist.getId());

                    //获取计划的剩余数量
                    String synum = planlistService.selectOne(new EntityWrapper<Planlist>()
                            .eq("plancode", buylist.getPlancode())
                            .eq("wzcode", buylist.getWzcode())
                            .eq("flag", "E")
                    ).getSynum();
                    BigDecimal synum1 = new BigDecimal(synum);
                    String cg1 = d.getBuynum().trim();
                    BigDecimal cg2 = new BigDecimal(cg1);
                    BigDecimal synum2 = synum1.add(cg2);
                    String b = buylist.getBuynum().trim();
                    //旧值
                    String c = d.getBuymoney().trim();
                    BigDecimal b1 = new BigDecimal(b);
                    int result = b1.compareTo(synum2);
                    if (result > 0) {
                        k++;
                        flag.append("需求计划号为：").append(buylist.getPlancode()).append(" 物资编码为：").append(buylist.getWzcode()).append(",");
                    }
                }
                if (k != 0) {
                    return flag.toString().substring(flag.length() - 1) + "已存在,如有需要请修改数量";
                } else {
                    /*通用字段赋值*/
                    User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                    String timeString = GetCurrentTime();
                    for (Buylist buylist : list) {
                        buylist.setUpdaterid(loginUser.getId());
                        buylist.setUpdater(loginUser.getUsername());
                        buylist.setUpdatetime(timeString);

                        Buylist d = buylistService.selectById(buylist.getId());
                        String oldcontent = GetGsonString(d);
                        //获取计划的剩余数量
                        String synum = planlistService.selectOne(new EntityWrapper<Planlist>()
                                .eq("plancode", buylist.getPlancode())
                                .eq("wzcode", buylist.getWzcode())
                                .eq("flag", "E")
                        ).getSynum();
                        BigDecimal synum1 = new BigDecimal(synum);
                        String cg1 = d.getBuynum().trim();
                        BigDecimal cg2 = new BigDecimal(cg1);
                        BigDecimal synum2 = synum1.add(cg2);
                        //旧值
                        String c = d.getBuymoney().trim();
                        BigDecimal c1 = new BigDecimal(c);
                        String cg3 = buylist.getBuynum().trim();
                        BigDecimal cg4 = new BigDecimal(cg3);
                        BigDecimal synumnew = synum2.subtract(cg4);
                        Planlist pl = planlistService.selectOne(new EntityWrapper<Planlist>().eq("plancode", buylist.getPlancode()).eq("wzcode", buylist.getWzcode()).eq("flag", "E"));

                        pl.setSynum(synumnew.toString());

                        planlistService.updateById(pl);

                        String a = buylist.getBuynum().trim();
                        buylist.setFlag("E");
                        buylist.setSynum(buylist.getBuynum().trim());
                        String dd = buylist.getBuyprice().trim();
                        BigDecimal a1 = new BigDecimal(a);
                        BigDecimal d1 = new BigDecimal(dd);
                        BigDecimal ad1 = a1.multiply(d1).setScale(2, BigDecimal.ROUND_HALF_UP);
                        buylist.setBuymoney(ad1.toString());

                        String xqmoney = buyService.selectOne(new EntityWrapper<Buy>().eq("buycode", buylist.getBuycode())).getBuysummoney();

                        BigDecimal xqmoney1 = new BigDecimal(xqmoney);

                        BigDecimal xq1 = xqmoney1.subtract(c1);
                        BigDecimal xq2 = xq1.add(ad1);
                        Buy p = buyService.selectOne(new EntityWrapper<Buy>().eq("buycode", buylist.getBuycode()).eq("flag", "E"));
                        p.setBuysummoney(xq2.toString());
                        buyService.updateById(p);
                        buylistService.updateById(buylist);
                        //写入日志表
                        Logs logs = new Logs();
                        logs.setId(getUUID());
                        logs.setFlagid(buylist.getId());
                        logs.setName("com.hchenpan.controller.BuylistController.update");
                        logs.setParams("com.hchenpan.pojo.Buylist");
                        logs.setDescription("修改采购计划物资明细大类");
                        logs.setUpdaterid(loginUser.getId());
                        logs.setIpaddress(getRomoteIP());
                        logs.setOptcontent(GetGsonString(buylist));
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

        }
        return ERROR;
    }

    /**
     * 功能:删除采购计划明细
     */
    @ResponseBody
    @GetMapping("/buylist/delete")
    public String delete(Buylist buylist) {
        if (checkuser()) {
            Buylist old = buylistService.selectById(buylist.getId());
            String oldcontent = GetGsonString(old);
            String spcode = buyService.selectOne(new EntityWrapper<Buy>().eq("buycode", old.getBuycode())).getSpcode();
            if (!"00".equals(spcode)) {
                return "审批中";
            } else {
                /*通用字段赋值*/
                User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                String timeString = GetCurrentTime();
                buylist.setUpdaterid(loginUser.getId());
                buylist.setUpdater(loginUser.getUsername());
                buylist.setUpdatetime(timeString);
                buylist.setFlag("D");

                //获取计划的剩余数量
                String synum = planlistService.selectOne(new EntityWrapper<Planlist>()
                        .eq("plancode", old.getPlancode())
                        .eq("wzcode", old.getWzcode())
                        .eq("flag", "E")
                ).getSynum();
                BigDecimal synum1 = new BigDecimal(synum);

                String cg1 = old.getBuynum().trim();
                BigDecimal cg2 = new BigDecimal(cg1);
                BigDecimal synumnew = synum1.add(cg2);
                Planlist pl = planlistService.selectOne(new EntityWrapper<Planlist>()
                        .eq("plancode", old.getPlancode())
                        .eq("wzcode", old.getWzcode())
                        .eq("flag", "E")
                );

                pl.setSynum(synumnew.toString());

                planlistService.updateById(pl);


                String a = old.getBuymoney().trim();
                String xqmoney = buyService.selectOne(new EntityWrapper<Buy>()
                        .eq("buycode", old.getBuycode()))
                        .getBuysummoney();

                BigDecimal a1 = new BigDecimal(a);
                BigDecimal b1 = new BigDecimal(xqmoney);
                BigDecimal b2 = b1.subtract(a1).setScale(2, BigDecimal.ROUND_HALF_UP);
                Buy p = buyService.selectOne(new EntityWrapper<Buy>()
                        .eq("buycode", old.getBuycode()).eq("flag", "E"));


                p.setBuysummoney(b2.toString());
                buyService.updateById(p);
                //写入日志表
                Logs logs = new Logs();
                logs.setId(getUUID());
                logs.setFlagid(buylist.getId());
                logs.setName("com.hchenpan.controller.BuylistController.delete");
                logs.setParams("com.hchenpan.pojo.Buylist");
                logs.setDescription("删除采购计划申请大类");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(buylist));
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
}