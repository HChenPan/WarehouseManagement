package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.BuylistMapper;
import com.hchenpan.pojo.Buylist;
import com.hchenpan.service.BuylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.BuylistServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/4/11 12:33 下午
 **/
@Service("buylistService")
public class BuylistServiceImpl extends BaseServiceImpl<BuylistMapper, Buylist> implements BuylistService {
    private final BuylistMapper mapper;

    @Autowired
    public BuylistServiceImpl(BuylistMapper mapper) {
        this.mapper = mapper;
    }
}