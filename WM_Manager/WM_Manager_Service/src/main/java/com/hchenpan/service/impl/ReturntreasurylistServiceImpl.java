package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.ReturntreasurylistMapper;
import com.hchenpan.pojo.Returntreasurylist;
import com.hchenpan.service.ReturntreasurylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.ReturntreasurylistServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("returntreasurylistService")
public class ReturntreasurylistServiceImpl extends BaseServiceImpl<ReturntreasurylistMapper, Returntreasurylist> implements ReturntreasurylistService {
    private final ReturntreasurylistMapper mapper;

    @Autowired
    public ReturntreasurylistServiceImpl(ReturntreasurylistMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public Page<Map<String, Object>> selectMXPage(Page<Map<String, Object>> page, Map<String, Object> params) {
        page.setRecords(transformUpperCase(mapper.selectMXPage(page, params)));
        return page;
    }
}