package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_CONTRACTTEMPCONTENT")
public class ContractTempContent extends BasePojo {
    private String content;

    private String flag;
    /**
     * 条款序号
     */
    private String sn;

    @TableField("tempname_Id")
    private String tempnameId;
    /**
     * 归属模板名称
     */
    @TableField(exist = false)
    private ContractTempName tempname;
    /**
     * 模板名称
     */
    @TableField(exist = false)
    private String realtempname;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public String getTempnameId() {
        return tempnameId;
    }

    public void setTempnameId(String tempnameId) {
        this.tempnameId = tempnameId == null ? null : tempnameId.trim();
    }

    public ContractTempName getTempname() {
        return tempname;
    }

    public void setTempname(ContractTempName tempname) {
        this.tempname = tempname;
    }

    public String getRealtempname() {
        return realtempname;
    }

    public void setRealtempname(String realtempname) {
        this.realtempname = realtempname;
    }
}