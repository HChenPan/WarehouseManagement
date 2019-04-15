package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.RolesPermissionsMapper;
import com.hchenpan.pojo.RolesPermissions;
import com.hchenpan.service.RolesPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.RolesPermissionsServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("rolesPermissionsService")
public class RolesPermissionsServiceImpl extends BaseServiceImpl<RolesPermissionsMapper, RolesPermissions> implements RolesPermissionsService {
    private final RolesPermissionsMapper mapper;

    @Autowired
    public RolesPermissionsServiceImpl(RolesPermissionsMapper mapper) {
        this.mapper = mapper;
    }


}