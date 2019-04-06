package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.SptypespLevelUserMapper;
import com.hchenpan.pojo.SptypespLevelUser;
import com.hchenpan.service.SptypespLevelUserService;
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
@Service("sptypespLevelUserService")
public class SptypespLevelUserServiceImpl extends BaseServiceImpl<SptypespLevelUserMapper, SptypespLevelUser> implements SptypespLevelUserService {
    private final SptypespLevelUserMapper sptypespLevelUserMapper;

    @Autowired
    public SptypespLevelUserServiceImpl(SptypespLevelUserMapper sptypespLevelUserMapper) {
        this.sptypespLevelUserMapper = sptypespLevelUserMapper;
    }


}