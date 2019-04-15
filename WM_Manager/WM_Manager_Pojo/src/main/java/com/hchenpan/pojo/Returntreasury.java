package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_RETURNTREASURY")
public class Returntreasury extends BasePojo {
    /*退库类型*/
    private String tktype;
    //退库流水号
    private String tkcode;
    //申请时间
    private String sqdate;
    //退库申请人
    private String sqr;
    //退库操作人
    private String tkczr;
    //退库操作人id
    private String tkczrid;
    //退库原因
    private String tkreason;
    //退库状态
    private String tkstatus;

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


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getSqdate() {
        return sqdate;
    }

    public void setSqdate(String sqdate) {
        this.sqdate = sqdate == null ? null : sqdate.trim();
    }

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr == null ? null : sqr.trim();
    }

    public String getTkcode() {
        return tkcode;
    }

    public void setTkcode(String tkcode) {
        this.tkcode = tkcode == null ? null : tkcode.trim();
    }

    public String getTkczr() {
        return tkczr;
    }

    public void setTkczr(String tkczr) {
        this.tkczr = tkczr == null ? null : tkczr.trim();
    }

    public String getTkczrid() {
        return tkczrid;
    }

    public void setTkczrid(String tkczrid) {
        this.tkczrid = tkczrid == null ? null : tkczrid.trim();
    }

    public String getTkreason() {
        return tkreason;
    }

    public void setTkreason(String tkreason) {
        this.tkreason = tkreason == null ? null : tkreason.trim();
    }

    public String getTkstatus() {
        return tkstatus;
    }

    public void setTkstatus(String tkstatus) {
        this.tkstatus = tkstatus == null ? null : tkstatus.trim();
    }

    public String getTktype() {
        return tktype;
    }

    public void setTktype(String tktype) {
        this.tktype = tktype == null ? null : tktype.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
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