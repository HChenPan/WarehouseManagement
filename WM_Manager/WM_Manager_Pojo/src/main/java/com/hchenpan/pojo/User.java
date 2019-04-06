package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

import java.util.Objects;

/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_USER")
public class User extends BasePojo {

    private String username;

    private String password;

    private String realname;

    private String department;

    private String tel;

    private String issuper;

    private String lastlogintime;

    private String applogin;

    private String departmentid;

    private String state;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIssuper() {
        return issuper;
    }

    public void setIssuper(String issuper) {
        this.issuper = issuper;
    }

    public String getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(String lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getApplogin() {
        return applogin;
    }

    public void setApplogin(String applogin) {
        this.applogin = applogin;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(realname, user.realname) &&
                Objects.equals(department, user.department) &&
                Objects.equals(tel, user.tel) &&
                Objects.equals(issuper, user.issuper) &&
                Objects.equals(lastlogintime, user.lastlogintime) &&
                Objects.equals(applogin, user.applogin) &&
                Objects.equals(departmentid, user.departmentid) &&
                Objects.equals(state, user.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, realname, department, tel, issuper, lastlogintime, applogin, departmentid, state);
    }
}