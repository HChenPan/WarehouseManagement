package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_BUYER")
public class Buyer extends BasePojo {
    /**
     * 供应商代码
     */
    private String buyercode;

    /**
     * 供应商名称
     */
    private String buyername;

    /**
     * 住所
     */
    private String address;

    /**
     * 法定代表人
     */
    private String legalrepresentative;

    /**
     * 电话
     */
    private String phone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 开户行
     */
    private String bank;

    /**
     * 银行账号
     */
    private String account;

    /**
     * 邮政编码
     */
    private String postcode;

    /**
     * 税务证号
     */
    private String taxid;

    /**
     * 供货范围
     */
    private String supplyscope;

    /**
     * 注册资金
     */
    private String registeredcapital;

    /**
     * 备注
     */
    private String remark;


    /**
     * 状态标志
     */
    private String flag;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getBuyercode() {
        return buyercode;
    }

    public void setBuyercode(String buyercode) {
        this.buyercode = buyercode == null ? null : buyercode.trim();
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername == null ? null : buyername.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getLegalrepresentative() {
        return legalrepresentative;
    }

    public void setLegalrepresentative(String legalrepresentative) {
        this.legalrepresentative = legalrepresentative == null ? null : legalrepresentative.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getRegisteredcapital() {
        return registeredcapital;
    }

    public void setRegisteredcapital(String registeredcapital) {
        this.registeredcapital = registeredcapital == null ? null : registeredcapital.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSupplyscope() {
        return supplyscope;
    }

    public void setSupplyscope(String supplyscope) {
        this.supplyscope = supplyscope == null ? null : supplyscope.trim();
    }

    public String getTaxid() {
        return taxid;
    }

    public void setTaxid(String taxid) {
        this.taxid = taxid == null ? null : taxid.trim();
    }
}