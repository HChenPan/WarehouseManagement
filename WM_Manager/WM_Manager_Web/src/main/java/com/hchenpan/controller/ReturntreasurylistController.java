package com.hchenpan.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hchenpan.common.BaseController;
import com.hchenpan.pojo.Dictionaryschild;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.Returntreasurylist;
import com.hchenpan.pojo.User;
import com.hchenpan.service.*;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.ReturntreasurylistController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class ReturntreasurylistController extends BaseController {
    private final ReturntreasurylistService returntreasurylistService;
    private final DictionaryschildService dictionaryschildService;
    private final SparepartCodeService sparepartCodeService;
    private final ReturntreasuryService returntreasuryService;
    private final LogsService logsService;
    private final UserService userService;

    @Autowired
    public ReturntreasurylistController(ReturntreasurylistService returntreasurylistService, DictionaryschildService dictionaryschildService, SparepartCodeService sparepartCodeService, ReturntreasuryService returntreasuryService, LogsService logsService, UserService userService) {
        this.returntreasurylistService = returntreasurylistService;
        this.dictionaryschildService = dictionaryschildService;
        this.sparepartCodeService = sparepartCodeService;
        this.returntreasuryService = returntreasuryService;
        this.logsService = logsService;
        this.userService = userService;
    }


    /**
     * 功能:采购入库维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/returntreasurylist/search")
    public String search(Returntreasurylist returntreasurylist) {
        Page<Returntreasurylist> page = getPage();
        EntityWrapper<Returntreasurylist> ew = new EntityWrapper<>();

        if (StringUtils.isNotEmpty(returntreasurylist.getTkcode())) {
            ew.like("tkcode", returntreasurylist.getTkcode(), SqlLike.DEFAULT);
        }
        ew.eq("flag", "E");
        return jsonPage(returntreasurylistService.selectPage(page, ew));
    }

    /**
     * 功能:取单条数据编辑
     */
    @ResponseBody
    @PostMapping("/returntreasurylist/getallbyid")
    public String getallbyid(Returntreasurylist returntreasurylist) {
        return GetGsonString(returntreasurylistService.selectById(returntreasurylist.getId()));
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/returntreasurylist/searchformx")
    public String searchformx(Returntreasurylist returntreasurylist) {
        Page<Map<String, Object>> page = getPage();
        Map<String, Object> params = new HashMap<>(10);
        String[] aa = returntreasurylist.getNotecode().split(",");
        String notecodes = "";
        for (int i = 0; i < aa.length; i++) {
            notecodes = notecodes + "'" + aa[i] + "'" + ",";
        }
        notecodes = notecodes.substring(0, notecodes.length() - 1);
        params.put("notecodes", notecodes);
        return jsonPage(returntreasurylistService.selectMXPage(page, params));
    }

    /**
     * 功能：新增采购入库
     */
    @ResponseBody
    @PostMapping("/returntreasurylist/create")
    public String create(Returntreasurylist returntreasurylist) {
        if (checkuser()) {
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            String tkcode = request.getParameter("tkcode").trim();
            String arrayList = request.getParameter("arrayList");
                //替换字符串中的'/'
            String str = arrayList.replaceAll("\\\\", "\"");
            //转为json对象
            JSONArray json = JSONArray.fromObject(str);
            List<Returntreasurylist> list = (List<Returntreasurylist>) JSONArray.toCollection(json, Returntreasurylist.class);

            

            String tkstatus = returntreasuryService.gettkstatus(tkcode);
            if ("已退货".equals(tkstatus)) {
                return "已退货";
            } else {
                int k = 0;
                String flag = "";
                for (int i = 0; i < list.size(); i++) {

                    int wznum = returntreasurylistService.selectCount(new EntityWrapper<Returntreasurylist>()
                            .eq("wzcode", list.get(i).getWzcode())
                            .eq("contractbasicid", list.get(i).getContractbasicid())
                            .eq("buycode", list.get(i).getBuycode())
                            .eq("plancode", list.get(i).getPlancode())
                            .eq("flag", "E")
                            .eq("rkcode", list.get(i).getRkcode())
                            .eq("tkcode", list.get(i).getTkcode())
                    );
                    if (wznum > 0) {
                        k = k + 1;
                        flag += "入库流水号为：" + list.get(i).getRkcode() + "合同号为：" + list.get(i).getContractbasicid() + " 采购计划号为：" + list.get(i).getBuycode() + " 需求计划号为：" + list.get(i).getPlancode() + " 物资编码为：" + list.get(i).getWzcode() + ",";
                        continue;
                    }
                }
                if (k == 0) {
                    for (int i = 0; i < list.size(); i++) {

                        list.get(i).setUpdatetime(GetCurrentTime());
                        list.get(i).setFlag("E");
                        list.get(i).setSjthsl("0.00");
                        list.get(i).setSjthmoney("0.00");
                        List<Dictionaryschild> list1 = dictionaryschildService.getdchildlistbydecode("BZ");
                        list.get(i).setZjname(list1.get(0).getName().trim());
                        list.get(i).setZjcode(list1.get(0).getCode().trim());
                        list.get(i).setTkcode(tkcode);
                        list.get(i).setId(getUUID());
                        list.get(i).setCreatorid(loginUser.getId());
                        list.get(i).setCreator(loginUser.getUsername());
                        list.get(i).setCreatetime(timeString);
                        list.get(i).setUpdaterid(loginUser.getId());
                        list.get(i).setUpdater(loginUser.getUsername());
                        list.get(i).setUpdatetime(timeString);
                        list.get(i).setFlag("E");

                        returntreasurylistService.insert(list.get(i));
                        //写入日志表
                        Logs logs = new Logs();
                        logs.setFlagid(list.get(i).getId());
                        logs.setName("com.hchenpan.controller.ReturntreasurylistController.create");
                        logs.setParams("com.hchenpan.pojo.Returntreasurylist类");
                        logs.setDescription("新增采购入库退货物资明细申请");
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
                    if (!flag.equals("success") && !flag.equals("error")) {
                        flag = flag.substring(0, flag.length() - 1);
                        flag += "该物资已存在退货单中，如有需要请修改数量";
                    }
                    return flag;
                }

            }
        }
        return ERROR;
    }

    /**
     * 功能：更新采购入库
     */
    @ResponseBody
    @PostMapping("/returntreasurylist/update")
    public String update(Returntreasurylist returntreasurylist) {
        if (checkuser()) {
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();


            String arrayList = request.getParameter("arrayList");
              //替换字符串中的'/'
            String str = arrayList.replaceAll("\\\\", "\"");
            //转为json对象
            JSONArray json = JSONArray.fromObject(str);
            List<Returntreasurylist> list = (List<Returntreasurylist>) JSONArray.toCollection(json, Returntreasurylist.class);
            
            String tkstatus = returntreasuryService.gettkstatus(list.get(0).getTkcode().trim());
            if ("已退货".equals(tkstatus)) {
                return "已退货";
            } else {
                int k = 0;
                String flag = "";
                for (int i = 0; i < list.size(); i++) {
                    String a = list.get(i).getSjthsl().trim();
                    BigDecimal a1 = new BigDecimal(a);

                    String b = list.get(i).getSjnum().trim();
                    BigDecimal b1 = new BigDecimal(b);
                    int result = a1.compareTo(b1);
                    if (result > 0) {
                        k = k + 1;
                        flag += "入库流水号为：" + list.get(i).getRkcode() + "合同号为：" + list.get(i).getContractbasicid() + " 采购计划号为：" + list.get(i).getBuycode() + " 需求计划号为：" + list.get(i).getPlancode() + " 物资编码为：" + list.get(i).getWzcode() + ",";
                        continue;
                    }
                }
                if (k == 0) {
                    for (int i = 0; i < list.size(); i++) {
                        Returntreasurylist d = returntreasurylistService.selectById(list.get(i).getId());
                        String oldcontent = GetGsonString(d);
                        String sjthsl1 = list.get(i).getSjthsl().trim();
                        BigDecimal sjthsl2 = new BigDecimal(sjthsl1);
                        String price = list.get(i).getPlanprice().trim();
                        BigDecimal price1 = new BigDecimal(price);
                        BigDecimal total = price1.multiply(sjthsl2).setScale(2, BigDecimal.ROUND_HALF_UP);
                        list.get(i).setSjthmoney(total.toString());
                        list.get(i).setUpdatetime(timeString);

                        returntreasurylistService.updateById(list.get(i));
                        //写入日志表
                        Logs logs = new Logs();
                        logs.setFlagid(list.get(i).getId());
                        logs.setName("com.hchenpan.controller.ReturntreasurylistController.update");
                        logs.setParams("com.hchenpan.pojo.Returntreasurylist类");
                        logs.setDescription("修改采购入库退货申请物资明细大类");
                        logs.setUpdaterid(loginUser.getId());
                        logs.setIpaddress(getRomoteIP());
                        logs.setOptcontent(GetGsonString(list.get(i)));
                        logs.setCreatorid(loginUser.getId());
                        logs.setCreator(loginUser.getUsername());
                        logs.setCreatetime(timeString);
                        logs.setOldcontent(oldcontent);
                        logs.setRealname(loginUser.getRealname());
                        logs.setUpdater(loginUser.getUsername());
                        logs.setUpdatetime(timeString);
                        logs.setId(getUUID());
                        logsService.insert(logs);


                    }
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

    /**
     * 功能:删除采购入库
     */
    @ResponseBody
    @GetMapping("/returntreasurylist/delete")
    public String delete(Returntreasurylist returntreasurylist) {
        if (checkuser()) {
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();

            Returntreasurylist d = returntreasurylistService.selectById(returntreasurylist.getId());
            String tkstatus = returntreasuryService.gettkstatus(d.getTkcode().trim());
            if ("未退货".equals(tkstatus)) {
                d.setFlag("D");

                d.setUpdatetime(timeString);
                returntreasurylistService.updateById(d);

                // 写入日志表
                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(d.getId());
                logs.setName("com.hchenpan.controller.ReturntreasurylistController.delete");
                logs.setParams("com.hchenpan.pojo.Returntreasurylist类");
                logs.setDescription("删除采购入库退货申请物资明细大类");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(d));
                logs.setCreatorid(loginUser.getId());
                logs.setCreator(loginUser.getUsername());
                logs.setCreatetime(timeString);
                logs.setRealname(loginUser.getRealname());
                logs.setUpdater(loginUser.getUsername());
                logs.setUpdatetime(timeString);
                logs.setId(getUUID());
                logsService.insert(logs);


                return SUCCESS;

            } else {
                return "已退货";
            }
        }
        return ERROR;
    }
}