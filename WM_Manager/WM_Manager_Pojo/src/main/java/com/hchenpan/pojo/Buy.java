package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_BUY")
public class Buy extends BasePojo {
    /**
     * 采购计划号
     */
    private String buycode;
    /**
     * 采购大类
     */
    private String buytype;
    /**
     * 采购大类名称
     */
    private String buyname;
    /**
     * 审批状态
     */
    private String spstatus;
    /**
     * 审批节点
     */
    private String spcode;
    /**
     * 采购总金额
     */
    private String buysummoney;
    /**
     * 采购日期
     */
    private String buydate;
    /**
     * 采购人
     */
    private String buysqr;
    /**
     * 采购人id
     */
    private String buysqrid;
    /**
     * 采购部门
     */
    private String buyunit;
    /**
     * 采购部门id
     */
    private String buyunitid;
    /**
     * 资金单位
     */
    private String zjname;
    /**
     * 资金编码
     */
    private String zjcode;
    /**
     * 备注
     */
    private String note;
    /**
     * 提交人id
     */
    private String userid;

    /**
     * 标识
     */
    private String flag;
    /**
     * 审批结束时间
     */
    private String spjsdate;
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
    /**
     * 用于接收审批人真实姓名
     */
    @TableField(exist = false)
    private String spuser;

    /**
     * 用于接收审批人id
     */
    @TableField(exist = false)
    private String spuserid;

    /**
     * 用于接收审批结果
     */
    @TableField(exist = false)
    private String spresult;

    /**
     * 用于接收审批意见
     */
    @TableField(exist = false)
    private String spadvice;


    public String getBuycode() {
        return buycode;
    }

    public void setBuycode(String buycode) {
        this.buycode = buycode == null ? null : buycode.trim();
    }

    public String getBuydate() {
        return buydate;
    }

    public void setBuydate(String buydate) {
        this.buydate = buydate == null ? null : buydate.trim();
    }

    public String getBuyname() {
        return buyname;
    }

    public void setBuyname(String buyname) {
        this.buyname = buyname == null ? null : buyname.trim();
    }

    public String getBuysqr() {
        return buysqr;
    }

    public void setBuysqr(String buysqr) {
        this.buysqr = buysqr == null ? null : buysqr.trim();
    }

    public String getBuysqrid() {
        return buysqrid;
    }

    public void setBuysqrid(String buysqrid) {
        this.buysqrid = buysqrid == null ? null : buysqrid.trim();
    }

    public String getBuysummoney() {
        return buysummoney;
    }

    public void setBuysummoney(String buysummoney) {
        this.buysummoney = buysummoney == null ? null : buysummoney.trim();
    }

    public String getBuytype() {
        return buytype;
    }

    public void setBuytype(String buytype) {
        this.buytype = buytype == null ? null : buytype.trim();
    }

    public String getBuyunit() {
        return buyunit;
    }

    public void setBuyunit(String buyunit) {
        this.buyunit = buyunit == null ? null : buyunit.trim();
    }

    public String getBuyunitid() {
        return buyunitid;
    }

    public void setBuyunitid(String buyunitid) {
        this.buyunitid = buyunitid == null ? null : buyunitid.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getSpcode() {
        return spcode;
    }

    public void setSpcode(String spcode) {
        this.spcode = spcode == null ? null : spcode.trim();
    }

    public String getSpjsdate() {
        return spjsdate;
    }

    public void setSpjsdate(String spjsdate) {
        this.spjsdate = spjsdate == null ? null : spjsdate.trim();
    }

    public String getSpstatus() {
        return spstatus;
    }

    public void setSpstatus(String spstatus) {
        this.spstatus = spstatus == null ? null : spstatus.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
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

    public String getSpuser() {
        return spuser;
    }

    public void setSpuser(String spuser) {
        this.spuser = spuser;
    }

    public String getSpuserid() {
        return spuserid;
    }

    public void setSpuserid(String spuserid) {
        this.spuserid = spuserid;
    }

    public String getSpresult() {
        return spresult;
    }

    public void setSpresult(String spresult) {
        this.spresult = spresult;
    }

    public String getSpadvice() {
        return spadvice;
    }

    public void setSpadvice(String spadvice) {
        this.spadvice = spadvice;
    }
}