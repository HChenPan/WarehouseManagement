package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.DictionaryschildMapper;
import com.hchenpan.mapper.RolesMapper;
import com.hchenpan.pojo.Dictionaryschild;
import com.hchenpan.pojo.Roles;
import com.hchenpan.service.DictionaryschildService;
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
@Service("dictionaryschildService")
public class DictionaryschildServiceImpl extends BaseServiceImpl<DictionaryschildMapper, Dictionaryschild> implements DictionaryschildService {
    private final DictionaryschildMapper mapper;

    @Autowired
    public DictionaryschildServiceImpl(DictionaryschildMapper mapper) {
        this.mapper = mapper;
    }


}