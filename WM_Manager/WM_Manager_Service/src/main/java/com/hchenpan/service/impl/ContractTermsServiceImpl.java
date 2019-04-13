package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.ContractTermsMapper;
import com.hchenpan.pojo.ContractTerms;
import com.hchenpan.service.ContractTermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.ContractTermsServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("contractTermsService")
public class ContractTermsServiceImpl extends BaseServiceImpl<ContractTermsMapper, ContractTerms> implements ContractTermsService {
    private final ContractTermsMapper mapper;

    @Autowired
    public ContractTermsServiceImpl(ContractTermsMapper mapper) {
        this.mapper = mapper;
    }


}