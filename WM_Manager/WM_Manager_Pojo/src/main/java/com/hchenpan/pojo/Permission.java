package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_PERMISSION")
public class Permission extends BasePojo {


    private String name;

    private String namevalue;

    private String description;

    private String modular;

    private String indexorder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamevalue() {
        return namevalue;
    }

    public void setNamevalue(String namevalue) {
        this.namevalue = namevalue;
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

    public String getIndexorder() {
        return indexorder;
    }

    public void setIndexorder(String indexorder) {
        this.indexorder = indexorder;
    }
}