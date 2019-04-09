package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.StockMapper;
import com.hchenpan.pojo.Applytransfer;
import com.hchenpan.pojo.Stock;
import com.hchenpan.pojo.Transferlist;
import com.hchenpan.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.StockServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("stockService")
public class StockServiceImpl extends BaseServiceImpl<StockMapper, Stock> implements StockService {
    private final StockMapper mapper;

    @Autowired
    public StockServiceImpl(StockMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public void Cutkcnumber(String dcckcode, String wzcode, String realnum) {

    }

    @Override
    public void Addkcnumber(Transferlist transferlist, Applytransfer old) {

    }
}