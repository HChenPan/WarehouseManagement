package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_APPLYTRANSFER")
public class Applytransfer extends BasePojo {

    /**
     * 申请调入编号
     */
    private String applytransfercode;

    /**
     * 上报状态
     */
    private String sbstatus;

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
     * 上报金额
     */
    private String sbmoney;
    /**
     * 实际金额
     */
    private String realmoney;

    private String zjname;
    private String zjcode;

    /**
     * 调入仓库
     */
    private String drck;
    /**
     * 调入仓库编号
     */
    private String drckcode;
    /**
     * 调入仓库id
     */
    private String drckid;

    /**
     * 调出仓库
     */
    private String dcck;
    /**
     * 调出仓库编号
     */
    private String dcckcode;
    /**
     * 调出仓库id
     */
    private String dcckid;

    /**
     * 标识
     */
    private String flag;

    /**
     * 备注
     */
    private String note;
    /**
     * 上报日期
     */
    private String sbdate;

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
    //提交人id
    private String userid;


    public String getApplytransfercode() {
        return applytransfercode;
    }

    public void setApplytransfercode(String applytransfercode) {
        this.applytransfercode = applytransfercode == null ? null : applytransfercode.trim();
    }

    public String getDcck() {
        return dcck;
    }

    public void setDcck(String dcck) {
        this.dcck = dcck == null ? null : dcck.trim();
    }

    public String getDcckcode() {
        return dcckcode;
    }

    public void setDcckcode(String dcckcode) {
        this.dcckcode = dcckcode == null ? null : dcckcode.trim();
    }

    public String getDcckid() {
        return dcckid;
    }

    public void setDcckid(String dcckid) {
        this.dcckid = dcckid == null ? null : dcckid.trim();
    }

    public String getDrck() {
        return drck;
    }

    public void setDrck(String drck) {
        this.drck = drck == null ? null : drck.trim();
    }

    public String getDrckcode() {
        return drckcode;
    }

    public void setDrckcode(String drckcode) {
        this.drckcode = drckcode == null ? null : drckcode.trim();
    }

    public String getDrckid() {
        return drckid;
    }

    public void setDrckid(String drckid) {
        this.drckid = drckid == null ? null : drckid.trim();
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

    public String getRealmoney() {
        return realmoney;
    }

    public void setRealmoney(String realmoney) {
        this.realmoney = realmoney == null ? null : realmoney.trim();
    }

    public String getSbdate() {
        return sbdate;
    }

    public void setSbdate(String sbdate) {
        this.sbdate = sbdate == null ? null : sbdate.trim();
    }

    public String getSbmoney() {
        return sbmoney;
    }

    public void setSbmoney(String sbmoney) {
        this.sbmoney = sbmoney == null ? null : sbmoney.trim();
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
}