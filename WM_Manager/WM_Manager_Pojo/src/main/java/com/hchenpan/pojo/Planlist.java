package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_PLANLIST")
public class Planlist extends BasePojo {
    /**
     * 计划号
     */
    private String plancode;
    /**
     * 计划号id
     */
    private String plancodeid;
    /**
     * 计划大类
     */
    private String plantype;
    /**
     * 计划名称
     */
    private String planname;
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
     * 图号
     */
    private String tuhao;
    /**
     * 主机名称及型号
     */
    private String hostname;
    /**
     * 单位
     */
    private String unit;
    /**
     * 单价
     */
    private String price;
    /**
     * 计划数量
     */
    private String plannum;
    /**
     * 计划金额
     */
    private String planmoney;


    /**
     * 计划审批数量
     */
    private String spnum;
    /**
     * 计划审批单价
     */
    private String spprice;
    /**
     * 计划审批金额
     */
    private String spmoney;
    /**
     * 剩余量
     */
    private String synum;
    /**
     * 备注
     */
    private String note;

    /**
     * 标识
     */
    private String flag;
    private String zjname;
    private String zjcode;


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname == null ? null : hostname.trim();
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

    public String getPlancode() {
        return plancode;
    }

    public void setPlancode(String plancode) {
        this.plancode = plancode == null ? null : plancode.trim();
    }

    public String getPlancodeid() {
        return plancodeid;
    }

    public void setPlancodeid(String plancodeid) {
        this.plancodeid = plancodeid == null ? null : plancodeid.trim();
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

    public String getPlannum() {
        return plannum;
    }

    public void setPlannum(String plannum) {
        this.plannum = plannum == null ? null : plannum.trim();
    }

    public String getPlantype() {
        return plantype;
    }

    public void setPlantype(String plantype) {
        this.plantype = plantype == null ? null : plantype.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getSpmoney() {
        return spmoney;
    }

    public void setSpmoney(String spmoney) {
        this.spmoney = spmoney == null ? null : spmoney.trim();
    }

    public String getSpnum() {
        return spnum;
    }

    public void setSpnum(String spnum) {
        this.spnum = spnum == null ? null : spnum.trim();
    }

    public String getSpprice() {
        return spprice;
    }

    public void setSpprice(String spprice) {
        this.spprice = spprice == null ? null : spprice.trim();
    }

    public String getSynum() {
        return synum;
    }

    public void setSynum(String synum) {
        this.synum = synum == null ? null : synum.trim();
    }

    public String getTuhao() {
        return tuhao;
    }

    public void setTuhao(String tuhao) {
        this.tuhao = tuhao == null ? null : tuhao.trim();
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
}