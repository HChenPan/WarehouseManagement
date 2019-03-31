package com.hchenpan.model;

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.model.Spart
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/31 11:29 上午
 **/
public class Spart {
    /*主键*/
    private String id;
    private String name;
    /*显示文字*/
    private String code;

    private String description;
    private String parentcode;
    private String parentid;

    /*折叠状态*/
    private String state;

    /*子节点列表*/
    private List<Spart> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentcode() {
        return parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Spart> getChildren() {
        return children;
    }

    public void setChildren(List<Spart> children) {
        this.children = children;
    }
}