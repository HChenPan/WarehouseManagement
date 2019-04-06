package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_PROJECTNOMANAGE")
public class ProjectNoManage extends BasePojo {
    /**
     * 工程号编码
     */
    private String projectno;

    /**
     * 工程号名称
     */
    private String projectname;

    /**
     * 工程号创建人
     */
    private String createperson;


    /**
     * 工程号更新人
     */
    private String updateperson;

    /**
     * 逻辑存在标志
     */
    private String flag;

    /**
     * 备注
     */
    private String remark;

    public String getProjectno() {
        return projectno;
    }

    public void setProjectno(String projectno) {
        this.projectno = projectno;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }


    public String getCreateperson() {
        return createperson;
    }

    public void setCreateperson(String createperson) {
        this.createperson = createperson;
    }



    public String getUpdateperson() {
        return updateperson;
    }

    public void setUpdateperson(String updateperson) {
        this.updateperson = updateperson;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}