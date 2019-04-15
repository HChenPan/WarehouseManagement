package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_RETURNTREASURYLIST")
public class Returntreasurylist extends BasePojo {
    //退库流水号
    private String tkcode;
    //入库流水号
    private String rkcode;
    //合同号
    private String contractbasicid;
    //采购计划号
    private String buycode;
    //需求计划号
    private String plancode;
    //物资编码
    private String wzcode;
    //物资名称
    private String wzname;
    //实际收货量
    private String sjnum;
    //合同采购量
    private String planbum;
    //单价 
    private String planprice;
    //实际入库总金额
    private String sjmoney;
    private String zjname;
    private String zjcode;
    //实际退货数量初始
    private String sjthslcs;
    //实际退货数量
    private String sjthsljs;
    //实际退货数量
    private String sycknum;
    //剩余退货数量
    private String sjthsl;
    //实际退货总金额
    private String sjthmoney;
    //单位
    private String unit;

    /*标识*/
    private String flag;
    /*仓库编码*/
    private String storehousecode;
    /*仓库名称*/
    private String storehousename;
    /*用于入库流水号传值*/
    @TableField(exist = false)
    private String notecode;


    public String getBuycode() {
        return buycode;
    }

    public void setBuycode(String buycode) {
        this.buycode = buycode == null ? null : buycode.trim();
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

    public String getRkcode() {
        return rkcode;
    }

    public void setRkcode(String rkcode) {
        this.rkcode = rkcode == null ? null : rkcode.trim();
    }

    public String getSjmoney() {
        return sjmoney;
    }

    public void setSjmoney(String sjmoney) {
        this.sjmoney = sjmoney == null ? null : sjmoney.trim();
    }

    public String getSjnum() {
        return sjnum;
    }

    public void setSjnum(String sjnum) {
        this.sjnum = sjnum == null ? null : sjnum.trim();
    }

    public String getSjthmoney() {
        return sjthmoney;
    }

    public void setSjthmoney(String sjthmoney) {
        this.sjthmoney = sjthmoney == null ? null : sjthmoney.trim();
    }

    public String getSjthsl() {
        return sjthsl;
    }

    public void setSjthsl(String sjthsl) {
        this.sjthsl = sjthsl == null ? null : sjthsl.trim();
    }

    public String getSjthslcs() {
        return sjthslcs;
    }

    public void setSjthslcs(String sjthslcs) {
        this.sjthslcs = sjthslcs == null ? null : sjthslcs.trim();
    }

    public String getSjthsljs() {
        return sjthsljs;
    }

    public void setSjthsljs(String sjthsljs) {
        this.sjthsljs = sjthsljs == null ? null : sjthsljs.trim();
    }

    public String getStorehousecode() {
        return storehousecode;
    }

    public void setStorehousecode(String storehousecode) {
        this.storehousecode = storehousecode == null ? null : storehousecode.trim();
    }

    public String getStorehousename() {
        return storehousename;
    }

    public void setStorehousename(String storehousename) {
        this.storehousename = storehousename == null ? null : storehousename.trim();
    }

    public String getSycknum() {
        return sycknum;
    }

    public void setSycknum(String sycknum) {
        this.sycknum = sycknum == null ? null : sycknum.trim();
    }

    public String getTkcode() {
        return tkcode;
    }

    public void setTkcode(String tkcode) {
        this.tkcode = tkcode == null ? null : tkcode.trim();
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



    public String getNotecode() {
        return notecode;
    }

    public void setNotecode(String notecode) {
        this.notecode = notecode;
    }
}