package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/****
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_APPROVALRECORD")
public class ApprovalRecord extends BasePojo {
    /**
     * 审批id
     */
    private String spid;

    /**
     * 审批类型编码
     */
    private String sptypecode;

    /**
     * 审批节点
     */
    private String spnodecode;

    /**
     * 审批意见
     */
    private String spadvice;

    /**
     * 审批结果
     */
    private String spresult;

    /**
     * 审批人
     */
    private String spuser;

    /**
     * 审批人id
     */
    private String spuserid;

    /**
     * 审批时间
     */
    private String sptime;


    public String getSpadvice() {
        return spadvice;
    }

    public void setSpadvice(String spadvice) {
        this.spadvice = spadvice == null ? null : spadvice.trim();
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid == null ? null : spid.trim();
    }

    public String getSpnodecode() {
        return spnodecode;
    }

    public void setSpnodecode(String spnodecode) {
        this.spnodecode = spnodecode == null ? null : spnodecode.trim();
    }

    public String getSpresult() {
        return spresult;
    }

    public void setSpresult(String spresult) {
        this.spresult = spresult == null ? null : spresult.trim();
    }

    public String getSptime() {
        return sptime;
    }

    public void setSptime(String sptime) {
        this.sptime = sptime == null ? null : sptime.trim();
    }

    public String getSptypecode() {
        return sptypecode;
    }

    public void setSptypecode(String sptypecode) {
        this.sptypecode = sptypecode == null ? null : sptypecode.trim();
    }

    public String getSpuser() {
        return spuser;
    }

    public void setSpuser(String spuser) {
        this.spuser = spuser == null ? null : spuser.trim();
    }

    public String getSpuserid() {
        return spuserid;
    }

    public void setSpuserid(String spuserid) {
        this.spuserid = spuserid == null ? null : spuserid.trim();
    }
}