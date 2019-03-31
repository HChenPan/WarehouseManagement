package com.hchenpan.model;

import java.util.Collections;
import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.model.PageContainer
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/31 11:30 上午
 **/
public class PageContainer<T> {
    private int total;

    protected List<T> rows = Collections.emptyList();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(final List<T> rows) {
        this.rows = rows;
    }
}