package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_WAREHOUSING")
public class Warehousing extends BasePojo {
    /*入库类型*/
    private String entryinfotype;


    /*单据号*/
    private String notecode;
    /*仓库编码*/
    private String storehousecode;

    /*仓库编码id*/
    private String storehouseid;

    /*仓库名称*/
    private String storehousename;
    /*收料人*/
    private String consignee;
    //入库状态
    private String rkstatus;

    //	入库时间
    private String entrydate;

    //	保管员
    private String storeman;
    private String zjname;
    private String zjcode;
    //	入库总金额
    private String summoney;

    //	 备注
    private String note;

    /*标识*/
    private String flag;
    /*用于查询时间区间传值*/
    @TableField(exist = false)
    private String datetime1;
    /*用于查询时间区间传值*/
    @TableField(exist = false)
    private String datetime2;
    //提交人id
    private String userid;


    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate == null ? null : entrydate.trim();
    }

    public String getEntryinfotype() {
        return entryinfotype;
    }

    public void setEntryinfotype(String entryinfotype) {
        this.entryinfotype = entryinfotype == null ? null : entryinfotype.trim();
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

    public String getNotecode() {
        return notecode;
    }

    public void setNotecode(String notecode) {
        this.notecode = notecode == null ? null : notecode.trim();
    }

    public String getRkstatus() {
        return rkstatus;
    }

    public void setRkstatus(String rkstatus) {
        this.rkstatus = rkstatus == null ? null : rkstatus.trim();
    }

    public String getStorehousecode() {
        return storehousecode;
    }

    public void setStorehousecode(String storehousecode) {
        this.storehousecode = storehousecode == null ? null : storehousecode.trim();
    }

    public String getStorehouseid() {
        return storehouseid;
    }

    public void setStorehouseid(String storehouseid) {
        this.storehouseid = storehouseid == null ? null : storehouseid.trim();
    }

    public String getStorehousename() {
        return storehousename;
    }

    public void setStorehousename(String storehousename) {
        this.storehousename = storehousename == null ? null : storehousename.trim();
    }

    public String getStoreman() {
        return storeman;
    }

    public void setStoreman(String storeman) {
        this.storeman = storeman == null ? null : storeman.trim();
    }

    public String getSummoney() {
        return summoney;
    }

    public void setSummoney(String summoney) {
        this.summoney = summoney == null ? null : summoney.trim();
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