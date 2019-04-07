package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

import java.util.Set;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_WAREHOUSENUM")
public class WarehouseNum extends BasePojo {
    /***
     * 发货人
     */
    private String fhr;
    /***
     * 发货人中文
     */
    private String fhrzw;
    @TableField(exist = false)
    private String fhrid;
    private String flag;
    /**
     * 所属部门
     */
    private String ssunitid;
    /**
     * 用于接收所属部门中文名
     */
    @TableField(exist = false)
    private String ssunitname;
    /**
     * 仓库编码
     */
    private String stockcode;
    /**
     * 仓库名称
     */
    private String stockname;
    /**
     * 仓库类型
     */
    private String stocktype;
    /**
     * 用于接收仓库类型中文名
     */
    @TableField(exist = false)
    private String stocktypename;
    /**
     * 审批人
     */
    @TableField(exist = false)
    private Set<User> spusers;

    public String getFhr() {
        return fhr;
    }

    public void setFhr(String fhr) {
        this.fhr = fhr;
    }

    public String getFhrzw() {
        return fhrzw;
    }

    public void setFhrzw(String fhrzw) {
        this.fhrzw = fhrzw;
    }

    public String getFhrid() {
        return fhrid;
    }

    public void setFhrid(String fhrid) {
        this.fhrid = fhrid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSsunitid() {
        return ssunitid;
    }

    public void setSsunitid(String ssunitid) {
        this.ssunitid = ssunitid;
    }

    public String getSsunitname() {
        return ssunitname;
    }

    public void setSsunitname(String ssunitname) {
        this.ssunitname = ssunitname;
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode;
    }

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    public String getStocktype() {
        return stocktype;
    }

    public void setStocktype(String stocktype) {
        this.stocktype = stocktype;
    }

    public String getStocktypename() {
        return stocktypename;
    }

    public void setStocktypename(String stocktypename) {
        this.stocktypename = stocktypename;
    }

    public Set<User> getSpusers() {
        return spusers;
    }

    public void setSpusers(Set<User> spusers) {
        this.spusers = spusers;
    }
}