package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.RolesMapper;
import com.hchenpan.pojo.Roles;
import com.hchenpan.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.TestServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("rolesService")
public class RolesServiceImpl extends BaseServiceImpl<RolesMapper, Roles> implements RolesService {
    private final RolesMapper mapper;

    @Autowired
    public RolesServiceImpl(RolesMapper mapper) {
        this.mapper = mapper;
    }


}