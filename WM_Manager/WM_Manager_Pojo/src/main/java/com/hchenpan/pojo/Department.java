package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;
/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_DEPARTMENT")
public class Department extends BasePojo {

    private String name;

    private String tel;

    private String deptnumber;

    private String parentid;

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

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
}