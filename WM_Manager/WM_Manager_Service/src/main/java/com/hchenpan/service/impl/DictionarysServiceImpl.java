package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.DictionarysMapper;
import com.hchenpan.mapper.DictionaryschildMapper;
import com.hchenpan.pojo.Dictionarys;
import com.hchenpan.pojo.Dictionaryschild;
import com.hchenpan.service.DictionarysService;
import com.hchenpan.service.DictionaryschildService;
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
@Service("dictionarysService")
public class DictionarysServiceImpl extends BaseServiceImpl<DictionarysMapper, Dictionarys> implements DictionarysService {
    private final DictionarysMapper mapper;

    @Autowired
    public DictionarysServiceImpl(DictionarysMapper mapper) {
        this.mapper = mapper;
    }


}