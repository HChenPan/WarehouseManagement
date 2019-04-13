package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;
/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_CONTRACTTERMS")
public class ContractTerms extends BasePojo {
    /**
     * 合同流水号
     */
    private String contractbasicid;

    /**
     * 条款序号
     */
    private String sn;

    /**
     * 条款内容
     */
    private String content;

    /**
     * 标识
     */
    private String flag;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }
}