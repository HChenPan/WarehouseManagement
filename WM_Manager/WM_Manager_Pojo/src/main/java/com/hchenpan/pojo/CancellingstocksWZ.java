package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;
/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_CANCELLINGSTOCKSWZ")
public class CancellingstocksWZ extends BasePojo {
    /* 退库单号 */
    private String TKcode;
    /* 退库id */
    private String TKid;
    /* 领料单号 */
    private String callslipcode;

    /* 物资编码 */
    private String wzcode;
    /* 物资名称 */
    private String wzname;

    // 物资单位
    private String unit;

    // 物资型号
    private String modelSpecification;


    // 物资单价
    private String price;

    // 领料总数
    private String sum;

    // 剩余领料数量
    private String sysum;
    // 退库数量
    private String tksum;
    // 退库单价
    private String tkprice;

    // 资金类型
    private String zjcode;

    // 资金单位
    private String zjname;

    // 仓库名称
    private String stockname;

    // 仓库code
    private String stockcode;


    /* 标识 */
    private String flag;


    public String getCallslipcode() {
        return callslipcode;
    }

    public void setCallslipcode(String callslipcode) {
        this.callslipcode = callslipcode == null ? null : callslipcode.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
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

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum == null ? null : sum.trim();
    }

    public String getSysum() {
        return sysum;
    }

    public void setSysum(String sysum) {
        this.sysum = sysum == null ? null : sysum.trim();
    }

    public String getTkprice() {
        return tkprice;
    }

    public void setTkprice(String tkprice) {
        this.tkprice = tkprice == null ? null : tkprice.trim();
    }

    public String getTksum() {
        return tksum;
    }

    public void setTksum(String tksum) {
        this.tksum = tksum == null ? null : tksum.trim();
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

    public String getTKcode() {
        return TKcode;
    }

    public void setTKcode(String TKcode) {
        this.TKcode = TKcode;
    }

    public String getTKid() {
        return TKid;
    }

    public void setTKid(String TKid) {
        this.TKid = TKid;
    }

    public String getModelSpecification() {
        return modelSpecification;
    }

    public void setModelSpecification(String modelSpecification) {
        this.modelSpecification = modelSpecification;
    }


}