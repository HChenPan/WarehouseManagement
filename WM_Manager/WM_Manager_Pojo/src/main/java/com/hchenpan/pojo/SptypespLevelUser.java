package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;
/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_SPTYPESPLEVEL_USER")
public class SptypespLevelUser extends BasePojo {
    private String sptypesplevelid;

    private String userid;

    public String getSptypesplevelid() {
        return sptypesplevelid;
    }

    public void setSptypesplevelid(String sptypesplevelid) {
        this.sptypesplevelid = sptypesplevelid == null ? null : sptypesplevelid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
}