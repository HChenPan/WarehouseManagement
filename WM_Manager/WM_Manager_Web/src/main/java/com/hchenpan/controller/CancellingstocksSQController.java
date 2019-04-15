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
import java.util.Iterator;
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.CancellingstocksSQController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class CancellingstocksSQController extends BaseController {
    private final CancellingstocksSQService cancellingstocksSQService;
    private final CancellingstocksWZService cancellingstocksWZService;
    private final StockService stockService;
    private final CallslipGoodsService callslipGoodsService;
    private final LogsService logsService;

    @Autowired
    public CancellingstocksSQController(CancellingstocksSQService cancellingstocksSQService, CancellingstocksWZService cancellingstocksWZService, StockService stockService, CallslipService callslipService, CallslipGoodsService callslipGoodsService, LogsService logsService, UserService userService) {
        this.cancellingstocksSQService = cancellingstocksSQService;
        this.cancellingstocksWZService = cancellingstocksWZService;
        this.stockService = stockService;
        this.callslipGoodsService = callslipGoodsService;
        this.logsService = logsService;
    }

    @GetMapping("/cancellingstocks")
    public String cancellingstocks() {
        return View("/reserve/cancellingstocks");
    }

    /**
     * 功能:采购入库维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/cancellingstockssq/search")
    public String search(CancellingstocksSQ cancellingstocksSQ) {
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        Page<CancellingstocksSQ> page = getPage();
        EntityWrapper<CancellingstocksSQ> ew = new EntityWrapper<>();
        if (StringUtil.notTrimEmpty(cancellingstocksSQ.getTKcode())) {
            ew.like("TKcode", cancellingstocksSQ.getTKcode(), SqlLike.DEFAULT);
        }
        if (StringUtil.notTrimEmpty(cancellingstocksSQ.getDatetime1())) {
            ew.gt("createtime", cancellingstocksSQ.getDatetime1());
        }
        if (StringUtil.notTrimEmpty(cancellingstocksSQ.getDatetime2())) {
            ew.lt("createtime", cancellingstocksSQ.getDatetime2());
        }
        ew.eq("flag", "E").eq("userid", loginUser.getId());
        return jsonPage(cancellingstocksSQService.selectPage(page, ew));
    }

    /**
     * 功能:提供所有采购入库信息,供下拉
     */
    @ResponseBody
    @PostMapping("/cancellingstockssq/getallbyid")
    public String getallbyid(CancellingstocksSQ cancellingstocksSQ) {
        return GetGsonString(cancellingstocksSQService.selectById(cancellingstocksSQ.getId()));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/cancellingstockssq/getbyid")
    public String getbyid(CancellingstocksSQ cancellingstocksSQ) {
        return ERROR;
    }

    /**
     * 功能：新增采购入库
     */
    @ResponseBody
    @PostMapping("/cancellingstockssq/create")
    public String create(CancellingstocksSQ cancellingstocksSQ) {
        if (checkuser()) {
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            String tKcode = cancellingstocksSQService.createTKcode();
            cancellingstocksSQ.setTKcode(tKcode);
            cancellingstocksSQ.setTKtype("领料退库");
            cancellingstocksSQ.setNote(cancellingstocksSQ.getNote());
            cancellingstocksSQ.setUserid(loginUser.getId());
            cancellingstocksSQ.setRealname(loginUser.getRealname());
            cancellingstocksSQ.setCreatetime(timeString);
            cancellingstocksSQ.setTkstatus("草稿");
            cancellingstocksSQ.setUpdatetime(timeString);
            cancellingstocksSQ.setId(getUUID());
            cancellingstocksSQ.setCreatorid(loginUser.getId());
            cancellingstocksSQ.setCreator(loginUser.getUsername());
            cancellingstocksSQ.setCreatetime(timeString);
            cancellingstocksSQ.setUpdaterid(loginUser.getId());
            cancellingstocksSQ.setUpdater(loginUser.getUsername());
            cancellingstocksSQ.setUpdatetime(timeString);
            cancellingstocksSQ.setFlag("E");


            try {
                cancellingstocksSQService.insert(cancellingstocksSQ);

                // 写入日志表
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(cancellingstocksSQ.getId());
                logs.setName("com.hchenpan.controller.CancellingstocksSQController.create");
                logs.setParams("com.hchenpan.pojo.CancellingstocksSQ类");
                logs.setDescription("新增领料退库单 ");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(cancellingstocksSQ));
                logs.setCreatorid(loginUser.getId());
                logs.setCreator(loginUser.getUsername());
                logs.setCreatetime(timeString);
                logs.setRealname(loginUser.getRealname());
                logs.setUpdater(loginUser.getUsername());
                logs.setUpdatetime(timeString);
                logs.setId(getUUID());
                logsService.insert(logs);


                return SUCCESS;
            } catch (Exception e) {
                return "数据错误，请刷新页面重试！";
            }

        }
        return ERROR;
    }

    /**
     * 功能：更新采购入库
     */
    @ResponseBody
    @PostMapping("/cancellingstockssq/update")
    public String update(CancellingstocksSQ cancellingstocksSQ) {
        if (checkuser()) {
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            CancellingstocksSQ csNew = cancellingstocksSQService.selectById(cancellingstocksSQ.getId());
            String oldcontent = GetGsonString(csNew);
            cancellingstocksSQ.setUpdaterid(loginUser.getId());
            cancellingstocksSQ.setUpdater(loginUser.getUsername());
            cancellingstocksSQ.setUpdatetime(timeString);

            cancellingstocksSQService.updateById(cancellingstocksSQ);

            // 写入日志表
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(cancellingstocksSQ.getId());
            logs.setName("com.hchenpan.controller.CancellingstocksSQController.update");
            logs.setParams("com.hchenpan.pojo.CancellingstocksSQ类");
            logs.setDescription("修改领料退库单 ");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(cancellingstocksSQ));
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
        }
        return ERROR;
    }

    /**
     * 功能:删除采购入库
     */
    @ResponseBody
    @GetMapping("/cancellingstockssq/delete")
    public String delete(CancellingstocksSQ cancellingstocksSQ) {
        if (checkuser()) {
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            CancellingstocksSQ csNew = cancellingstocksSQService.selectById(cancellingstocksSQ.getId());
            String oldcontent = GetGsonString(csNew);

            cancellingstocksSQService.deleteById(csNew);
            cancellingstocksWZService.delete(new EntityWrapper<CancellingstocksWZ>().eq("TKid", csNew.getId().trim()));

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(cancellingstocksSQ.getId());
            logs.setName("com.hchenpan.controller.CancellingstocksSQController.delete");
            logs.setParams("com.hchenpan.pojo.CancellingstocksSQ类");
            logs.setDescription("删除领料退库单 ");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(cancellingstocksSQ));
            logs.setCreatorid(loginUser.getId());
            logs.setCreator(loginUser.getUsername());
            logs.setCreatetime(timeString);
            logs.setOldcontent(oldcontent);
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
     * 功能:删除采购入库
     */
    @ResponseBody
    @GetMapping("/cancellingstockssq/send")
    public String send(CancellingstocksSQ cancellingstocksSQ) {
        if (checkuser()) {
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            List<CancellingstocksWZ> list = cancellingstocksWZService.selectList(new EntityWrapper<CancellingstocksWZ>()
                    .eq("TKid", cancellingstocksSQ.getId())
                    .eq("flag", "E")
            );

            for (Iterator<CancellingstocksWZ> iterator = list.iterator(); iterator.hasNext(); ) {
                CancellingstocksWZ CW = iterator.next();
                if (CW.getTkprice().equals("0") || CW.getTkprice().equals("0.00") || CW.getTksum().equals("0")
                        || CW.getTksum().equals("0.00")) {
                    return "为空";

                }
                BigDecimal tksum = new BigDecimal(CW.getTksum()).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal sum = new BigDecimal(CW.getSysum()).setScale(2, BigDecimal.ROUND_HALF_UP);
                String num = sum.subtract(tksum).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
                if (Integer.parseInt(num) < 0) {
                    return "领料单号为 " + CW.getCallslipcode() + "物资编码为 " + CW.getWzcode() + " 的退库数量大于剩余可退库数量，请修改";

                }
            }
            try {
                for (Iterator<CancellingstocksWZ> iterator = list.iterator(); iterator.hasNext(); ) {
                    CancellingstocksWZ CW = iterator.next();

                    CallslipGoods callslipGoods = callslipGoodsService.selectOne(new EntityWrapper<CallslipGoods>()
                            .eq("callslipcode", CW.getCallslipcode())
                            .eq("wzcode", CW.getWzcode())
                            .eq("stockcode", CW.getStockcode())
                            .eq("flag", "E")
                    );

                    BigDecimal sysum = new BigDecimal(callslipGoods.getSysum()).setScale(2,
                            BigDecimal.ROUND_HALF_UP);
                    BigDecimal Tksum = new BigDecimal(CW.getTksum()).setScale(2, BigDecimal.ROUND_HALF_UP);

                    callslipGoods.setSysum(sysum.subtract(Tksum).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    callslipGoodsService.updateById(callslipGoods);
                    stockService.TKstocks(CW);
                }
                CancellingstocksSQ csNew = cancellingstocksSQService.selectById(cancellingstocksSQ.getId());
                csNew.setTkstatus("已退库");
                csNew.setUpdatetime(timeString);

                cancellingstocksSQService.updateById(csNew);
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(cancellingstocksSQ.getId());
                logs.setName("com.hchenpan.controller.CancellingstocksSQController.send");
                logs.setParams("com.hchenpan.pojo.CancellingstocksSQ类");
                logs.setDescription("操作退库");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(cancellingstocksSQ));
                logs.setCreatorid(loginUser.getId());
                logs.setCreator(loginUser.getUsername());
                logs.setCreatetime(timeString);
                /* logs.setOldcontent(oldcontent); */
                logs.setRealname(loginUser.getRealname());
                logs.setUpdater(loginUser.getUsername());
                logs.setUpdatetime(timeString);
                logs.setId(getUUID());
                logsService.insert(logs);
                return SUCCESS;

            } catch (Exception e) {
                return "退库失败";
            }
        }
        return ERROR;
    }

}