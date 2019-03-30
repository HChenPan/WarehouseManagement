package com.hchenpan.model;

import java.io.Serializable;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.model.ChildDictionaryVO
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/29 12:06 下午
 **/
public class ChildDictionaryVO implements Serializable {

    private String id;
    private String dcode;
    private String dname;
    private String note;
    private String code;
    private String name;
    private String updatetime;
    private String rownumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDcode() {
        return dcode;
    }

    public void setDcode(String dcode) {
        this.dcode = dcode;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getRownumber() {
        return rownumber;
    }

    public void setRownumber(String rownumber) {
        this.rownumber = rownumber;
    }
}