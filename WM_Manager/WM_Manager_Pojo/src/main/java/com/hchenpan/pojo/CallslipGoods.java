package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;
/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_CALLSLIPGOODS")
public class CallslipGoods extends BasePojo {
    /*领料单号*/
    private String callslipcode;

    /*物资编码*/
    private String wzcode;
    /*物资名称*/
    private String wzname;

    //	物资单位
    private String unit;

    //	物资型号
    private String modelspcification;


    //	物资单价---出库时写入移动平均价
    private String price;

    //领料总数
    private String sum;

    //	剩余领料数量
    private String sysum;

    //	资金类型
    private String zjcode;

    //	资金单位
    private String zjname;

    //	仓库名称
    private String stockname;

    //	仓库code
    private String stockcode;

    /*标识*/
    private String flag;

    //	物资总价--出库时写入
    private String summoney;

    //	来源
    private String comefrom;

    //需求号
    private String plancode;

    //	采购计划号
    private String buycode;

    //	合同流水号
    private String contractbasicid;

    //	入库编号
    private String rkcode;

    //	剩余出库数量-用于接收需求单上的剩余量
    @TableField(exist = false)
    private String sycknum;


    public String getBuycode() {
        return buycode;
    }

    public void setBuycode(String buycode) {
        this.buycode = buycode == null ? null : buycode.trim();
    }

    public String getCallslipcode() {
        return callslipcode;
    }

    public void setCallslipcode(String callslipcode) {
        this.callslipcode = callslipcode == null ? null : callslipcode.trim();
    }

    public String getComefrom() {
        return comefrom;
    }

    public void setComefrom(String comefrom) {
        this.comefrom = comefrom == null ? null : comefrom.trim();
    }

    public String getContractbasicid() {
        return contractbasicid;
    }

    public void setContractbasicid(String contractbasicid) {
        this.contractbasicid = contractbasicid == null ? null : contractbasicid.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getModelspcification() {
        return modelspcification;
    }

    public void setModelspcification(String modelspcification) {
        this.modelspcification = modelspcification == null ? null : modelspcification.trim();
    }

    public String getPlancode() {
        return plancode;
    }

    public void setPlancode(String plancode) {
        this.plancode = plancode == null ? null : plancode.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getRkcode() {
        return rkcode;
    }

    public void setRkcode(String rkcode) {
        this.rkcode = rkcode == null ? null : rkcode.trim();
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

    public String getSummoney() {
        return summoney;
    }

    public void setSummoney(String summoney) {
        this.summoney = summoney == null ? null : summoney.trim();
    }

    public String getSysum() {
        return sysum;
    }

    public void setSysum(String sysum) {
        this.sysum = sysum == null ? null : sysum.trim();
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


    public String getSycknum() {
        return sycknum;
    }

    public void setSycknum(String sycknum) {
        this.sycknum = sycknum;
    }
}