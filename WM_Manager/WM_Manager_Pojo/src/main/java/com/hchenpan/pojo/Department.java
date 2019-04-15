package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_DEPARTMENT")
public class Department extends BasePojo {
    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门办公电话
     */
    private String tel;

    /**
     * 部门编号
     */
    private String deptnumber;

    /**
     * 上级部门
     */
    private String parentid;


    @TableField(exist = false)
    private String _parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDeptnumber() {
        return deptnumber;
    }

    public void setDeptnumber(String deptnumber) {
        this.deptnumber = deptnumber;
    }

    public String get_parentId() {
        return parentid;
    }

    public void set_parentId(String _parentId) {
        this._parentId = this.parentid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
}