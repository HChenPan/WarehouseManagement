package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.ContractTempNameMapper;
import com.hchenpan.pojo.ContractTempName;
import com.hchenpan.service.ContractTempNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.ContractTempNameServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("contractTempNameService")
public class ContractTempNameServiceImpl extends BaseServiceImpl<ContractTempNameMapper, ContractTempName> implements ContractTempNameService {
    private final ContractTempNameMapper mapper;

    @Autowired
    public ContractTempNameServiceImpl(ContractTempNameMapper mapper) {
        this.mapper = mapper;
    }


}