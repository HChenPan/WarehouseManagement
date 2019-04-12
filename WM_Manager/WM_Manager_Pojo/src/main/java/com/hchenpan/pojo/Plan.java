package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_PLAN")
public class Plan extends BasePojo {
    /**
     * 计划号
     */
    private String plancode;
    /**
     * 计划大类
     */
    private String plantype;
    /**
     * 审批状态
     */
    private String spstatus;
    /**
     * 审批节点
     */
    private String spcode;
    /**
     * 计划金额
     */
    private String planmoney;
    /**
     * 计划金额
     */
    private String planspmoney;
    /**
     * 计划名称
     */
    private String planname;
    /**
     * 计划大类名称id
     */
    private String planid;
    /**
     * 上报状态
     */
    private String sbstatus;
    /**
     * 计划申报日期
     */
    private String sbdate;
    /**
     * 项目号
     */
    private String projectcode;
    /**
     * 项目号id
     */
    private String projectid;
    /**
     * 申请人
     */
    private String sqrname;
    /**
     * 申请人id
     */
    private String sqrid;
    /**
     * 需求部门
     */
    private String sbunit;
    /**
     * 需求部门id
     */
    private String sbunitid;

    /**
     * 标识
     */
    private String flag;
    /**
     * 用途
     */
    private String note;
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
    private String zjname;
    private String zjcode;
    /**
     * 提交人id
     */
    private String userid;
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

    public String getPlancode() {
        return plancode;
    }

    public void setPlancode(String plancode) {
        this.plancode = plancode == null ? null : plancode.trim();
    }

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid == null ? null : planid.trim();
    }

    public String getPlanmoney() {
        return planmoney;
    }

    public void setPlanmoney(String planmoney) {
        this.planmoney = planmoney == null ? null : planmoney.trim();
    }

    public String getPlanname() {
        return planname;
    }

    public void setPlanname(String planname) {
        this.planname = planname == null ? null : planname.trim();
    }

    public String getPlanspmoney() {
        return planspmoney;
    }

    public void setPlanspmoney(String planspmoney) {
        this.planspmoney = planspmoney == null ? null : planspmoney.trim();
    }

    public String getPlantype() {
        return plantype;
    }

    public void setPlantype(String plantype) {
        this.plantype = plantype == null ? null : plantype.trim();
    }

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode == null ? null : projectcode.trim();
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid == null ? null : projectid.trim();
    }

    public String getSbdate() {
        return sbdate;
    }

    public void setSbdate(String sbdate) {
        this.sbdate = sbdate == null ? null : sbdate.trim();
    }

    public String getSbstatus() {
        return sbstatus;
    }

    public void setSbstatus(String sbstatus) {
        this.sbstatus = sbstatus == null ? null : sbstatus.trim();
    }

    public String getSbunit() {
        return sbunit;
    }

    public void setSbunit(String sbunit) {
        this.sbunit = sbunit == null ? null : sbunit.trim();
    }

    public String getSbunitid() {
        return sbunitid;
    }

    public void setSbunitid(String sbunitid) {
        this.sbunitid = sbunitid == null ? null : sbunitid.trim();
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

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid == null ? null : sqrid.trim();
    }

    public String getSqrname() {
        return sqrname;
    }

    public void setSqrname(String sqrname) {
        this.sqrname = sqrname == null ? null : sqrname.trim();
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