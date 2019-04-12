package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.WzQxMapper;
import com.hchenpan.pojo.WzQx;
import com.hchenpan.service.WzQxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.WzQxServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("wzQxService")
public class WzQxServiceImpl extends BaseServiceImpl<WzQxMapper, WzQx> implements WzQxService {
    private final WzQxMapper mapper;

    @Autowired
    public WzQxServiceImpl(WzQxMapper mapper) {
        this.mapper = mapper;
    }


}