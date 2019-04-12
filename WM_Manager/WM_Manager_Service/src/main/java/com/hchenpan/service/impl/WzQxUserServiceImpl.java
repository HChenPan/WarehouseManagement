package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.WzQxUserMapper;
import com.hchenpan.pojo.WzQxUser;
import com.hchenpan.service.WzQxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.WzQxUserServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("wzQxUserService")
public class WzQxUserServiceImpl extends BaseServiceImpl<WzQxUserMapper, WzQxUser> implements WzQxUserService {
    private final WzQxUserMapper mapper;

    @Autowired
    public WzQxUserServiceImpl(WzQxUserMapper mapper) {
        this.mapper = mapper;
    }


}