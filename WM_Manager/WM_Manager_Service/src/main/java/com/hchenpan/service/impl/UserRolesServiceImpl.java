package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.UserRolesMapper;
import com.hchenpan.pojo.UserRoles;
import com.hchenpan.service.UserRolesService;
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
@Service("userrolesService")
public class UserRolesServiceImpl extends BaseServiceImpl<UserRolesMapper, UserRoles> implements UserRolesService {
    private final UserRolesMapper mapper;

    @Autowired
    public UserRolesServiceImpl(UserRolesMapper mapper) {
        this.mapper = mapper;
    }


}