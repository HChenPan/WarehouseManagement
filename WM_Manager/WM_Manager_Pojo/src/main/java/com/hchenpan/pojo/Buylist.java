package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_BUYLIST")
public class Buylist extends BasePojo {
    /**
     * 采购计划号
     */
    private String buycode;
    /**
     * 采购计划号id
     */
    private String buycodeid;
    /**
     * 采购大类
     */
    private String buytype;
    /**
     * 采购名称
     */
    private String buyname;
    /**
     * 物资编码
     */
    private String wzcode;
    /**
     * 物资名称
     */
    private String wzname;
    /**
     * 型号规格
     */
    private String modelspcification;
    /**
     * 单位
     */
    private String unit;
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
     * 需求计划号
     */
    private String plancode;
    /**
     * 剩余量
     */
    private String synum;
    /**
     * 资金单位
     */
    private String zjname;
    /**
     * 资金编码
     */
    private String zjcode;
    /**
     * 标识
     */
    private String flag;

    public String getBuycode() {
        return buycode;
    }

    public void setBuycode(String buycode) {
        this.buycode = buycode == null ? null : buycode.trim();
    }

    public String getBuycodeid() {
        return buycodeid;
    }

    public void setBuycodeid(String buycodeid) {
        this.buycodeid = buycodeid == null ? null : buycodeid.trim();
    }

    public String getBuymoney() {
        return buymoney;
    }

    public void setBuymoney(String buymoney) {
        this.buymoney = buymoney == null ? null : buymoney.trim();
    }

    public String getBuyname() {
        return buyname;
    }

    public void setBuyname(String buyname) {
        this.buyname = buyname == null ? null : buyname.trim();
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

    public String getBuytype() {
        return buytype;
    }

    public void setBuytype(String buytype) {
        this.buytype = buytype == null ? null : buytype.trim();
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

    public String getSynum() {
        return synum;
    }

    public void setSynum(String synum) {
        this.synum = synum == null ? null : synum.trim();
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


}