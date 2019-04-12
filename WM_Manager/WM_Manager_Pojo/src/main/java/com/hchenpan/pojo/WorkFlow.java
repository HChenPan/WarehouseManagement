package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

/*****
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_WORKFLOW")
public class WorkFlow extends BasePojo {
    /**
     * 审批类型编码
     */
    private String sptypecode;

    /**
     * 审批类型名称
     */
    @TableField(exist = false)
    private String sptypename;

    /**
     * 当前审批节点
     */
    private String spnode;

    /**
     * 当前审批节点
     */
    @TableField(exist = false)
    private String spnodename;

    /**
     * 前进节点
     */
    private String nextnode;

    /**
     * 前进节点名称
     */
    @TableField(exist = false)
    private String nextnodename;

    /**
     * 后退节点
     */
    private String backnode;

    /**
     * 后退节点名称
     */
    @TableField(exist = false)
    private String backnodename;


    /**
     * 审批金额下限
     */
    private String spmoneylowlimit;

    /**
     * 审批金额上限
     */
    private String spmoneyuplimit;


    public String getBacknode() {
        return backnode;
    }

    public void setBacknode(String backnode) {
        this.backnode = backnode == null ? null : backnode.trim();
    }

    public String getNextnode() {
        return nextnode;
    }

    public void setNextnode(String nextnode) {
        this.nextnode = nextnode == null ? null : nextnode.trim();
    }

    public String getSpmoneylowlimit() {
        return spmoneylowlimit;
    }

    public void setSpmoneylowlimit(String spmoneylowlimit) {
        this.spmoneylowlimit = spmoneylowlimit == null ? null : spmoneylowlimit.trim();
    }

    public String getSpmoneyuplimit() {
        return spmoneyuplimit;
    }

    public void setSpmoneyuplimit(String spmoneyuplimit) {
        this.spmoneyuplimit = spmoneyuplimit == null ? null : spmoneyuplimit.trim();
    }

    public String getSpnode() {
        return spnode;
    }

    public void setSpnode(String spnode) {
        this.spnode = spnode == null ? null : spnode.trim();
    }

    public String getSptypecode() {
        return sptypecode;
    }

    public void setSptypecode(String sptypecode) {
        this.sptypecode = sptypecode == null ? null : sptypecode.trim();
    }

    public String getSptypename() {
        return sptypename;
    }

    public void setSptypename(String sptypename) {
        this.sptypename = sptypename;
    }

    public String getSpnodename() {
        return spnodename;
    }

    public void setSpnodename(String spnodename) {
        this.spnodename = spnodename;
    }

    public String getNextnodename() {
        return nextnodename;
    }

    public void setNextnodename(String nextnodename) {
        this.nextnodename = nextnodename;
    }

    public String getBacknodename() {
        return backnodename;
    }

    public void setBacknodename(String backnodename) {
        this.backnodename = backnodename;
    }
}