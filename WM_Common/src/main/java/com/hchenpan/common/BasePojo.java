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
    private String creatorId;
    @TableField(fill = FieldFill.INSERT)
    private String creator;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.UPDATE)
    private String updaterId;
    @TableField(fill = FieldFill.UPDATE)
    private String updater;
    @TableField(fill = FieldFill.UPDATE)
    private String updateTime;

    public BasePojo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getCreateTimes() {
        return getCreateTime();
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getUpdateTimes() {
        return getUpdateTime();
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}