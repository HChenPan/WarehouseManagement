package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_USER")
public class User extends BasePojo {
    /**
     * 最后登录时间
     */
    private String lastlogintime;

    /**
     * 用于判断是否APP端登录
     */
    private String applogin;


    private String departmentid;

    private String state;

    /**
     * 用户名
     */
    private String username;

    /**
     * 口令
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 归属部门
     */
    @TableField(exist = false)
    private Department depart;

    private String department;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 判断是否超级管理员
     */
    private String issuper;

    /**
     * 对应角色列表
     */
    @TableField(exist = false)
    private List<Roles> roles;

    /**
     * 用于密码修改的临时口令
     */
    @TableField(exist = false)
    private String password1;

    /**
     * 用于密码修改的临时口令
     */
    @TableField(exist = false)
    private String password2;

    /**
     * 用于传值的角色列表的主键列表
     */
    @TableField(exist = false)
    private List<String> rid;

    /**
     * 对应权限键值集合
     */
    @TableField(exist = false)
    private Set<String> permissions;

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

    public Department getDepart() {
        return depart;
    }

    public void setDepart(Department depart) {
        this.depart = depart;
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

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public List<String> getRid() {
        return rid;
    }

    public void setRid(List<String> rid) {
        this.rid = rid;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(lastlogintime, user.lastlogintime) &&
                Objects.equals(applogin, user.applogin) &&
                Objects.equals(departmentid, user.departmentid) &&
                Objects.equals(state, user.state) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(realname, user.realname) &&
                Objects.equals(depart, user.depart) &&
                Objects.equals(department, user.department) &&
                Objects.equals(tel, user.tel) &&
                Objects.equals(issuper, user.issuper) &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(password1, user.password1) &&
                Objects.equals(password2, user.password2) &&
                Objects.equals(rid, user.rid) &&
                Objects.equals(permissions, user.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastlogintime, applogin, departmentid, state, username, password, realname, depart, department, tel, issuper, roles, password1, password2, rid, permissions);
    }
}