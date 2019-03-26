package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_ROLES")
public class Roles extends BasePojo {


    private String name;

    private String description;

    private String type;

    private String indexorder;

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

    public String getIndexorder() {
        return indexorder;
    }

    public void setIndexorder(String indexorder) {
        this.indexorder = indexorder;
    }
}