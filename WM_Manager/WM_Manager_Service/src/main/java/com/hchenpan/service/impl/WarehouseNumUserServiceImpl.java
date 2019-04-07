package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.WarehouseNumUserMapper;
import com.hchenpan.pojo.WarehouseNumUser;
import com.hchenpan.service.WarehouseNumUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.WarehouseNumUserServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("warehouseNumUserService")
public class WarehouseNumUserServiceImpl extends BaseServiceImpl<WarehouseNumUserMapper, WarehouseNumUser> implements WarehouseNumUserService {
    private final WarehouseNumUserMapper mapper;

    @Autowired
    public WarehouseNumUserServiceImpl(WarehouseNumUserMapper mapper) {
        this.mapper = mapper;
    }


}