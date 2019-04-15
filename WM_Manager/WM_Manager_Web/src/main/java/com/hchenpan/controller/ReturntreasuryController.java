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
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.ReturntreasuryController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class ReturntreasuryController extends BaseController {
    private final ReturntreasuryService returntreasuryService;
    private final ReturntreasurylistService returntreasurylistService;
    private final LogsService logsService;
    private final StockService stockService;
    private final UserService userService;

    @Autowired
    public ReturntreasuryController(ReturntreasuryService returntreasuryService, ReturntreasurylistService returntreasurylistService, LogsService logsService, StockService stockService, UserService userService) {
        this.returntreasuryService = returntreasuryService;
        this.returntreasurylistService = returntreasurylistService;
        this.logsService = logsService;
        this.stockService = stockService;
        this.userService = userService;
    }

    @GetMapping("/returntreasury")
    public String returntreasury() {
        return View("/reserve/returntreasury");
    }

    /**
     * 功能:采购入库维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/returntreasury/search")
    public String search(Returntreasury returntreasury) {
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        Page<Returntreasury> page = getPage();
        EntityWrapper<Returntreasury> ew = new EntityWrapper<>();

        if (StringUtils.isNotEmpty(returntreasury.getTkcode())) {
            ew.like("tkcode", returntreasury.getTkcode(), SqlLike.DEFAULT);

        }
        if (StringUtils.isNotEmpty(returntreasury.getDatetime1())) {
            ew.gt("sqdate", returntreasury.getDatetime1());

        }
        if (StringUtils.isNotEmpty(returntreasury.getDatetime2())) {
            ew.lt("sqdate", returntreasury.getDatetime2());

        }
        ew.eq("userid", loginUser.getId());
        ew.eq("flag", "E");

        return jsonPage(returntreasuryService.selectPage(page, ew));
    }

    /**
     * 功能:提供所有采购入库信息,供下拉
     */
    @ResponseBody
    @PostMapping("/returntreasury/getallbyid")
    public String getallbyid(Returntreasury returntreasury) {
        return GetGsonString(returntreasuryService.selectById(returntreasury.getId()));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/returntreasury/getbyid")
    public String getbyid(Returntreasury returntreasury) {
        return ERROR;
    }

    /**
     * 功能：新增采购入库
     */
    @ResponseBody
    @PostMapping("/returntreasury/create")
    public String create(Returntreasury returntreasury) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            returntreasury.setTkcode(returntreasuryService.createtkcode());
            returntreasury.setTktype("采购退库红冲");
            returntreasury.setUserid(loginUser.getId().trim());
            returntreasury.setSqdate(returntreasury.getSqdate().trim());
            returntreasury.setSqr(returntreasury.getSqr().trim());
            returntreasury.setTkczr(loginUser.getRealname());
            returntreasury.setTkczrid(loginUser.getId());
            returntreasury.setTkreason(returntreasury.getTkreason().trim());
            returntreasury.setTkstatus("未退货");
            returntreasury.setUpdatetime(GetCurrentTime());
            returntreasury.setFlag("E");
            returntreasury.setId(getUUID());
            returntreasury.setCreatorid(loginUser.getId());
            returntreasury.setCreator(loginUser.getUsername());
            returntreasury.setCreatetime(timeString);
            returntreasury.setUpdaterid(loginUser.getId());
            returntreasury.setUpdater(loginUser.getUsername());
            returntreasury.setUpdatetime(timeString);
            returntreasury.setFlag("E");

            returntreasuryService.insert(returntreasury);


            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(returntreasury.getId());
            logs.setName("com.hchenpan.controller.ReturntreasuryController.create");
            logs.setParams("com.hchenpan.pojo.Returntreasury类");
            logs.setDescription("新增采购退货入库申请");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(returntreasury));
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
        return ERROR;
    }

    /**
     * 功能：更新采购入库
     */
    @ResponseBody
    @PostMapping("/returntreasury/update")
    public String update(Returntreasury returntreasury) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            Returntreasury d = returntreasuryService.selectById(returntreasury.getId());
            String oldcontent = GetGsonString(d);
            returntreasury.setTkczr(loginUser.getRealname());
            returntreasury.setTkczrid(loginUser.getId());
            returntreasury.setUpdaterid(loginUser.getId());
            returntreasury.setUpdater(loginUser.getUsername());
            returntreasury.setUpdatetime(timeString);

            returntreasuryService.updateById(returntreasury);

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(returntreasury.getId());
            logs.setName("com.hchenpan.controller.ReturntreasuryController.update");
            logs.setParams("com.hchenpan.pojo.Returntreasury类");
            logs.setDescription("修改采购退库申请大类");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(returntreasury));
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
     * 功能:删除采购入库
     */
    @ResponseBody
    @GetMapping("/returntreasury/delete")
    public String delete(Returntreasury returntreasury) {
        if (checkuser()) {
            {
                /*通用字段赋值*/
                User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                String timeString = GetCurrentTime();
                Returntreasury d = returntreasuryService.selectById(returntreasury.getId());
                String oldcontent = GetGsonString(d);
                d.setUpdaterid(loginUser.getId());
                d.setUpdater(loginUser.getUsername());
                d.setUpdatetime(timeString);

                returntreasurylistService.delete(new EntityWrapper<Returntreasurylist>()
                        .eq("tkcode", d.getTkcode())
                );
                returntreasuryService.deleteById(d);

                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(returntreasury.getId());
                logs.setName("com.hchenpan.controller.ReturntreasuryController.delete");
                logs.setParams("com.hchenpan.pojo.Returntreasury类");
                logs.setDescription("删除采购入库退库申请大类");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(returntreasury));
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
        return ERROR;
    }

    /**
     * 领料单物资出库
     */
    @ResponseBody
    @PostMapping("/returntreasury/tk")
    public String tk(Returntreasury returntreasury) {
        if (checkuser()) {
            //非空时进入
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            Returntreasury t = returntreasuryService.selectById(returntreasury.getId());
            int kongsl = returntreasurylistService.selectCount(new EntityWrapper<Returntreasurylist>()
                    .eq("flag", "E")
                    .eq("tkcode", t.getTkcode())
                    .eq("sjthsl", "0.00")
            );
            int kongsl1 = returntreasurylistService.selectCount(new EntityWrapper<Returntreasurylist>()
                    .eq("tkcode", t.getTkcode())
            );
            if (kongsl > 0 || kongsl1 == 0) {
                return "存在数量为空";
            } else {
                List<Returntreasurylist> returntreasurylist = returntreasurylistService.selectList(new EntityWrapper<Returntreasurylist>()
                        .eq("tkcode", t.getTkcode())
                        .eq("flag", "E")
                );

                //						循环比较库存数量和领料单数量
                int k = 0;
                String flag = "";
                for (int i = 0; i < returntreasurylist.size(); i++) {
                    List<Stock> stocklist = stockService.getwzlist(returntreasurylist.get(i).getStorehousecode().trim(), returntreasurylist.get(i).getWzcode().trim());
                    String kcnumber = stocklist.get(0).getBqend().trim();
                    BigDecimal kcnumber1 = new BigDecimal(kcnumber);
                    String sjthnumber = returntreasurylist.get(i).getSjthsl().trim();
                    BigDecimal sjthnumber1 = new BigDecimal(sjthnumber);
                    int a = sjthnumber1.compareTo(kcnumber1);

                    if (a > 0) {
                        k = k + 1;
                        flag += "入库流水号为：" + returntreasurylist.get(i).getRkcode() + "合同号为：" + returntreasurylist.get(i).getContractbasicid() + " 采购计划号为：" + returntreasurylist.get(i).getBuycode() + " 需求计划号为：" + returntreasurylist.get(i).getPlancode() + " 物资编码为：" + returntreasurylist.get(i).getWzcode() + ",";
                        continue;
                    }
                }

                if (k == 0) {
                    //						  循环计算一个物资的单价、总价并反填库存
                    for (int i = 0; i < returntreasurylist.size(); i++) {
                        List<Stock> stocklist = stockService.getwzlist(returntreasurylist.get(i).getStorehousecode().trim(), returntreasurylist.get(i).getWzcode().trim());
                        String kcnumber = stocklist.get(0).getBqend().trim();
                        BigDecimal kcnumber1 = new BigDecimal(kcnumber);
                        String sjthnumber = returntreasurylist.get(i).getSjthsl().trim();
                        String sjthmoney = returntreasurylist.get(i).getSjthmoney().trim();
                        BigDecimal sjthmoney1 = new BigDecimal(sjthmoney);
                        BigDecimal sjthnumber1 = new BigDecimal(sjthnumber);
                        int a = sjthnumber1.compareTo(kcnumber1);
                        if (a == 0) {
                            Stock st = stockService.selectById(stocklist.get(0).getId());
                            String bqout = st.getBqout().trim();
                            BigDecimal bqout1 = new BigDecimal(bqout);
                            BigDecimal bqoutnew = bqout1.add(sjthnumber1).setScale(2, BigDecimal.ROUND_HALF_UP);
                            st.setBqout(bqoutnew.toString());
                            String bqoutmoney = st.getBqoutmoney().trim();
                            BigDecimal bqoutmoney1 = new BigDecimal(bqoutmoney);
                            BigDecimal bqoutmoneynew = bqoutmoney1.add(sjthmoney1).setScale(2, BigDecimal.ROUND_HALF_UP);
                            st.setBqoutmoney(bqoutmoneynew.toString());
                            st.setBqend("0.00");
                            st.setBqendmoney("0.00");
                            st.setPrice("0.00");
                            st.setUpdatetime(GetCurrentTime());
                            stockService.updateById(st);
                        } else if (a < 0) {
                            Stock st = stockService.selectById(stocklist.get(0).getId());
                            String bqout = st.getBqout().trim();
                            BigDecimal bqout1 = new BigDecimal(bqout);
                            BigDecimal bqoutnew = bqout1.add(sjthnumber1).setScale(2, BigDecimal.ROUND_HALF_UP);
                            st.setBqout(bqoutnew.toString());
                            String bqoutmoney = st.getBqoutmoney().trim();
                            BigDecimal bqoutmoney1 = new BigDecimal(bqoutmoney);
                            BigDecimal bqoutmoneynew = bqoutmoney1.add(sjthmoney1).setScale(2, BigDecimal.ROUND_HALF_UP);
                            st.setBqoutmoney(bqoutmoneynew.toString());
                            String bqend = st.getBqend().trim();
                            BigDecimal bqend1 = new BigDecimal(bqend);
                            BigDecimal bqendnew = bqend1.subtract(sjthnumber1).setScale(2, BigDecimal.ROUND_HALF_UP);
                            st.setBqend(bqendnew.toString());
                            String bqendmoney = st.getBqendmoney().trim();
                            BigDecimal bqendmoney1 = new BigDecimal(bqendmoney);
                            BigDecimal bqendmoneynew = bqendmoney1.subtract(sjthmoney1).setScale(2, BigDecimal.ROUND_HALF_UP);
                            st.setBqendmoney(bqendmoneynew.toString());
                            BigDecimal price = bqendmoneynew.divide(bqendnew, 2, BigDecimal.ROUND_HALF_UP);
                            st.setPrice(price.toString());
                            st.setUpdatetime(GetCurrentTime());
                            stockService.updateById(st);
                        }

                    }
                    t.setTkstatus("已退货");
                    t.setUpdatetime(GetCurrentTime());
                    returntreasuryService.updateById(t);

                    //写入日志表
                    Logs logs = new Logs();
                    logs.setFlagid(returntreasury.getId());
                    logs.setName("com.hchenpan.controller.ReturntreasuryController.tk");
                    logs.setParams("com.hchenpan.pojo.Returntreasury类");
                    logs.setDescription("采购入库退货");
                    logs.setUpdaterid(loginUser.getId());
                    logs.setIpaddress(getRomoteIP());
                    logs.setOptcontent(GetGsonString(returntreasury));
                    /* 修改，需要保存修改前后的数据 */
//                    logs.setOldcontent(oldcontent);
                    logs.setCreatorid(loginUser.getId());
                    logs.setCreator(loginUser.getUsername());
                    logs.setCreatetime(timeString);
                    logs.setUpdater(loginUser.getUsername());
                    logs.setRealname(loginUser.getRealname());
                    logs.setUpdatetime(timeString);
                    logs.setId(getUUID());
                    logsService.insert(logs);
                    return SUCCESS;


                } else if (k != 0) {
                    if (!flag.equals("success") && !flag.equals("error") && !flag.equals("存在数量为空")) {
                        flag = flag.substring(5, flag.length() - 1);
                        flag += "实际退货量大于实际库存量";
                    }
                    return flag;
                }
            }

        }
        return ERROR;
    }
}