package com.hchenpan.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.hchenpan.common.BasePojo;

import java.util.Set;

/****
 * @author hchenpan
 * @version 1.0
 */
@TableName("WM_WZQX")
public class WzQx extends BasePojo {
    /**
     * 物资编码前缀
     */
    private String wzqz;

    //操作人id
    private String czr;

    //操作人中文
    private String czrzw;


    /**
     * 审批人
     */
    @TableField(exist = false)
    private Set<User> spusers;

    private String flag;

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr == null ? null : czr.trim();
    }

    public String getCzrzw() {
        return czrzw;
    }

    public void setCzrzw(String czrzw) {
        this.czrzw = czrzw == null ? null : czrzw.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getWzqz() {
        return wzqz;
    }

    public void setWzqz(String wzqz) {
        this.wzqz = wzqz == null ? null : wzqz.trim();
    }

    public Set<User> getSpusers() {
        return spusers;
    }

    public void setSpusers(Set<User> spusers) {
        this.spusers = spusers;
    }
}