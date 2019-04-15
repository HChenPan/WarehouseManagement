package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.CancellingstocksWZMapper;
import com.hchenpan.pojo.CancellingstocksWZ;
import com.hchenpan.service.CancellingstocksWZService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.CancellingstocksWZServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("cancellingstocksWZService")
public class CancellingstocksWZServiceImpl extends BaseServiceImpl<CancellingstocksWZMapper, CancellingstocksWZ> implements CancellingstocksWZService {
    private final CancellingstocksWZMapper mapper;

    @Autowired
    public CancellingstocksWZServiceImpl(CancellingstocksWZMapper mapper) {
        this.mapper = mapper;
    }


}