package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

import java.util.Set;

/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_SPTYPESPLEVEL")
public class SptypespLevel extends BasePojo {
    /**
     * 逻辑删除
     */
    private String flag;
    /**
     * 审批级别编码
     */
    private String splevelcode;
    /**
     * 审批备注
     */
    private String spnote;
    /**
     * 审批类型编码
     */
    private String sptypecode;
    /**
     * 审批人id
     */
    private String spusersid;

    /**
     * 审批人中文
     */
    private String spuserszw;

    /**
     * 审批人集合
     */
    @TableField(exist = false)
    private Set<User> spusers;

    /**
     * 审批类型名称
     */
    @TableField(exist = false)
    private String sptypename;
    /**
     * 审批级别名称
     */
    @TableField(exist = false)
    private String splevelname;

    /**
     * 中间传值审批人
     */
    @TableField(exist = false)
    private String deptname;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSplevelcode() {
        return splevelcode;
    }

    public void setSplevelcode(String splevelcode) {
        this.splevelcode = splevelcode;
    }

    public String getSpnote() {
        return spnote;
    }

    public void setSpnote(String spnote) {
        this.spnote = spnote;
    }

    public String getSptypecode() {
        return sptypecode;
    }

    public void setSptypecode(String sptypecode) {
        this.sptypecode = sptypecode;
    }

    public String getSpusersid() {
        return spusersid;
    }

    public void setSpusersid(String spusersid) {
        this.spusersid = spusersid;
    }

    public String getSpuserszw() {
        return spuserszw;
    }

    public void setSpuserszw(String spuserszw) {
        this.spuserszw = spuserszw;
    }

    public Set<User> getSpusers() {
        return spusers;
    }

    public void setSpusers(Set<User> spusers) {
        this.spusers = spusers;
    }

    public String getSptypename() {
        return sptypename;
    }

    public void setSptypename(String sptypename) {
        this.sptypename = sptypename;
    }

    public String getSplevelname() {
        return splevelname;
    }

    public void setSplevelname(String splevelname) {
        this.splevelname = splevelname;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}