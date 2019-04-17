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
import java.util.Iterator;
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.CancellingstocksWZController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class CancellingstocksWZController extends BaseController {
    private final CancellingstocksSQService cancellingstocksSQService;
    private final CancellingstocksWZService cancellingstocksWZService;
    private final StockService stockService;
    private final CallslipGoodsService callslipGoodsService;
    private final LogsService logsService;
    private final UserService userService;

    @Autowired
    public CancellingstocksWZController(CancellingstocksSQService cancellingstocksSQService, CancellingstocksWZService cancellingstocksWZService, StockService stockService, CallslipGoodsService callslipGoodsService, LogsService logsService, UserService userService) {
        this.cancellingstocksSQService = cancellingstocksSQService;
        this.cancellingstocksWZService = cancellingstocksWZService;
        this.stockService = stockService;
        this.callslipGoodsService = callslipGoodsService;
        this.logsService = logsService;
        this.userService = userService;
    }


    /**
     * 功能:采购入库维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/cancellingstockswz/search")
    public String search(CancellingstocksWZ cancellingstocksWZ) {
        Page<CancellingstocksWZ> page = getPage();
        EntityWrapper<CancellingstocksWZ> ew = new EntityWrapper<>();
        if (StringUtil.notTrimEmpty(cancellingstocksWZ.getTKid())) {
            ew.like("TKid", cancellingstocksWZ.getTKid(), SqlLike.DEFAULT);
        }

        ew.eq("flag", "E");
        return jsonPage(cancellingstocksWZService.selectPage(page, ew));

    }

    /**
     * 功能:提供所有采购入库信息,供下拉
     */
    @ResponseBody
    @PostMapping("/cancellingstockswz/getall")
    public String getall() {
        return ERROR;
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/cancellingstockswz/getbyid")
    public String getbyid(CancellingstocksWZ cancellingstocksWZ) {
        return ERROR;
    }

    /**
     * 功能：新增采购入库
     */
    @ResponseBody
    @PostMapping("/cancellingstockswz/create")
    public String create(CancellingstocksWZ cancellingstocksWZ) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            String TKcode = request.getParameter("TKcode").trim();
            String arrayList = request.getParameter("arrayList");
            //替换字符串中的'/'
            String str = arrayList.replaceAll("\\\\", "\"");
            //转为json对象
            JSONArray json = JSONArray.fromObject(str);

            List<CallslipGoods> list = (List<CallslipGoods>) JSONArray.toCollection(json, CallslipGoods.class);

            CancellingstocksSQ CS = cancellingstocksSQService.getbycode(TKcode);
            if ("已退库".equals(CS.getTkstatus())) {
                return "已退库";
            } else {
                for (Iterator<CallslipGoods> iterator = list.iterator(); iterator.hasNext(); ) {
                    CallslipGoods callslipGoods = callslipGoodsService.getbycode(iterator.next());
                    if (cancellingstocksWZService.selectCount(new EntityWrapper<CancellingstocksWZ>()
                            .eq("wzcode", callslipGoods.getWzcode())
                            .eq("TKcode", TKcode)
                            .eq("callslipcode", callslipGoods.getCallslipcode())
                            .eq("stockcode", callslipGoods.getStockcode())
                            .eq("flag", "E")
                    ) > 0) {
                        CancellingstocksWZ cwNew = new CancellingstocksWZ();
                        cwNew.setTKcode(CS.getTKcode());
                        cwNew.setTKid(CS.getId());
                        cwNew.setCallslipcode(callslipGoods.getCallslipcode());
                        cwNew.setWzcode(callslipGoods.getWzcode());
                        cwNew.setWzname(callslipGoods.getWzname());
                        cwNew.setUnit(callslipGoods.getUnit());
                        cwNew.setModelSpecification(callslipGoods.getModelspcification());
                        cwNew.setCreatetime(timeString);
                        cwNew.setPrice(callslipGoods.getPrice());
                        cwNew.setSum(callslipGoods.getSum());
                        cwNew.setSysum(callslipGoods.getSysum());
                        cwNew.setTksum("0.00");
                        cwNew.setTkprice(callslipGoods.getPrice());
                        cwNew.setZjcode(callslipGoods.getZjcode());
                        cwNew.setZjname(callslipGoods.getZjname());
                        cwNew.setStockcode(callslipGoods.getStockcode());
                        cwNew.setStockname(callslipGoods.getStockname());
                        cwNew.setUpdatetime(timeString);
                        cwNew.setFlag("E");
                        cwNew.setId(getUUID());
                        cwNew.setFlag("E");
                        cwNew.setCreatorid(loginUser.getId());
                        cwNew.setCreator(loginUser.getUsername());
                        cwNew.setCreatetime(timeString);
                        cwNew.setUpdaterid(loginUser.getId());
                        cwNew.setUpdater(loginUser.getUsername());
                        cwNew.setUpdatetime(timeString);
                        cwNew.setCreatetime(timeString);

                        cancellingstocksWZService.insert(cwNew);
                        //写入日志表
                        Logs logs = new Logs();
                        logs.setId(getUUID());
                        logs.setFlagid(cwNew.getId());
                        logs.setName("com.hchenpan.controller.CancellingstocksWZController.create");
                        logs.setParams("com.hchenpan.pojo.CancellingstocksWZ");
                        logs.setDescription("新增领料退库物资明细");
                        logs.setUpdaterid(loginUser.getId());
                        logs.setIpaddress(getRomoteIP());
                        logs.setOptcontent(GetGsonString(cwNew));
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
                        return "领料单号为 " + callslipGoods.getCallslipcode() + " 物资编码为 " + callslipGoods.getWzcode()
                                + "的退货明细已存在，请核实后重试！";
                    }

                }

            }

        }
        return ERROR;
    }

    /**
     * 功能：更新采购入库
     */
    @ResponseBody
    @PostMapping("/cancellingstockswz/update")
    public String update(CancellingstocksWZ cancellingstocksWZ) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            String TKcode = request.getParameter("TKcode").trim();
            String arrayList = request.getParameter("arrayList");
            //替换字符串中的'/'
            String str = arrayList.replaceAll("\\\\", "\"");
            //转为json对象
            JSONArray json = JSONArray.fromObject(str);

            List<CancellingstocksWZ> list = (List<CancellingstocksWZ>) JSONArray.toCollection(json, CancellingstocksWZ.class);


            for (Iterator<CancellingstocksWZ> iterator = list.iterator(); iterator.hasNext(); ) {
                CancellingstocksWZ CW = iterator.next();
                BigDecimal tksum = new BigDecimal(CW.getTksum()).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal sum = new BigDecimal(CW.getSysum()).setScale(2, BigDecimal.ROUND_HALF_UP);
                String num = sum.subtract(tksum).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
                if (Integer.parseInt(num) < 0) {
                    return "领料单号为 " + CW.getCallslipcode() + "物资编码为 " + CW.getWzcode() + " 的退库数量大于剩余可退库数量，请修改";

                }
            }

            for (Iterator<CancellingstocksWZ> iterator = list.iterator(); iterator.hasNext(); ) {
                CancellingstocksWZ cwNew = iterator.next();
                CancellingstocksSQ CS = cancellingstocksSQService.getbycode(cwNew.getTKcode());
                if ("已退库".equals(CS.getTkstatus())) {
                    return "已退库";
                } else {
                    String oldcontent = GetGsonString(cwNew);
                    cwNew.setUpdatetime(timeString);
                    cancellingstocksWZService.updateById(cwNew);

                    //写入日志表
                    Logs logs = new Logs();
                    logs.setId(getUUID());
                    logs.setFlagid(cwNew.getId());
                    logs.setName("com.hchenpan.controller.CancellingstocksWZController.update");
                    logs.setParams("com.hchenpan.pojo.CancellingstocksWZ");
                    logs.setDescription("修改领料退库物资明细");
                    logs.setUpdaterid(loginUser.getId());
                    logs.setIpaddress(getRomoteIP());
                    logs.setOptcontent(GetGsonString(cwNew));
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
        }


        return ERROR;
    }

    /**
     * 功能:删除采购入库
     */
    @ResponseBody
    @GetMapping("/cancellingstockswz/delete")
    public String delete(CancellingstocksWZ cancellingstocksWZ) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            CancellingstocksWZ cwNew = cancellingstocksWZService.selectById(cancellingstocksWZ.getId().trim());
            String oldcontent = GetGsonString(cwNew);
            CancellingstocksSQ CS = cancellingstocksSQService.getbycode(cwNew.getTKcode());
            if ("已退库".equals(CS.getTkstatus())) {
                return "已退库";
            } else {

                cwNew.setUpdatetime(timeString);
                cwNew.setFlag("D");

                cancellingstocksWZService.deleteById(cwNew);

                //写入日志表
                Logs logs = new Logs();
                logs.setId(getUUID());
                logs.setFlagid(cwNew.getId());
                logs.setName("com.hchenpan.controller.CancellingstocksWZController.delete");
                logs.setParams("com.hchenpan.pojo.CancellingstocksWZ");
                logs.setDescription("删除领料退库物资明细");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(cwNew));
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