package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_TRANSFERLIST")
public class Transferlist extends BasePojo {
    /**
     * 申请调入 调拨任务单 编号
     */
    private String applytransfercode;
    /**
     * 申请调入 调拨任务单 id
     */
    private String applytransfercodeid;

    /**
     * 需求部门
     */
    private String sbunit;
    /**
     * 需求部门id
     */
    private String sbunitid;

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
     * 物资编码
     */
    private String wzcode;
    /**
     * 物资名称
     */
    private String wzname;
    /**
     * 物资id
     */
    private String wzid;
    /**
     * 型号规格
     */
    private String modelspcification;
    /**
     * 单位
     */
    private String unit;
    /**
     * 单价
     */
    private String price;

    /**
     * 申请数量
     */
    private String sqnum;

    /**
     * 累计发货数量
     */
    private String ljnum;

    /**
     * 实际单价
     */
    private String realprice;

    /**
     * 实际数量
     */
    private String realnum;

    /**
     * 是否修改单价及数量
     */
    private String iscorrect;

    /**
     * 备注
     */
    private String note;
    /**
     * 标识
     */
    private String flag;


    public String getApplytransfercode() {
        return applytransfercode;
    }

    public void setApplytransfercode(String applytransfercode) {
        this.applytransfercode = applytransfercode == null ? null : applytransfercode.trim();
    }

    public String getApplytransfercodeid() {
        return applytransfercodeid;
    }

    public void setApplytransfercodeid(String applytransfercodeid) {
        this.applytransfercodeid = applytransfercodeid == null ? null : applytransfercodeid.trim();
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getIscorrect() {
        return iscorrect;
    }

    public void setIscorrect(String iscorrect) {
        this.iscorrect = iscorrect == null ? null : iscorrect.trim();
    }

    public String getLjnum() {
        return ljnum;
    }

    public void setLjnum(String ljnum) {
        this.ljnum = ljnum == null ? null : ljnum.trim();
    }

    public String getModelspcification() {
        return modelspcification;
    }

    public void setModelspcification(String modelspcification) {
        this.modelspcification = modelspcification == null ? null : modelspcification.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getRealnum() {
        return realnum;
    }

    public void setRealnum(String realnum) {
        this.realnum = realnum == null ? null : realnum.trim();
    }

    public String getRealprice() {
        return realprice;
    }

    public void setRealprice(String realprice) {
        this.realprice = realprice == null ? null : realprice.trim();
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

    public String getSqnum() {
        return sqnum;
    }

    public void setSqnum(String sqnum) {
        this.sqnum = sqnum == null ? null : sqnum.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getWzcode() {
        return wzcode;
    }

    public void setWzcode(String wzcode) {
        this.wzcode = wzcode == null ? null : wzcode.trim();
    }

    public String getWzid() {
        return wzid;
    }

    public void setWzid(String wzid) {
        this.wzid = wzid == null ? null : wzid.trim();
    }

    public String getWzname() {
        return wzname;
    }

    public void setWzname(String wzname) {
        this.wzname = wzname == null ? null : wzname.trim();
    }
}