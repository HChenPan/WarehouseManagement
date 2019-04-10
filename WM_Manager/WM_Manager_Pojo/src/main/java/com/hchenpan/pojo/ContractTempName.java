package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;
/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_CONTRACTTEMPNAME")
public class ContractTempName extends BasePojo {
    /**
     * 合同类别名称
     */
    private String contractempname;

    /**
     * 合同描述
     */
    private String introduce;

    /**
     * 创建人id
     **/
    private String createuserid;
    private String flag;


    public String getContractempname() {
        return contractempname;
    }

    public void setContractempname(String contractempname) {
        this.contractempname = contractempname == null ? null : contractempname.trim();
    }

    public String getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(String createuserid) {
        this.createuserid = createuserid == null ? null : createuserid.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

}