package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

import java.util.List;

/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_ROLES")
public class Roles extends BasePojo {


    /**
     * 角色名称
     */
    private String name;

    /**
     * 功能描述
     */
    private String description;

    /**
     * 角色类别
     */
    private String type;

    /**
     * 排列顺序
     */
    private int indexorder;

    /**
     * 对应权限列表
     */
    @TableField(exist = false)
    private List<Permission> permissions;
    /**
     * 对应权限列表
     */
    @TableField(exist = false)
    private String rid;

    /**
     * 用于传值的权限主键列表
     */
    @TableField(exist = false)
    private List<String> pid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIndexorder() {
        return indexorder;
    }

    public void setIndexorder(int indexorder) {
        this.indexorder = indexorder;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<String> getPid() {
        return pid;
    }

    public void setPid(List<String> pid) {
        this.pid = pid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}