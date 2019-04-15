package com.hchenpan.model;

import com.hchenpan.common.BasePojo;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.model.DepartTree
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/4/15 09:04 下午
 **/
public class DepartTree extends BasePojo {
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
        return _parentId;
    }

    public void set_parentId(String _parentId) {
        this._parentId = _parentId;
    }
}