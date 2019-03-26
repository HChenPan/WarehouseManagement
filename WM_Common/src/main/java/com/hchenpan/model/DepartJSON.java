package com.hchenpan.model;

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.model.DepartJSON
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/24 05:02 下午
 **/
public class DepartJSON {
    /*主键*/
    private String id;

    /*显示文字*/
    private String text;

    /*折叠状态*/
    private String state;

    /*子节点列表*/
    private List<DepartJSON> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DepartJSON> getChildren() {
        return children;
    }

    public void setChildren(List<DepartJSON> children) {
        this.children = children;
    }
}