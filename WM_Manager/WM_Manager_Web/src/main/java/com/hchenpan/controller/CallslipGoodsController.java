package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.CallslipGoods;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.User;
import com.hchenpan.pojo.Warehousinglist;
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
 * ClassName : com.hchenpan.controller.CallslipGoodsController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class CallslipGoodsController extends BaseController {
    private final CallslipGoodsService callslipGoodsService;
    private final LogsService logsService;
    private final StockService stockService;
    private final CallslipService callslipService;
    private final WarehousinglistService warehousinglistService;
    private final UserService userService;

    @Autowired
    public CallslipGoodsController(CallslipGoodsService callslipGoodsService, LogsService logsService, StockService stockService, CallslipService callslipService, WarehousinglistService warehousinglistService, UserService userService) {
        this.callslipGoodsService = callslipGoodsService;
        this.logsService = logsService;
        this.stockService = stockService;
        this.callslipService = callslipService;
        this.warehousinglistService = warehousinglistService;
        this.userService = userService;
    }


    /**
     * 功能:采购入库维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/callslipgoods/searchformx")
    public String searchformx(CallslipGoods callslipGoods) {
        String[] aa = callslipGoods.getCallslipcode().split(",");
        String callslipcodes = "";
        for (int i = 0; i < aa.length; i++) {
            callslipcodes = callslipcodes + "'" + aa[i] + "'" + ",";
        }
        callslipcodes = callslipcodes.substring(0, callslipcodes.length() - 1);
        EntityWrapper<CallslipGoods> ew = new EntityWrapper<>();
        ew.eq("falg", "E");
        ew.gt("sysum", "0");
        ew.in("callslipcode", callslipcodes);
        ew.orderBy("updatetime");
        return ListToGson(callslipGoodsService.selectList(ew));
    }

    /**
     * 功能:提供所有采购入库信息,供下拉
     */
    @ResponseBody
    @PostMapping("/callslipgoods/getwzlist")
    public String getwzlist(CallslipGoods callslipGoods) {
        return ListToGson(callslipGoodsService.selectList(new EntityWrapper<CallslipGoods>()
                .eq("flag", "E")
                .eq("callslipcode", callslipGoods.getCallslipcode())
        ));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/callslipgoods/getbyid")
    public String getbyid(CallslipGoods callslipGoods) {
        return GetGsonString(callslipGoodsService.selectById(callslipGoods.getId()));
    }

    /**
     * 功能：新增采购入库
     */
    @ResponseBody
    @PostMapping("/callslipgoods/create")
    public String create() {
        if (checkuser()) {
            //非空时进入
            //非空时进入

            String arrayList = request.getParameter("arrayList");
            String callslipcode = request.getParameter("callslipcode");

            //替换字符串中的'/'
            String str = arrayList.replaceAll("\\\\", "\"");
            //转为json对象
            JSONArray json = JSONArray.fromObject(str);

            List<CallslipGoods> list = (List<CallslipGoods>) JSONArray.toCollection(json, CallslipGoods.class);

            String spcode = callslipService.getstatus(callslipcode);
            if (spcode.equals("00")) {
                /*通用字段赋值*/
                User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                String timeString = GetCurrentTime();
                for (int i = 0; i < list.size(); i++) {
                    //			判断领料单下面是否已经有此物资
                    int wznum = callslipGoodsService.getwznum(list.get(i).getWzcode().trim(), "E", callslipcode, "库存表", "", "", "", "");
                    if (wznum == 0) {
                        list.get(i).setFlag("E");
                        list.get(i).setSum("0.00");
                        list.get(i).setSysum("0.00");
                        list.get(i).setComefrom("库存表");
                        list.get(i).setCallslipcode(callslipcode);

                        list.get(i).setId(getUUID());
                        list.get(i).setFlag("E");
                        list.get(i).setCreatorid(loginUser.getId());
                        list.get(i).setCreator(loginUser.getUsername());
                        list.get(i).setCreatetime(timeString);
                        list.get(i).setUpdaterid(loginUser.getId());
                        list.get(i).setUpdater(loginUser.getUsername());
                        list.get(i).setUpdatetime(timeString);
                        list.get(i).setCreatetime(timeString);

                        list.get(i).setUpdatetime(timeString);
                        list.get(i).setCreatetime(timeString);
                        callslipGoodsService.insert(list.get(i));

                        //写入日志表
                        Logs logs = new Logs();
                        logs.setId(getUUID());
                        logs.setFlagid(list.get(i).getId());
                        logs.setName("com.hchenpan.controller.CallslipGoodsController.create");
                        logs.setParams("com.hchenpan.pojo.CallslipGoods");
                        logs.setDescription("新增物资信息-从库存表");
                        logs.setUpdaterid(loginUser.getId());
                        logs.setIpaddress(getRomoteIP());
                        logs.setOptcontent(GetGsonString(list.get(i)));
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
            } else {
                return "状态";
            }
        }
        return ERROR;
    }

    /**
     * 功能：新增物资信息（从需求单）
     */
    @ResponseBody
    @PostMapping("/callslipgoods/createxq")
    public String createxq(CallslipGoods callslipGoods) {
        if (checkuser()) {
            //非空时进入
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            String arrayList = request.getParameter("arrayList");
            String callslipcode = request.getParameter("callslipcode");
            String stockcode = request.getParameter("stockcode");

            //替换字符串中的'/'
            String str = arrayList.replaceAll("\\\\", "\"");
            //转为json对象
            JSONArray json = JSONArray.fromObject(str);

            List<CallslipGoods> list = (List<CallslipGoods>) JSONArray.toCollection(json, CallslipGoods.class);


            String spcode = callslipService.getstatus(callslipcode);
            if (spcode.equals("00")) {
                for (int i = 0; i < list.size(); i++) {
//			判断领料单下面是否已经有此物资

                    int wznum = callslipGoodsService.getwznum(list.get(i).getWzcode().trim(), "E", callslipcode, "需求单", "", "", "", "");
                    if (wznum == 0) {
                        list.get(i).setFlag("E");
                        list.get(i).setSum(list.get(i).getSycknum());
                        list.get(i).setSysum(list.get(i).getSycknum());
                        list.get(i).setComefrom("需求单");
                        list.get(i).setCallslipcode(callslipcode);
                        list.get(i).setStockcode(stockcode);

                        list.get(i).setUpdatetime(timeString);
                        list.get(i).setCreatetime(timeString);
                        Warehousinglist p = warehousinglistService.getgoods(list.get(i).getPlancode(), list.get(i).getBuycode(), list.get(i).getContractbasicid(), list.get(i).getRkcode(), list.get(i).getWzcode());
                        p.setSycknum("0.00");
                        warehousinglistService.updateById(p);
                        list.get(i).setId(getUUID());
                        list.get(i).setFlag("E");
                        list.get(i).setCreatorid(loginUser.getId());
                        list.get(i).setCreator(loginUser.getUsername());
                        list.get(i).setCreatetime(timeString);
                        list.get(i).setUpdaterid(loginUser.getId());
                        list.get(i).setUpdater(loginUser.getUsername());
                        list.get(i).setUpdatetime(timeString);
                        list.get(i).setCreatetime(timeString);
                        callslipGoodsService.insert(list.get(i));

                        //写入日志表
                        Logs logs = new Logs();
                        logs.setId(getUUID());
                        logs.setFlagid(list.get(i).getId());
                        logs.setName("com.hchenpan.controller.CallslipGoodsController.create");
                        logs.setParams("com.hchenpan.pojo.CallslipGoods");
                        logs.setDescription("新增物资信息-从需求单");
                        logs.setUpdaterid(loginUser.getId());
                        logs.setIpaddress(getRomoteIP());
                        logs.setOptcontent(GetGsonString(list.get(i)));
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
            } else {
                return "状态";
            }
        }
        return ERROR;
    }

    /**
     * 功能：物资信息列表
     */
    @ResponseBody
    @PostMapping("/callslipgoods/list")
    public String list(CallslipGoods callslipGoods) {
        Page<CallslipGoods> page = getPage();
        EntityWrapper<CallslipGoods> ew = new EntityWrapper<>();
        if (StringUtil.notTrimEmpty(callslipGoods.getCallslipcode())) {
            ew.like("callslipcode", callslipGoods.getCallslipcode(), SqlLike.DEFAULT);
        }
        ew.eq("flag", "E");
        return jsonPage(callslipGoodsService.selectPage(page, ew));
    }

    /**
     * 功能：更新采购入库
     */
    @ResponseBody
    @PostMapping("/callslipgoods/update")
    public String update(CallslipGoods callslipGoods) {
        if (checkuser()) {
            //非空时进入
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            String arrayList = request.getParameter("arrayList");
            //替换字符串中的'/'
            String str = arrayList.replaceAll("\\\\", "\"");
            //转为json对象
            JSONArray json = JSONArray.fromObject(str);

            List<CallslipGoods> list = (List<CallslipGoods>) JSONArray.toCollection(json, CallslipGoods.class);
            String spcode = callslipService.getstatus(list.get(0).getCallslipcode());
            if ("00".equals(spcode)) {

                for (int i = 0; i < list.size(); i++) {
                    if ("需求单".equals(list.get(i).getComefrom())) {

                        Warehousinglist p = warehousinglistService.getgoods(list.get(i).getPlancode(), list.get(i).getBuycode(), list.get(i).getContractbasicid(), list.get(i).getRkcode(), list.get(i).getWzcode());
                        String sycknum = p.getSycknum();
                        BigDecimal sycknum1 = new BigDecimal(sycknum);
                        //CallslipGoods c=callslipgoodsservice.getwz(list.get(i).getWzcode(),"E",list.get(i).getCallslipcode(),"需求单",list.get(i).getPlancode(), list.get(i).getBuycode(),list.get(i).getContractbasicid(),list.get(i).getRkcode());
                        //String oldsum=c.getSum();
                        String oldsum = callslipGoodsService.selectOne(new EntityWrapper<CallslipGoods>()
                                .eq("wzcode", list.get(i).getWzcode())
                                .eq("callslipcode", list.get(i).getCallslipcode())
                                .eq("comefrom", "需求单")
                                .eq("plancode", list.get(i).getPlancode())
                                .eq("buycode", list.get(i).getBuycode())
                                .eq("contractbasicid", list.get(i).getContractbasicid())
                                .eq("rkcode", list.get(i).getRkcode())
                                .eq("flag", "E")
                        ).getSum();

                        BigDecimal oldsum1 = new BigDecimal(oldsum);
                        BigDecimal sum = sycknum1.add(oldsum1);
                        String nowsum = list.get(i).getSum();
                        BigDecimal nowsum1 = new BigDecimal(nowsum);

//					  		新的剩余量
                        BigDecimal newsycknum = sum.subtract(nowsum1);
                        p.setSycknum(newsycknum.toString());

                        list.get(i).setSysum(list.get(i).getSum());

                    } else {

                        list.get(i).setSysum(list.get(i).getSum());

                    }

                    callslipGoodsService.updateById(list.get(i));

//写入日志表
                    Logs logs = new Logs();
                    logs.setId(getUUID());
                    logs.setFlagid(list.get(i).getId());
                    logs.setName("com.hchenpan.controller.CallslipGoodsController.update");
                    logs.setParams("com.hchenpan.pojo.CallslipGoods");
                    logs.setDescription("修改物资领用数量");
                    logs.setUpdaterid(loginUser.getId());
                    logs.setIpaddress(getRomoteIP());
                    logs.setOptcontent(GetGsonString(list.get(i)));
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
            } else {
                return "状态";
            }
        }
        return ERROR;
    }

    /**
     * 功能:删除采购入库
     */
    @ResponseBody
    @GetMapping("/callslipgoods/delete")
    public String delete(CallslipGoods callslipGoods) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            String id = callslipGoods.getId();

            //maintenanceservice.delete(id);
            CallslipGoods m = callslipGoodsService.selectById(id);
            String spcode = callslipService.getstatus(m.getCallslipcode());
            if ("00".equals(spcode)) {
                if ("需求单".equals(m.getComefrom())) {
                    Warehousinglist p = warehousinglistService.getgoods(m.getPlancode(), m.getBuycode(), m.getContractbasicid(), m.getRkcode(), m.getWzcode());
                    String sycknum = p.getSycknum();
                    BigDecimal sycknum1 = new BigDecimal(sycknum);
                    String sum = m.getSum();
                    BigDecimal sum1 = new BigDecimal(sum);
                    BigDecimal newsycknum = sum1.add(sycknum1);
                    p.setSycknum(newsycknum.toString());
                    warehousinglistService.updateById(p);
                }


                m.setFlag("D");
                m.setUpdatetime(timeString);
                callslipGoodsService.deleteById(m);

                //写入日志表
                Logs logs = new Logs();
                logs.setId(getUUID());
                logs.setFlagid(m.getId());
                logs.setName("com.hchenpan.controller.CallslipGoodsController.update");
                logs.setParams("com.hchenpan.pojo.CallslipGoods");
                logs.setDescription("修改物资领用数量");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(m));
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
            } else {
                return "状态";
            }
        }
        return ERROR;
    }
}