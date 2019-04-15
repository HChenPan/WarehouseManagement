package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_CALLSLIP")
public class Callslip extends BasePojo {
    //	领料大类
    private String callsliptype;

    //	领料单号
    private String callslipcode;

    //	仓库
    private String storehouse;

    //	部门
    private String department;

    //	申请日期
    private String applydate;

    //	用途
    private String application;

    //	工程号
    private String projectno;

    //	工程名称
    private String projectname;

    //	备注
    private String note;

    //	逻辑删除标志
    private String flag;

    //	创建人id
    private String userid;

    //	创建人真实姓名
    private String realname;


    //	领料大类编码
    private String llcode;

    //	领料状态
    private String status;

    //	仓库名称
    @TableField(exist = false)
    private String storehousename;

    //	出库人id
    private String outuserid;

    //	出库人姓名
    private String outusername;

    //	出库时间
    private String outtime;

    /*用于查询时间区间传值*/
    @TableField(exist = false)
    private String datetime1;
    /*用于查询时间区间传值*/
    @TableField(exist = false)
    private String datetime2;


    //	审批级别
    private String spcode;

    /*用于接收审批结果*/
    @TableField(exist = false)
    private String spresult;

    /*用于接收审批人真实姓名*/
    @TableField(exist = false)
    private String spuser;


    /*用于接收审批意见*/
    @TableField(exist = false)
    private String spadvice;

    /*用于接收审批人id*/
    @TableField(exist = false)
    private String spuserid;

    //	总价-默认1(只用于适应审批接口,不做任何价格判断)
    private String money;

    //	审批结束时间
    private String spjsdate;


    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application == null ? null : application.trim();
    }

    public String getApplydate() {
        return applydate;
    }

    public void setApplydate(String applydate) {
        this.applydate = applydate == null ? null : applydate.trim();
    }

    public String getCallslipcode() {
        return callslipcode;
    }

    public void setCallslipcode(String callslipcode) {
        this.callslipcode = callslipcode == null ? null : callslipcode.trim();
    }

    public String getCallsliptype() {
        return callsliptype;
    }

    public void setCallsliptype(String callsliptype) {
        this.callsliptype = callsliptype == null ? null : callsliptype.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getLlcode() {
        return llcode;
    }

    public void setLlcode(String llcode) {
        this.llcode = llcode == null ? null : llcode.trim();
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money == null ? null : money.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime == null ? null : outtime.trim();
    }

    public String getOutuserid() {
        return outuserid;
    }

    public void setOutuserid(String outuserid) {
        this.outuserid = outuserid == null ? null : outuserid.trim();
    }

    public String getOutusername() {
        return outusername;
    }

    public void setOutusername(String outusername) {
        this.outusername = outusername == null ? null : outusername.trim();
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname == null ? null : projectname.trim();
    }

    public String getProjectno() {
        return projectno;
    }

    public void setProjectno(String projectno) {
        this.projectno = projectno == null ? null : projectno.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getStorehouse() {
        return storehouse;
    }

    public void setStorehouse(String storehouse) {
        this.storehouse = storehouse == null ? null : storehouse.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }


    public String getStorehousename() {
        return storehousename;
    }

    public void setStorehousename(String storehousename) {
        this.storehousename = storehousename;
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

    public String getSpadvice() {
        return spadvice;
    }

    public void setSpadvice(String spadvice) {
        this.spadvice = spadvice;
    }

    public String getSpuserid() {
        return spuserid;
    }

    public void setSpuserid(String spuserid) {
        this.spuserid = spuserid;
    }
}