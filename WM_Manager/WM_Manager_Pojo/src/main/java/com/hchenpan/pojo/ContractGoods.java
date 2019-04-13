package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_CONTRACTGOODS")
public class ContractGoods extends BasePojo {
    /**
     * 合同流水号
     */
    private String contractbasicid;
    /**
     * 采购计划号
     */
    private String buycode;
    /**
     * 物资编码
     */
    private String wzcode;
    /**
     * 物资名称
     */
    private String wzname;
    /**
     * 采购数量
     */
    private String buynum;
    /**
     * 采购金额
     */
    private String buymoney;
    /**
     * 采购单价
     */
    private String buyprice;

    /**
     * 实际采购数量
     */
    private String planbum;

    /**
     * 实际采购单价
     */
    private String planprice;

    /**
     * 物资单位
     */
    private String unit;

    /**
     * 需求号
     */
    private String plancode;


    /**
     * 物资总金额
     */
    private String summoney;

    /**
     * 剩余入库数量
     */
    private String syrksum;


    /**
     * 标识
     */
    private String flag;
    /**
     * 实际出库量
     */
    @TableField(exist = false)
    private String sjnum;

    /**
     * 规格型号
     */
    private String modelspcification;


    /**
     * 剩余物资
     */
    @TableField(exist = false)
    private String synum;

    /**
     * 资金编码
     */
    private String zjcode;

    /**
     * 资金名称
     */
    private String zjname;


    public String getBuycode() {
        return buycode;
    }

    public void setBuycode(String buycode) {
        this.buycode = buycode == null ? null : buycode.trim();
    }

    public String getBuymoney() {
        return buymoney;
    }

    public void setBuymoney(String buymoney) {
        this.buymoney = buymoney == null ? null : buymoney.trim();
    }

    public String getBuynum() {
        return buynum;
    }

    public void setBuynum(String buynum) {
        this.buynum = buynum == null ? null : buynum.trim();
    }

    public String getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(String buyprice) {
        this.buyprice = buyprice == null ? null : buyprice.trim();
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

    public String getPlanbum() {
        return planbum;
    }

    public void setPlanbum(String planbum) {
        this.planbum = planbum == null ? null : planbum.trim();
    }

    public String getPlancode() {
        return plancode;
    }

    public void setPlancode(String plancode) {
        this.plancode = plancode == null ? null : plancode.trim();
    }

    public String getPlanprice() {
        return planprice;
    }

    public void setPlanprice(String planprice) {
        this.planprice = planprice == null ? null : planprice.trim();
    }

    public String getSummoney() {
        return summoney;
    }

    public void setSummoney(String summoney) {
        this.summoney = summoney == null ? null : summoney.trim();
    }

    public String getSyrksum() {
        return syrksum;
    }

    public void setSyrksum(String syrksum) {
        this.syrksum = syrksum == null ? null : syrksum.trim();
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


    public String getSjnum() {
        return sjnum;
    }

    public void setSjnum(String sjnum) {
        this.sjnum = sjnum;
    }

    public String getSynum() {
        return synum;
    }

    public void setSynum(String synum) {
        this.synum = synum;
    }
}