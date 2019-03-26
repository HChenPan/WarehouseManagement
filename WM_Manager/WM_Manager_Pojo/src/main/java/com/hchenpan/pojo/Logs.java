package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_LOGS")
public class Logs extends BasePojo {
    private String description;

    private String flagid;

    private String ipaddress;

    private String name;

    private String oldcontent;

    private String optcontent;

    private String params;

    private String realname;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getFlagid() {
        return flagid;
    }

    public void setFlagid(String flagid) {
        this.flagid = flagid == null ? null : flagid.trim();
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress == null ? null : ipaddress.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOldcontent() {
        return oldcontent;
    }

    public void setOldcontent(String oldcontent) {
        this.oldcontent = oldcontent == null ? null : oldcontent.trim();
    }

    public String getOptcontent() {
        return optcontent;
    }

    public void setOptcontent(String optcontent) {
        this.optcontent = optcontent == null ? null : optcontent.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }
}