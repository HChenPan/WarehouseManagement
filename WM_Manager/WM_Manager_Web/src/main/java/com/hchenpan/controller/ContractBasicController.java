package com.hchenpan.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseController;
import com.hchenpan.model.CommboxList;
import com.hchenpan.pojo.*;
import com.hchenpan.service.*;
import com.hchenpan.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.controller.ContractBasicController
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:48 上午
 **/

@Controller
public class ContractBasicController extends BaseController {
    private final ContractBasicService contractBasicService;
    private final ContractGoodsService contractGoodsService;
    private final ContractTermsService contractTermsService;
    private final DictionaryschildService dictionaryschildService;
    private final ApprovalRecordService approvalRecordService;
    private final WorkFlowService workFlowService;
    private final SptypespLevelService sptypespLevelService;
    private final LogsService logsService;

    @Autowired
    public ContractBasicController(ContractBasicService contractBasicService, ContractGoodsService contractGoodsService, ContractTermsService contractTermsService, DictionaryschildService dictionaryschildService, ApprovalRecordService approvalRecordService, WorkFlowService workFlowService, SptypespLevelService sptypespLevelService, LogsService logsService, UserService userService) {
        this.contractBasicService = contractBasicService;
        this.contractGoodsService = contractGoodsService;
        this.contractTermsService = contractTermsService;
        this.dictionaryschildService = dictionaryschildService;
        this.approvalRecordService = approvalRecordService;
        this.workFlowService = workFlowService;
        this.sptypespLevelService = sptypespLevelService;
        this.logsService = logsService;
    }

    @GetMapping("/contractbasic")
    public String contractBasic() {

        return View("/contractmanage/contractbasic");
    }

    @GetMapping("/contractapproval")
    public String contractapproval() {

        return View("/contractmanage/contractapproval");
    }

    /**
     * 功能:采购计划大类维护-查询模块提供查询的分页数据
     */
    @ResponseBody
    @PostMapping("/contractbasic/list")
    public String list(ContractBasic contractBasic) {
        Page<Map<String, Object>> page = getPage();
        Map<String, Object> params = new HashMap<>(10);

        if (StringUtil.notTrimEmpty(contractBasic.getSerialsnumber())) {
            params.put("serialsnumber", contractBasic.getSerialsnumber());
        }

        if (StringUtil.notTrimEmpty(contractBasic.getContracttype())) {
            params.put("contracttype", contractBasic.getContracttype());
        }
        if (StringUtil.notTrimEmpty(contractBasic.getDatetime1())) {
            params.put("datetime1", contractBasic.getDatetime1());
        }
        /*通用字段赋值*/
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        params.put("creatuserid", loginUser.getId());
        if (StringUtil.notTrimEmpty(contractBasic.getDatetime2())) {
            params.put("datetime2", contractBasic.getDatetime2());
        }
        return jsonPage(contractBasicService.selectContractPage(page, params));
    }

    /**
     * 功能:提供所有采购计划大类信息,供下拉
     */
    @ResponseBody
    @PostMapping("/contractbasic/getall")
    public String getall() {
        return ERROR;
    }

    /**
     * 功能：取单条数据编辑
     */
    @ResponseBody
    @GetMapping("/contractbasic/getbyid")
    public String getbyid(ContractBasic contractBasic) {
        return GetGsonString(contractBasicService.selectById(contractBasic.getId()));
    }

    /**
     * 功能：新增采购计划大类
     */
    @ResponseBody
    @PostMapping("/contractbasic/create")
    public String create(ContractBasic contractBasic) {
        if (checkuser()) {
            /*通用字段赋值*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            contractBasic.setId(getUUID());
            contractBasic.setCreatorid(loginUser.getId());
            contractBasic.setCreator(loginUser.getUsername());
            contractBasic.setCreatetime(timeString);
            contractBasic.setUpdaterid(loginUser.getId());
            contractBasic.setUpdater(loginUser.getUsername());
            contractBasic.setUpdatetime(timeString);
            contractBasic.setFlag("E");


            contractBasic.setContractstatus("进行中");
            contractBasic.setAuditingstatus("草稿");
            contractBasic.setSpcode("00");
            contractBasic.setSummoney("0.00");
            String htInitials = contractBasic.getContracttypecode();
            String numberid = contractBasicService.createnumberid(htInitials);
            contractBasic.setSerialsnumber(htInitials + numberid);

            List<Dictionaryschild> list = dictionaryschildService.getdchildlistbydecode("BZ");
            contractBasic.setZjname(list.get(0).getName().trim());
            contractBasic.setZjcode(list.get(0).getCode().trim());

            contractBasicService.insert(contractBasic);

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(contractBasic.getId());
            logs.setName("com.hchenpan.controller.ContractBasicController.create");
            logs.setParams("com.hchenpan.pojo.ContractBasic类");
            logs.setDescription("录入合同基本信息");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(contractBasic));
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
     * 功能：更新采购计划大类
     */
    @ResponseBody
    @PostMapping("/contractbasic/update")
    public String update(ContractBasic contractBasic) {
        if (checkuser()) {
            ContractBasic old = contractBasicService.selectById(contractBasic.getId());
            String oldcontent = GetGsonString(old);
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            contractBasic.setUpdaterid(loginUser.getId());
            contractBasic.setUpdater(loginUser.getUsername());
            contractBasic.setUpdatetime(timeString);
            contractBasicService.updateById(contractBasic);
            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(contractBasic.getId());
            logs.setName("com.hchenpan.controller.ContractBasicController.update");
            logs.setParams("com.hchenpan.pojo.ContractBasic类");
            logs.setDescription("修改合同基本信息");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(contractBasic));
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
     * 功能:删除采购计划大类
     */
    @ResponseBody
    @GetMapping("/contractbasic/delete")
    public String delete(ContractBasic contractBasic) {
        if (checkuser()) {
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            ContractBasic old = contractBasicService.selectById(contractBasic.getId());
            String oldcontent = GetGsonString(old);

            old.setUpdaterid(loginUser.getId());
            old.setUpdater(loginUser.getUsername());
            old.setUpdatetime(timeString);
            int wzsum = contractGoodsService.selectCount(new EntityWrapper<ContractGoods>().eq("", old.getSerialsnumber()).eq("flag", "E"));


            if (wzsum > 0) {
                return "有物资";
            } else {

                old.setFlag("D");
                old.setContractstatus("已删除");
                contractBasicService.deleteById(old);

                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(old.getId());
                logs.setName("com.hchenpan.controller.ContractBasicController.update");
                logs.setParams("com.hchenpan.pojo.ContractBasic类");
                logs.setDescription("删除合同基本信息");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(old));
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
     * 功能:固定资产上报
     */
    @ResponseBody
    @GetMapping("/contractbasic/send")
    public String send(ContractBasic contractBasic) {
        if (checkuser()) {
            /*通用字段修改*/
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            ContractBasic old = contractBasicService.selectById(contractBasic.getId());
            String oldcontent = GetGsonString(old);
            int termsnum = contractTermsService.selectCount(new EntityWrapper<ContractTerms>().eq("flag", "E").eq("contractBasicid", old.getSerialsnumber()));
            if (termsnum == 0) {
                return "未添加合同条款";
            } else {
                old.setAuditingstatus("一级审批");
                old.setSpcode("11");
                old.setUpdaterid(loginUser.getId());
                old.setUpdater(loginUser.getUsername());
                old.setUpdatetime(timeString);

                contractBasicService.updateById(old);


                //写入日志表
                Logs logs = new Logs();
                logs.setFlagid(old.getId());
                logs.setName("com.hchenpan.controller.ContractBasicController.send");
                logs.setParams("com.hchenpan.pojo.ContractBasic类");
                logs.setDescription("上报合同审批");
                logs.setUpdaterid(loginUser.getId());
                logs.setIpaddress(getRomoteIP());
                logs.setOptcontent(GetGsonString(old));
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
     * 功能:编写合同号
     */
    @ResponseBody
    @GetMapping("/contractbasic/createcontractid")
    public String createcontractid(ContractBasic contractBasic) {
        if (checkuser()) {
            User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            String timeString = GetCurrentTime();
            ContractBasic old = contractBasicService.selectById(contractBasic.getId());
            String oldcontent = GetGsonString(old);
            old.setUpdaterid(loginUser.getId());
            old.setUpdater(loginUser.getUsername());
            old.setUpdatetime(timeString);

            old.setContractstatus("执行");
            old.setContractid(contractBasic.getContractid());

            contractBasicService.updateById(old);

            //写入日志表
            Logs logs = new Logs();
            logs.setFlagid(old.getId());
            logs.setName("com.hchenpan.controller.ContractBasicController.send");
            logs.setParams("com.hchenpan.pojo.ContractBasic类");
            logs.setDescription("编写合同号");
            logs.setUpdaterid(loginUser.getId());
            logs.setIpaddress(getRomoteIP());
            logs.setOptcontent(GetGsonString(old));
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
     * 功能:编写合同号
     */
    @ResponseBody
    @PostMapping("/contractbasic/searchsplist")
    public String searchsplist() {
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        //获取计划下的所有审批类型
        String sptypecodes = dictionaryschildService.getsptypecodesbynote("CT");
        //获取当前登录用户的审批节点
        List<CommboxList> cList = sptypespLevelService.getusersplevelnode(sptypecodes, loginUser.getId());
        //获取固定资产审批列表
        Page<ContractBasic> page = getPage();
        EntityWrapper<ContractBasic> ew = new EntityWrapper<>();
        StringBuilder sql = new StringBuilder();

        sql.append(" AND (  ");
        for (int i = 1; i < cList.size(); i++) {
            CommboxList commboxList = cList.get(i);
            sql.append("( CONTRACTAUDITINGTYPCODE ='").append(commboxList.getId()).append("'and spcode = '").append(commboxList.getText()).append("') OR ");
        }
        sql.append(" flag='E' )");
        System.out.println(sql);
        ew.where("flag = {0}" + sql.toString(), "'E'");

        return GetGsonString(contractBasicService.selectPage(page, ew));

    }

    /**
     * 功能：审批操作：同意 S 退回 F
     */
    @ResponseBody
    @GetMapping("/contractbasic/approve")
    public String approve(ContractBasic contractBasic) {
        //插入审批记录
        ApprovalRecord a = new ApprovalRecord();
        /*通用字段赋值*/
        User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        String timeString = GetCurrentTime();
        a.setId(getUUID());
        a.setCreatorid(loginUser.getId());
        a.setCreator(loginUser.getUsername());
        a.setCreatetime(timeString);
        a.setUpdaterid(loginUser.getId());
        a.setUpdater(loginUser.getUsername());
        a.setUpdatetime(timeString);

        a.setSpid(contractBasic.getId());
        a.setSptypecode(contractBasic.getContractauditingtypcode());
        a.setSpnodecode(contractBasic.getSpcode());
        a.setSpadvice(contractBasic.getBackreason());
        a.setSpresult(contractBasic.getSpresult());
        a.setSpuserid(contractBasic.getBackuserid());
        a.setSpuser(contractBasic.getSpuser());
        a.setSptime(timeString);
        approvalRecordService.insert(a);

        //更改计划表
        String spnodecode = contractBasic.getSpcode();
        String money = contractBasic.getSummoney();
        String spresult = contractBasic.getSpresult();
        String spstatus = "";
        //获取下一级审批节点
        String nextspnode = workFlowService.getnextspnode(contractBasic.getContractauditingtypcode(), spnodecode, money, spresult);
        System.out.println(nextspnode);
        //获取审批节点名称
        if ("同意".equals(spresult)) {
            spstatus = dictionaryschildService.getSpnodenamebycode("SPJB", nextspnode);
        } else {
            spstatus = dictionaryschildService.getSpnodenamebycode("SPJB", spnodecode) + "退回";
        }
        //判断是否更新计划plan表的审批节点、审批状态
        //查询该级审批对应的审批人员数
        int spusernum = sptypespLevelService.getspusernum(contractBasic.getContractauditingtypcode(), spnodecode);
        //查询审批记录表中该级别同意的审批记录数
        int tyspnum = sptypespLevelService.gettyspnum(contractBasic.getContractauditingtypcode(), spnodecode, contractBasic.getId());
        contractBasic.setSpcode(nextspnode);
        contractBasic.setAuditingstatus(spstatus);
        if ("退回".equals(spresult)) {
            contractBasicService.updateById(contractBasic);
        } else {
            if (spusernum == tyspnum) {
                contractBasicService.updateById(contractBasic);
            }
        }
        //审批结束后更改plan表中的审批结束时间
        if ("99".equals(nextspnode)) {
            contractBasic.setBacktime(timeString);
            contractBasicService.updateById(contractBasic);
        }
        return SUCCESS;
    }
    @ResponseBody
    @GetMapping("/contractbasic/getcontractbasiclist")
    public String getcontractbasiclist() {
        return GetGsonString(contractBasicService.selectallList());
    }

}