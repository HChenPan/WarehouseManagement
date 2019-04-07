package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;
/***
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_WAREHOUSENUM_USER")
public class WarehouseNumUser extends BasePojo {
    private String warehouseid;

    private String userid;

    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid == null ? null : warehouseid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
}