package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;
/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_CANCELLINGSTOCKSSQ")
public class CancellingstocksSQ extends BasePojo {
    // 退库类型
    private String TKtype;

    // 退库单号
    private String TKcode;

    // 备注
    private String note;

    // 逻辑删除标志
    private String flag;
    // 申请时间
    private String sqdate;
    // 退库申请人
    private String sqr;
    // 退库人id
    private String userid;

    // 退库人真实姓名
    private String realname;


    /* 退库状态 */
    private String tkstatus;


    /* 用于查询时间区间传值 */
    @TableField(exist = false)
    private String datetime1;
    /* 用于查询时间区间传值 */
    @TableField(exist = false)
    private String datetime2;


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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
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

    public String getTkstatus() {
        return tkstatus;
    }

    public void setTkstatus(String tkstatus) {
        this.tkstatus = tkstatus == null ? null : tkstatus.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getTKtype() {
        return TKtype;
    }

    public void setTKtype(String TKtype) {
        this.TKtype = TKtype;
    }

    public String getTKcode() {
        return TKcode;
    }

    public void setTKcode(String TKcode) {
        this.TKcode = TKcode;
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