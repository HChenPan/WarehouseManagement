package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.TransferlistMapper;
import com.hchenpan.pojo.Transferlist;
import com.hchenpan.service.TransferlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.TransferlistServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("transferlistService")
public class TransferlistServiceImpl extends BaseServiceImpl<TransferlistMapper, Transferlist> implements TransferlistService {
    private final TransferlistMapper mapper;

    @Autowired
    public TransferlistServiceImpl(TransferlistMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public void updateMXById(String id, String applytransfercode, String sbunit, String sbunitid) {
        mapper.updateMXById(id, applytransfercode, sbunit, sbunitid);
    }
}