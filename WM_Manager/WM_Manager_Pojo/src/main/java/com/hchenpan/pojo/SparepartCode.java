package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_SPAREPARTCODE")
public class SparepartCode extends BasePojo {

    @TableField(exist = false)
    private String _parentid;

    @TableField("parentid")
    private String parentid;

    private String parentcode;

    private String code;

    private String currencytype;

    private String currencyunit;

    private String devicecode;

    private String hostname;

    private String modelspecification;

    private String name;

    private String planprice;

    private String purchasetime;

    private String remark;

    private String spareparttype;

    private String spareparttypecode;

    private String stockmin;

    private String supplycycle;

    private String tuhao;

    private String unit;

    private String ywname;

    private String description;

    public String get_parentid() {
        return _parentid;
    }

    public void set_parentid() {
        this._parentid = this.parentid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getParentcode() {
        return parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrencytype() {
        return currencytype;
    }

    public void setCurrencytype(String currencytype) {
        this.currencytype = currencytype;
    }

    public String getCurrencyunit() {
        return currencyunit;
    }

    public void setCurrencyunit(String currencyunit) {
        this.currencyunit = currencyunit;
    }

    public String getDevicecode() {
        return devicecode;
    }

    public void setDevicecode(String devicecode) {
        this.devicecode = devicecode;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getModelspecification() {
        return modelspecification;
    }

    public void setModelspecification(String modelspecification) {
        this.modelspecification = modelspecification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanprice() {
        return planprice;
    }

    public void setPlanprice(String planprice) {
        this.planprice = planprice;
    }

    public String getPurchasetime() {
        return purchasetime;
    }

    public void setPurchasetime(String purchasetime) {
        this.purchasetime = purchasetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpareparttype() {
        return spareparttype;
    }

    public void setSpareparttype(String spareparttype) {
        this.spareparttype = spareparttype;
    }

    public String getSpareparttypecode() {
        return spareparttypecode;
    }

    public void setSpareparttypecode(String spareparttypecode) {
        this.spareparttypecode = spareparttypecode;
    }

    public String getStockmin() {
        return stockmin;
    }

    public void setStockmin(String stockmin) {
        this.stockmin = stockmin;
    }

    public String getSupplycycle() {
        return supplycycle;
    }

    public void setSupplycycle(String supplycycle) {
        this.supplycycle = supplycycle;
    }

    public String getTuhao() {
        return tuhao;
    }

    public void setTuhao(String tuhao) {
        this.tuhao = tuhao;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getYwname() {
        return ywname;
    }

    public void setYwname(String ywname) {
        this.ywname = ywname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}