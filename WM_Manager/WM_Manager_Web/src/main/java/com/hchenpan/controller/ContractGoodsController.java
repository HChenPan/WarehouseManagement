package com.hchenpan.controller;

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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.ContractGoodsController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class ContractGoodsController extends BaseController {
    private final ContractGoodsService contractGoodsService;
    private final ContractBasicService contractBasicService;
    private final BuylistService buylistService;
    private final LogsService logsService;
    private final UserService userService;

    @Autowired
    public ContractGoodsController(ContractGoodsService contractGoodsService, ContractBasicService contractBasicService, BuylistService buylistService, LogsService logsService, UserService userService) {
        this.contractGoodsService = contractGoodsService;
        this.contractBasicService = contractBasicService;
        this.buylistService = buylistService;
        this.logsService = logsService;
        this.userService = userService;
    }

    @GetMapping("/contractgoods")
    public String contractGoods() {

        return View("/contractmanage/contractgoods");
    }

    /**
     * 功能:物资信息列表
     */
    @ResponseBody
    @PostMapping("/contractgoods/list")
    public String list(ContractGoods contractGoods) {
        Page<Map<String, Object>> page = getPage();
        Map<String, Object> params = new HashMap<>(10);

        if (StringUtil.notTrimEmpty(contractGoods.getContractbasicid())) {
            params.put("contractbasicid", contractGoods.getContractbasicid());
        }
        return jsonPage(contractGoodsService.selectContractPage(page, params));
    }


    @ResponseBody
    @PostMapping("/contractgoods/searchformx")
    public String searchformx(ContractGoods contractGoods) {
        Page<ContractGoods> page = getPage();
        EntityWrapper<ContractGoods> ew = new EntityWrapper<>();
        String[] aa = contractGoods.getContractbasicid().split(",");
        String contractbasicids = "";
        for (int i = 0; i < aa.length; i++) {
            contractbasicids = contractbasicids + "'" + aa[i] + "'" + ",";
        }
        contractbasicids = contractbasicids.substring(0, contractbasicids.length() - 1);
        ew.in("id", contractbasicids);
        ew.eq("flag", "E");
        ew.ge("syrksum", "0");
        return jsonPage(contractGoodsService.selectPage(page, ew));
    }

    /**
     * 功能:提供所有采购计划明细信息,供下拉
     */
    @ResponseBody
    @PostMapping("/contractgoods/getall")
    public String getall() {
        return ERROR;
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/contractgoods/getbyid")
    public String getbyid(ContractGoods contractGoods) {
        return GetGsonString(contractGoodsService.selectById(contractGoods.getId()));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/contractgoods/getcontractgoodslist")
    public String getcontractgoodslist() {
        return GetGsonString(contractGoodsService.selectallList());
    }

    /**
     * 功能：新增采购计划明细
     */
    @ResponseBody
    @PostMapping("/contractgoods/create")
    public String create() {
        if (checkuser()) {
            //非空时进入

            String arrayList = request.getParameter("arrayList");
            String serialsnumber = request.getParameter("serialsnumber");
            //将 arrayList 字符串转换成 json 对象
            //将 json 对象转换成 stock 集合
//            JSONArray json = JSONArray.fromObject(arrayList);
//            List<ContractGoods> list= (List<ContractGoods>)JSONArray.toCollection(json, ContractGoods.class);
            List<ContractGoods> list = new ArrayList<>();
            String auditingstatus = contractBasicService.selectOne(new EntityWrapper<ContractBasic>().eq("flag", "E").eq("serialsnumber", serialsnumber)).getAuditingstatus();

            if (!"00".equals(auditingstatus)) {
                return "审批中";
            } else {
                /*通用字段赋值*/
                User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
                String timeString = GetCurrentTime();
                for (ContractGoods goods : list) {
                    //getwznum
                    int wznum = contractGoodsService.selectCount(new EntityWrapper<ContractGoods>()
                            .eq("wzcode", goods.getWzcode())
                            .eq("contractbasicid", serialsnumber)
                            .eq("buycode", goods.getBuycode())
                            .eq("flag", "E")
                            .eq("plancode", goods.getPlancode())
                    );
                    if (wznum == 0) {
                        goods.setId(getUUID());
                        goods.setFlag("E");
                        goods.setCreatorid(loginUser.getId());
                        goods.setCreator(loginUser.getUsername());
                        goods.setCreatetime(timeString);
                        goods.setUpdaterid(loginUser.getId());
                        goods.setUpdater(loginUser.getUsername());
                        goods.setUpdatetime(timeString);
                        goods.setContractbasicid(serialsnumber);
                        goods.setCreatetime(timeString);
                        //			  获取剩余量 getsynum
                        Buylist p1 = buylistService.selectOne(new EntityWrapper<Buylist>()
                                .eq("buycode", goods.getBuycode())
                                .eq("wzcode", goods.getWzcode())
                                .eq("plancode", goods.getPlancode())
                                .eq("flag", "E")
                        );
                        String synum = p1.getSynum();
                        goods.setPlanbum(synum);
                        goods.setPlanprice("0.00");
                        goods.setSummoney("0.00");
                        goods.setSyrksum(synum);

                        p1.setSynum("0.00");
                        buylistService.updateById(p1);

                        contractGoodsService.insert(goods);
                        //写入日志表
                        Logs logs = new Logs();
                        logs.setId(getUUID());
                        logs.setFlagid(goods.getId());
                        logs.setName("com.hchenpan.controller.ContractGoodsController.create");
                        logs.setParams("com.hchenpan.pojo.ContractGoods");
                        logs.setDescription("增加合同物资信息");
                        logs.setUpdaterid(loginUser.getId());
                        logs.setIpaddress(getRomoteIP());
                        logs.setOptcontent(GetGsonString(goods));
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
                }
                return SUCCESS;
            }
        }
        return ERROR;
    }

    /**
     * 功能：新增物资信息（协议制合同）
     */
    @ResponseBody
    @PostMapping("/contractgoods/createxy")
    public String createxy() {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            String arrayList = request.getParameter("arrayList");
            String serialsnumber = request.getParameter("serialsnumber");
            //将 arrayList 字符串转换成 json 对象
            //将 json 对象转换成 stock 集合

            List<ContractGoods> list = new ArrayList<>();
            String auditingstatus = contractBasicService.selectOne(new EntityWrapper<ContractBasic>().eq("flag", "E").eq("serialsnumber", serialsnumber)).getAuditingstatus();

//			System.out.println(auditingstatus);
            if (!"00".equals(auditingstatus)) {
                return "审批中";
            } else {
                for (ContractGoods goods : list) {
                    //getwznum
                    int wznum = contractGoodsService.selectCount(new EntityWrapper<ContractGoods>()
                            .eq("wzcode", goods.getWzcode())
                            .eq("contractbasicid", serialsnumber)
                            .eq("buycode", goods.getBuycode())
                            .eq("flag", "E")
                            .eq("plancode", goods.getPlancode())
                    );
                    if (wznum == 0) {
                        goods.setId(getUUID());
                        goods.setFlag("E");
                        goods.setCreatorid(loginUser.getId());
                        goods.setCreator(loginUser.getUsername());
                        goods.setCreatetime(timeString);
                        goods.setUpdaterid(loginUser.getId());
                        goods.setUpdater(loginUser.getUsername());
                        goods.setUpdatetime(timeString);
                        goods.setContractbasicid(serialsnumber);
                        goods.setCreatetime(timeString);


                        goods.setContractbasicid(serialsnumber);
                        goods.setCreatetime(timeString);


                        goods.setPlanbum("0.00");
                        goods.setPlanprice("0.00");
                        goods.setSummoney("0.00");
                        goods.setSyrksum("0.00");

                        contractGoodsService.insert(goods);
                        //写入日志表
                        Logs logs = new Logs();
                        logs.setId(getUUID());
                        logs.setFlagid(goods.getId());
                        logs.setName("com.hchenpan.controller.ContractGoodsController.createxy");
                        logs.setParams("com.hchenpan.pojo.ContractGoods");
                        logs.setDescription("增加合同物资信息(协议制)");
                        logs.setUpdaterid(loginUser.getId());
                        logs.setIpaddress(getRomoteIP());
                        logs.setOptcontent(GetGsonString(goods));
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
                }
                return SUCCESS;
            }

        }
        return ERROR;
    }

    /**
     * 功能：更新采购计划明细
     */
    @ResponseBody
    @PostMapping("/contractgoods/update")
    public String update(ContractGoods contractGoods) {
        if (checkuser()) {
            //非空时进入
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            String arrayList = request.getParameter("arrayList");
            //将 arrayList 字符串转换成 json 对象
            //将 json 对象转换成 stock 集合

            List<ContractGoods> list = new ArrayList<>();
            String auditingstatus = contractBasicService.selectOne(new EntityWrapper<ContractBasic>().eq("flag", "E").eq("serialsnumber", list.get(0).getContractbasicid())).getAuditingstatus();

            if (!auditingstatus.equals("00")) {
                return "审批中";
            } else {
                for (int i = 0; i < list.size(); i++) {
                    String oldcontent = GetGsonString(list.get(i));
                    list.get(i).setUpdaterid(loginUser.getId());
                    list.get(i).setUpdater(loginUser.getUsername());
                    list.get(i).setUpdatetime(timeString);

//					  判断是否是协议制合同 getbyserialsnumber
                    String type = contractBasicService.selectOne(new EntityWrapper<ContractBasic>()
                            .eq("serialsnumber", list.get(i).getContractbasicid())
                            .eq("flag", "E")

                    ).getContracttype();
                    if ("40288101657e829701657eb5dde60014".equals(type)) {

//							获取现在输入之前的量 getoldplansum
                        String oldplansum = contractGoodsService.selectOne(new EntityWrapper<ContractGoods>()
                                .eq("buycode", list.get(i).getBuycode())
                                .eq("wzcode", list.get(i).getWzcode())
                                .eq("plancode", list.get(i).getPlancode())
                                .eq("contractbasicid", list.get(i).getContractbasicid())
                                .eq("flag", "E")
                        ).getPlanbum();
                        BigDecimal oldplansum1 = new BigDecimal(oldplansum);

//							  获取现在输入的量
                        String sum = list.get(i).getPlanbum().trim();
                        list.get(i).setSyrksum(sum);

                        BigDecimal sum1 = new BigDecimal(sum);


                        //获取现在输入的金额
                        String money = list.get(i).getPlanprice();
                        BigDecimal money1 = new BigDecimal(money);
//							summoney为现在物资的总价
                        BigDecimal summoney = money1.multiply(sum1).setScale(2, RoundingMode.HALF_UP);
                        list.get(i).setSummoney(summoney.toString());
//							获取之前的合同总价 getoldsummoney
                        String oldsummoney = contractBasicService.selectOne(new EntityWrapper<ContractBasic>()
                                .eq("flag", "E")
                                .eq("serialsnumber", list.get(i).getContractbasicid())
                        ).getSummoney();
                        BigDecimal oldsummoney1 = new BigDecimal(oldsummoney);
//							获取之前的物资总价 getoldsummoney
                        String oldwzsummoney = contractGoodsService.selectOne(new EntityWrapper<ContractGoods>()
                                .eq("buycode", list.get(i).getBuycode())
                                .eq("wzcode", list.get(i).getWzcode())
                                .eq("plancode", list.get(i).getPlancode())
                                .eq("contractbasicid", list.get(i).getContractbasicid())
                                .eq("flag", "E")
                        ).getSummoney();
                        BigDecimal oldwzsummoney1 = new BigDecimal(oldwzsummoney);
//							oldwzsunmoney2为以前的物资总价-现在物资总价
                        BigDecimal oldwzsunmoney2 = oldwzsummoney1.subtract(summoney);
                        //newsummoney为新的合同总价
                        BigDecimal newsummoney = oldsummoney1.subtract(oldwzsunmoney2).setScale(2, RoundingMode.HALF_UP);

                        ContractBasic p2 = contractBasicService.selectOne(new EntityWrapper<ContractBasic>()
                                .eq("serialsnumber", list.get(i).getContractbasicid())
                                .eq("flag", "E")

                        );

                        p2.setSummoney(newsummoney.toString());

                        contractBasicService.updateById(p2);
                        contractGoodsService.updateById(list.get(i));

                        //写入日志表
                        Logs logs = new Logs();
                        logs.setId(getUUID());
                        logs.setFlagid(list.get(i).getId());
                        logs.setName("com.hchenpan.controller.ContractGoodsController.update");
                        logs.setParams("com.hchenpan.pojo.ContractGoods");
                        logs.setDescription("修改物资采购数量/单价(协议制)");
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
                        logsService.insert(logs);
                    } else {
//					  获取剩余量
                        Buylist p1 = buylistService.selectOne(new EntityWrapper<Buylist>()
                                .eq("buycode", list.get(i).getBuycode())
                                .eq("wzcode", list.get(i).getWzcode())
                                .eq("plancode", list.get(i).getPlancode())
                                .eq("flag", "E")
                        );
                        String synum = p1.getSynum();
                        BigDecimal synum1 = new BigDecimal(synum);
//					获取当前输入量
                        String b = list.get(i).getPlanbum().trim();
                        BigDecimal b1 = new BigDecimal(b);
//					获取现在输入之前的量
                        String oldplansum = contractGoodsService.selectOne(new EntityWrapper<ContractGoods>()
                                .eq("buycode", list.get(i).getBuycode())
                                .eq("wzcode", list.get(i).getWzcode())
                                .eq("plancode", list.get(i).getPlancode())
                                .eq("contractbasicid", list.get(i).getContractbasicid())
                                .eq("flag", "E")
                        ).getPlanbum();
                        BigDecimal oldplansum1 = new BigDecimal(oldplansum);
                        BigDecimal newplansum = synum1.add(oldplansum1);
                        int result = b1.compareTo(newplansum);
                        if (result > 0) {
                            return "大于";
                        } else {
//					  获取现在输入的量
                            String sum = list.get(i).getPlanbum().trim();
                            list.get(i).setSyrksum(sum);

                            BigDecimal sum1 = new BigDecimal(sum);

                            BigDecimal new1 = synum1.add(oldplansum1);
//					new2为新的剩余量
                            BigDecimal new2 = new1.subtract(sum1).setScale(2, RoundingMode.HALF_UP);
                            Buylist pl = buylistService.selectOne(new EntityWrapper<Buylist>()
                                    .eq("plancode", list.get(i).getPlancode())
                                    .eq("wzcode", list.get(i).getWzcode())
                                    .eq("buycode", list.get(i).getBuycode())
                                    .eq("flag", "E")
                            );
                            pl.setSynum(new2.toString());
                            buylistService.updateById(pl);
                            //获取现在输入的金额
                            String money = list.get(i).getPlanprice();
                            BigDecimal money1 = new BigDecimal(money);
//					summoney为现在物资的总价
                            BigDecimal summoney = money1.multiply(sum1).setScale(2, RoundingMode.HALF_UP);
                            list.get(i).setSummoney(summoney.toString());
//					获取之前的合同总价 getoldsummoney
                            String oldsummoney = contractBasicService.selectOne(new EntityWrapper<ContractBasic>()
                                    .eq("flag", "E")
                                    .eq("serialsnumber", list.get(i).getContractbasicid())
                            ).getSummoney();
                            BigDecimal oldsummoney1 = new BigDecimal(oldsummoney);
//					获取之前的物资总价
                            String oldwzsummoney = contractGoodsService.selectOne(new EntityWrapper<ContractGoods>()
                                    .eq("buycode", list.get(i).getBuycode())
                                    .eq("wzcode", list.get(i).getWzcode())
                                    .eq("plancode", list.get(i).getPlancode())
                                    .eq("contractbasicid", list.get(i).getContractbasicid())
                                    .eq("flag", "E")
                            ).getSummoney();
                            BigDecimal oldwzsummoney1 = new BigDecimal(oldwzsummoney);
//					oldwzsunmoney2为以前的物资总价-现在物资总价
                            BigDecimal oldwzsunmoney2 = oldwzsummoney1.subtract(summoney);
                            //newsummoney为新的合同总价
                            BigDecimal newsummoney = oldsummoney1.subtract(oldwzsunmoney2).setScale(2, RoundingMode.HALF_UP);
                            ContractBasic p2 = contractBasicService.selectOne(new EntityWrapper<ContractBasic>()
                                    .eq("serialsnumber", list.get(i).getContractbasicid())
                                    .eq("flag", "E")

                            );

                            p2.setSummoney(newsummoney.toString());
                            contractBasicService.updateById(p2);

                            contractGoodsService.updateById(list.get(i));
                            //写入日志表
                            Logs logs = new Logs();
                            logs.setId(getUUID());
                            logs.setFlagid(list.get(i).getId());
                            logs.setName("com.hchenpan.controller.ContractGoodsController.update");
                            logs.setParams("com.hchenpan.pojo.ContractGoods");
                            logs.setDescription("修改物资采购数量/单价");
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
                            logsService.insert(logs);
                        }
                    }
                }
                return SUCCESS;
            }
        }
        return ERROR;
    }

    /**
     * 功能:删除采购计划明细
     */
    @ResponseBody
    @GetMapping("/contractgoods/delete")
    public String delete(ContractGoods contractGoods) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            String id = contractGoods.getId();
            ContractGoods m = contractGoodsService.selectById(id);
            String oldcontent = GetGsonString(m);
            String auditingstatus = contractBasicService.selectOne(new EntityWrapper<ContractBasic>().eq("flag", "E").eq("serialsnumber", m.getContractbasicid())).getAuditingstatus();
            if (!auditingstatus.equals("00")) {
                return "审批中";
            } else {
                m.setUpdaterid(loginUser.getId());
                m.setUpdater(loginUser.getUsername());
                m.setUpdatetime(timeString);
//				判断是否是协议制合同
                String type = contractBasicService.selectOne(new EntityWrapper<ContractBasic>()
                        .eq("serialsnumber", m.getContractbasicid())
                        .eq("flag", "E")

                ).getContracttype();
                if ("40288101657e829701657eb5dde60014".equals(type)) {

//						获取现在删除的物资总价
                    String wzsunmoney = m.getSummoney();
                    BigDecimal wzsunmoney1 = new BigDecimal(wzsunmoney);
//						获取以前的合同总价
                    String oldsunmoney = contractBasicService.selectOne(new EntityWrapper<ContractBasic>()
                            .eq("flag", "E")
                            .eq("serialsnumber", m.getContractbasicid())
                    ).getSummoney();
                    BigDecimal oldsunmoney1 = new BigDecimal(oldsunmoney);
//						以前的合同总价-现在删除的物资总价
                    BigDecimal newsunmoney = oldsunmoney1.subtract(wzsunmoney1).setScale(2, RoundingMode.HALF_UP);
//						获取合同信息对象
                    ContractBasic p2 = contractBasicService.selectOne(new EntityWrapper<ContractBasic>()
                            .eq("serialsnumber", m.getContractbasicid())
                            .eq("flag", "E")

                    );
                    p2.setSummoney(newsunmoney.toString());
                    contractBasicService.updateById(p2);
                    m.setFlag("D");
                    m.setUpdatetime(timeString);
                    m.setSummoney("0");
                    contractGoodsService.updateById(m);

                } else {

                    //			获取现在的数量
                    String sum = m.getPlanbum();
                    BigDecimal sum1 = new BigDecimal(sum);
//			获取剩余量
                    Buylist p1 = buylistService.selectOne(new EntityWrapper<Buylist>()
                            .eq("buycode", m.getBuycode())
                            .eq("wzcode", m.getWzcode())
                            .eq("plancode", m.getPlancode())
                            .eq("flag", "E")
                    );
                    String synum = p1.getSynum();
                    BigDecimal synum1 = new BigDecimal(synum);
                    BigDecimal newsynum = sum1.add(synum1).setScale(2, RoundingMode.HALF_UP);
//			获取物资表对象
                    Buylist pl = buylistService.selectOne(new EntityWrapper<Buylist>()
                            .eq("plancode", m.getPlancode())
                            .eq("wzcode", m.getWzcode())
                            .eq("buycode", m.getBuycode())
                            .eq("flag", "E")
                    );

                    pl.setSynum(newsynum.toString());
//			获取现在删除的物资总价
                    String wzsunmoney = m.getSummoney();
                    BigDecimal wzsunmoney1 = new BigDecimal(wzsunmoney);
//			获取以前的合同总价
                    String oldsunmoney = contractBasicService.selectOne(new EntityWrapper<ContractBasic>()
                            .eq("flag", "E")
                            .eq("serialsnumber", m.getContractbasicid())
                    ).getSummoney();
                    BigDecimal oldsunmoney1 = new BigDecimal(oldsunmoney);
//			以前的合同总价-现在删除的物资总价
                    BigDecimal newsunmoney = oldsunmoney1.subtract(wzsunmoney1).setScale(2, RoundingMode.HALF_UP);
//			获取合同信息对象
                    ContractBasic p2 = contractBasicService.selectOne(new EntityWrapper<ContractBasic>()
                            .eq("serialsnumber", m.getContractbasicid())
                            .eq("flag", "E")

                    );
                    p2.setSummoney(newsunmoney.toString());
                    contractBasicService.updateById(p2);
                    m.setFlag("D");
                    m.setUpdatetime(timeString);
                    m.setSummoney("0");
                    contractGoodsService.updateById(m);
                    buylistService.updateById(pl);
                }


                //写入日志表
                Logs logs = new Logs();
                logs.setId(getUUID());
                logs.setFlagid(m.getId());
                logs.setName("com.hchenpan.controller.ContractGoodsController.delete");
                logs.setParams("com.hchenpan.pojo.ContractGoods");
                logs.setDescription("删除合同物资");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(m));
                /* 修改，需要保存修改前后的数据 */
                logs.setOldcontent(oldcontent);
                logs.setCreatorid(loginUser.getId());
                logs.setCreator(loginUser.getUsername());
                logs.setCreatetime(timeString);
                logs.setUpdater(loginUser.getUsername());
                logs.setRealname(loginUser.getRealname());
                logs.setUpdatetime(timeString);
                logsService.insert(logs);
                return "success";
            }
        }
        return ERROR;
    }
}