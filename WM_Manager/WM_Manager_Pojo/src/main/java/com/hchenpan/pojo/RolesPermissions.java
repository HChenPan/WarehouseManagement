package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;
/**
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_ROLES_PERMISSIONS")
public class RolesPermissions extends BasePojo {
    private String rid;

    private String pid;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid == null ? null : rid.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }
}