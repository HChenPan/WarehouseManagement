package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_STOCK")
public class Stock extends BasePojo {
    /**
     * 年月
     */
    private String stockyearmon;
    /**
     * 仓库编码
     */
    private String stockcode;
    /**
     * 仓库名称
     */
    private String stockname;
    /**
     * 物资编码
     */
    private String wzcode;
    /**
     * 物资名称
     */
    private String wzname;
    /**
     * 规格型号
     */
    private String modelspcification;
    /**
     * 单位
     */
    private String unit;
    /**
     * 本期期初值
     */
    private String bqstart;
    /**
     * 本期入库量和
     */
    private String bqin;
    /**
     * 本期出库量和
     */
    private String bqout;
    /**
     * 本期期末量
     */
    private String bqend;
    /**
     * 本期期初金额
     */
    private String bqstartmoney;
    /**
     * 本期入库金额
     */
    private String bqinmoney;
    /**
     * 本期出库金额
     */
    private String bqoutmoney;
    /**
     * 本期期末金额
     */
    private String bqendmoney;
    /**
     * 单价
     */
    private String price;
    /**
     * 结转标志
     */
    private String tranflag;

    private String zjname;
    private String zjcode;
    /**
     * 用于获取截取的结转上一月份
     */
    @TableField(exist = false)
    private String stocklastyearmon;


    public String getBqend() {
        return bqend;
    }

    public void setBqend(String bqend) {
        this.bqend = bqend == null ? null : bqend.trim();
    }

    public String getBqendmoney() {
        return bqendmoney;
    }

    public void setBqendmoney(String bqendmoney) {
        this.bqendmoney = bqendmoney == null ? null : bqendmoney.trim();
    }

    public String getBqin() {
        return bqin;
    }

    public void setBqin(String bqin) {
        this.bqin = bqin == null ? null : bqin.trim();
    }

    public String getBqinmoney() {
        return bqinmoney;
    }

    public void setBqinmoney(String bqinmoney) {
        this.bqinmoney = bqinmoney == null ? null : bqinmoney.trim();
    }

    public String getBqout() {
        return bqout;
    }

    public void setBqout(String bqout) {
        this.bqout = bqout == null ? null : bqout.trim();
    }

    public String getBqoutmoney() {
        return bqoutmoney;
    }

    public void setBqoutmoney(String bqoutmoney) {
        this.bqoutmoney = bqoutmoney == null ? null : bqoutmoney.trim();
    }

    public String getBqstart() {
        return bqstart;
    }

    public void setBqstart(String bqstart) {
        this.bqstart = bqstart == null ? null : bqstart.trim();
    }

    public String getBqstartmoney() {
        return bqstartmoney;
    }

    public void setBqstartmoney(String bqstartmoney) {
        this.bqstartmoney = bqstartmoney == null ? null : bqstartmoney.trim();
    }

    public String getModelspcification() {
        return modelspcification;
    }

    public void setModelspcification(String modelspcification) {
        this.modelspcification = modelspcification == null ? null : modelspcification.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode == null ? null : stockcode.trim();
    }

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname == null ? null : stockname.trim();
    }

    public String getStockyearmon() {
        return stockyearmon;
    }

    public void setStockyearmon(String stockyearmon) {
        this.stockyearmon = stockyearmon == null ? null : stockyearmon.trim();
    }

    public String getTranflag() {
        return tranflag;
    }

    public void setTranflag(String tranflag) {
        this.tranflag = tranflag == null ? null : tranflag.trim();
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


    public String getStocklastyearmon() {
        return stocklastyearmon;
    }

    public void setStocklastyearmon(String stocklastyearmon) {
        this.stocklastyearmon = stocklastyearmon;
    }
}