package com.hchenpan.util;

import java.io.Serializable;
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.util.RedisPage
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/24 01:47 下午
 **/
public class RedisPage<T> implements Serializable {
    private List<T> records;
    private Integer total;
    private Integer size;
    private Integer current;
    private boolean searchCount;

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }
}