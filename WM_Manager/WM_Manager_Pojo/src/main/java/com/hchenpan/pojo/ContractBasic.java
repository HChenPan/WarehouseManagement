package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_CONTRACTBASIC")
public class ContractBasic extends BasePojo {

    /**
     * 合同流水号
     */
    private String serialsnumber;

    /***合同编号*/
    private String contractid;

    /**
     * 合同类型--存id
     */
    private String contracttype;

    /**
     * 合同类型code（数据库不建字段）
     */
    @TableField(exist = false)
    private String contracttypecode;

    /**
     * 签订方式
     */
    private String contractmethod;

    /**
     * 付款方式
     */
    private String paymentmethod;

    /**
     * 签订地点
     */
    private String contractarea;

    /**
     * 有效起始日期
     */
    private String startdate;

    /**
     * 有效截止日期
     */
    private String enddate;

    /**
     * 比价方1
     */
    private String bjf1;

    /**
     * 比价方2
     */
    private String bjf2;

    /**
     * 比价方3
     */
    private String bjf3;

    /**
     * 选择该供货商原因
     */
    private String supplierreasons;

    /**
     * 选择比价采购原因
     */
    private String bjreasons;

    /**
     * 运费承担
     */
    private String freight;

    /**
     * 合同税费
     */
    private String contracttax;

    /**
     * 合同审批类型--存id
     */
    private String contractauditingtype;

    /**
     * 合同审批类型--存名称
     */
    private String contractauditingtypename;

    /**
     * 合同审批类型--存编码code
     */
    private String contractauditingtypcode;

    /**
     * 出卖人id
     */
    private String venditorid;

    /**
     * 出卖人名称
     */
    private String venditorname;

    /**
     * 买受人id
     */
    private String buyerid;

    /**
     * 买受人名称
     */
    private String buyername;


    /**
     * 创建人id
     */
    private String creatuserid;

    /**
     * 创建人名字
     */
    @TableField(exist = false)
    private String creatusername;


    /**
     * 合同状态
     */
    private String contractstatus;

    /**
     * 审批状态
     */
    private String auditingstatus;

    /**
     * 回退人id
     */
    private String backuserid;

    /**
     * 回退人名字
     */
    @TableField(exist = false)
    private String backusername;

    /**
     * 回退时间
     */
    private String backtime;

    /**
     * 回退原因
     */
    private String backreason;

    /**
     * 用于接收审批结果
     */
    @TableField(exist = false)
    private String spresult;

    /**
     * 出卖方委托人
     */
    private String venditorwt;

    /**
     * 买受方委托人
     */
    private String buyerwt;

    /**
     * 逻辑删除标志
     */
    private String flag;

    /**
     * 备注
     */
    private String note;

    /**
     * 合同文本类型
     */
    private String contracttemp;

    /**
     * 合同总金额
     */
    private String summoney;

    /**
     * 审批代码
     */
    private String spcode;

    /**
     * 用于接收审批人真实姓名
     */
    @TableField(exist = false)
    private String spuser;

    /**
     * 用于查询时间区间传值
     */
    @TableField(exist = false)
    private String datetime1;
    /**
     * 用于查询时间区间传值
     */
    @TableField(exist = false)
    private String datetime2;

    private String zjcode;

    private String zjname;


    public String getAuditingstatus() {
        return auditingstatus;
    }

    public void setAuditingstatus(String auditingstatus) {
        this.auditingstatus = auditingstatus == null ? null : auditingstatus.trim();
    }

    public String getBackreason() {
        return backreason;
    }

    public void setBackreason(String backreason) {
        this.backreason = backreason == null ? null : backreason.trim();
    }

    public String getBacktime() {
        return backtime;
    }

    public void setBacktime(String backtime) {
        this.backtime = backtime == null ? null : backtime.trim();
    }

    public String getBackuserid() {
        return backuserid;
    }

    public void setBackuserid(String backuserid) {
        this.backuserid = backuserid == null ? null : backuserid.trim();
    }

    public String getBjf1() {
        return bjf1;
    }

    public void setBjf1(String bjf1) {
        this.bjf1 = bjf1 == null ? null : bjf1.trim();
    }

    public String getBjf2() {
        return bjf2;
    }

    public void setBjf2(String bjf2) {
        this.bjf2 = bjf2 == null ? null : bjf2.trim();
    }

    public String getBjf3() {
        return bjf3;
    }

    public void setBjf3(String bjf3) {
        this.bjf3 = bjf3 == null ? null : bjf3.trim();
    }

    public String getBjreasons() {
        return bjreasons;
    }

    public void setBjreasons(String bjreasons) {
        this.bjreasons = bjreasons == null ? null : bjreasons.trim();
    }

    public String getBuyerid() {
        return buyerid;
    }

    public void setBuyerid(String buyerid) {
        this.buyerid = buyerid == null ? null : buyerid.trim();
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername == null ? null : buyername.trim();
    }

    public String getBuyerwt() {
        return buyerwt;
    }

    public void setBuyerwt(String buyerwt) {
        this.buyerwt = buyerwt == null ? null : buyerwt.trim();
    }

    public String getContractarea() {
        return contractarea;
    }

    public void setContractarea(String contractarea) {
        this.contractarea = contractarea == null ? null : contractarea.trim();
    }

    public String getContractauditingtypcode() {
        return contractauditingtypcode;
    }

    public void setContractauditingtypcode(String contractauditingtypcode) {
        this.contractauditingtypcode = contractauditingtypcode == null ? null : contractauditingtypcode.trim();
    }

    public String getContractauditingtype() {
        return contractauditingtype;
    }

    public void setContractauditingtype(String contractauditingtype) {
        this.contractauditingtype = contractauditingtype == null ? null : contractauditingtype.trim();
    }

    public String getContractauditingtypename() {
        return contractauditingtypename;
    }

    public void setContractauditingtypename(String contractauditingtypename) {
        this.contractauditingtypename = contractauditingtypename == null ? null : contractauditingtypename.trim();
    }

    public String getContractid() {
        return contractid;
    }

    public void setContractid(String contractid) {
        this.contractid = contractid == null ? null : contractid.trim();
    }

    public String getContractmethod() {
        return contractmethod;
    }

    public void setContractmethod(String contractmethod) {
        this.contractmethod = contractmethod == null ? null : contractmethod.trim();
    }

    public String getContractstatus() {
        return contractstatus;
    }

    public void setContractstatus(String contractstatus) {
        this.contractstatus = contractstatus == null ? null : contractstatus.trim();
    }

    public String getContracttax() {
        return contracttax;
    }

    public void setContracttax(String contracttax) {
        this.contracttax = contracttax == null ? null : contracttax.trim();
    }

    public String getContracttemp() {
        return contracttemp;
    }

    public void setContracttemp(String contracttemp) {
        this.contracttemp = contracttemp == null ? null : contracttemp.trim();
    }

    public String getContracttype() {
        return contracttype;
    }

    public void setContracttype(String contracttype) {
        this.contracttype = contracttype == null ? null : contracttype.trim();
    }

    public String getCreatuserid() {
        return creatuserid;
    }

    public void setCreatuserid(String creatuserid) {
        this.creatuserid = creatuserid == null ? null : creatuserid.trim();
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight == null ? null : freight.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod == null ? null : paymentmethod.trim();
    }

    public String getSerialsnumber() {
        return serialsnumber;
    }

    public void setSerialsnumber(String serialsnumber) {
        this.serialsnumber = serialsnumber == null ? null : serialsnumber.trim();
    }

    public String getSpcode() {
        return spcode;
    }

    public void setSpcode(String spcode) {
        this.spcode = spcode == null ? null : spcode.trim();
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate == null ? null : startdate.trim();
    }

    public String getSummoney() {
        return summoney;
    }

    public void setSummoney(String summoney) {
        this.summoney = summoney == null ? null : summoney.trim();
    }

    public String getSupplierreasons() {
        return supplierreasons;
    }

    public void setSupplierreasons(String supplierreasons) {
        this.supplierreasons = supplierreasons == null ? null : supplierreasons.trim();
    }

    public String getVenditorid() {
        return venditorid;
    }

    public void setVenditorid(String venditorid) {
        this.venditorid = venditorid == null ? null : venditorid.trim();
    }

    public String getVenditorname() {
        return venditorname;
    }

    public void setVenditorname(String venditorname) {
        this.venditorname = venditorname == null ? null : venditorname.trim();
    }

    public String getVenditorwt() {
        return venditorwt;
    }

    public void setVenditorwt(String venditorwt) {
        this.venditorwt = venditorwt == null ? null : venditorwt.trim();
    }

    public String getZjcode() {
        return zjcode;
    }

    public void setZjcode(String zjcode) {
        this.zjcode = zjcode == null ? null : zjcode.trim();
    }

    public String getZjname() {
        return zjname;
    }

    public void setZjname(String zjname) {
        this.zjname = zjname == null ? null : zjname.trim();
    }


    public String getContracttypecode() {
        return contracttypecode;
    }

    public void setContracttypecode(String contracttypecode) {
        this.contracttypecode = contracttypecode;
    }


    public String getCreatusername() {
        return creatusername;
    }

    public void setCreatusername(String creatusername) {
        this.creatusername = creatusername;
    }


    public String getBackusername() {
        return backusername;
    }

    public void setBackusername(String backusername) {
        this.backusername = backusername;
    }

    public String getSpresult() {
        return spresult;
    }

    public void setSpresult(String spresult) {
        this.spresult = spresult;
    }

    public String getSpuser() {
        return spuser;
    }

    public void setSpuser(String spuser) {
        this.spuser = spuser;
    }

    public String getDatetime1() {
        return datetime1;
    }

    public void setDatetime1(String datetime1) {
        this.datetime1 = datetime1;
    }

    public String getDatetime2() {
        return datetime2;
    }

    public void setDatetime2(String datetime2) {
        this.datetime2 = datetime2;
    }
}