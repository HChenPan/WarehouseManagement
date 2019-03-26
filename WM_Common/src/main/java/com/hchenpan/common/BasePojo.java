package com.hchenpan.common;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.common.BaseModel
 * Description : pojo层继承本类 获取 id creatorId creator createTime updaterId updater updateTime
 * public class **** extends BasePojo
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/4 02:06 下午
 **/
public class BasePojo implements Serializable {
    private static final long serialVersionUID = -2717454989585121963L;
    @TableId(value = "ID", type = IdType.INPUT)
    private String id;
    @TableField(fill = FieldFill.INSERT)
    private String creatorid;
    @TableField(fill = FieldFill.INSERT)
    private String creator;
    @TableField(fill = FieldFill.INSERT)
    private String createtime;
    @TableField(fill = FieldFill.UPDATE)
    private String updaterid;
    @TableField(fill = FieldFill.UPDATE)
    private String updater;
    @TableField(fill = FieldFill.UPDATE)
    private String updatetime;

    public BasePojo() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdaterid() {
        return updaterid;
    }

    public void setUpdaterid(String updaterid) {
        this.updaterid = updaterid;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}