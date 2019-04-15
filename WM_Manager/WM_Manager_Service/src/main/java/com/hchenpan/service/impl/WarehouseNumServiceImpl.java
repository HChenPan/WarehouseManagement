package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.WarehouseNumMapper;
import com.hchenpan.pojo.WarehouseNum;
import com.hchenpan.service.WarehouseNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.TestServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("warehouseNumService")
public class WarehouseNumServiceImpl extends BaseServiceImpl<WarehouseNumMapper, WarehouseNum> implements WarehouseNumService {
    private final WarehouseNumMapper mapper;

    @Autowired
    public WarehouseNumServiceImpl(WarehouseNumMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public Page<Map<String, Object>> getPage(Page<Map<String, Object>> page, Map<String, Object> params) {
        page.setRecords(mapper.getPage(page, params));
        return page;
    }

    @Override
    public List<Map<String, Object>> selectCKbyfhrid(String fhrid) {


        return transformUpperCase(mapper.selectCKbyfhrid(fhrid));
    }
}