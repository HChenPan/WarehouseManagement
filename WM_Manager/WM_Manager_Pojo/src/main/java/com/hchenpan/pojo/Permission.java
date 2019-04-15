package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_PERMISSION")
public class Permission extends BasePojo {

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限键值
     */
    private String nameValue;

    /**
     * 权限操作描述
     */
    private String description;

    /**
     * 所属模块
     */
    private String modular;

    /**
     * 排列顺序
     */
    private int indexorder;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameValue() {
        return nameValue;
    }

    public void setNameValue(String nameValue) {
        this.nameValue = nameValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModular() {
        return modular;
    }

    public void setModular(String modular) {
        this.modular = modular;
    }

    public int getIndexorder() {
        return indexorder;
    }

    public void setIndexorder(int indexorder) {
        this.indexorder = indexorder;
    }
}