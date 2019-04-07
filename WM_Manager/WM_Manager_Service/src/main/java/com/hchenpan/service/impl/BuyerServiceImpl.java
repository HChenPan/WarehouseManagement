package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.BuyerMapper;
import com.hchenpan.pojo.Buyer;
import com.hchenpan.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.BuyerServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("buyerService")
public class BuyerServiceImpl extends BaseServiceImpl<BuyerMapper, Buyer> implements BuyerService {
    private final BuyerMapper mapper;

    @Autowired
    public BuyerServiceImpl(BuyerMapper mapper) {
        this.mapper = mapper;
    }


}