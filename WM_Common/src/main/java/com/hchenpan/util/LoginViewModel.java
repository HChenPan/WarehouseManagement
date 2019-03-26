package com.hchenpan.util;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.util.LoginViewModel
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/23 07:43 下午
 **/
public class LoginViewModel {
    @NotEmpty(message = "{loginviewmodel.email.length.error}")
    private String username;
    @NotEmpty(message = "{loginviewmodel.password.length.error}")
    private String password;
    private Boolean remember;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getRemember() {
        return remember == null ? false : true;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }
}