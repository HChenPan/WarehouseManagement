package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.SupplierMapper;
import com.hchenpan.pojo.Supplier;
import com.hchenpan.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.SupplierServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("supplierService")
public class SupplierServiceImpl extends BaseServiceImpl<SupplierMapper, Supplier> implements SupplierService {
    private final SupplierMapper mapper;

    @Autowired
    public SupplierServiceImpl(SupplierMapper mapper) {
        this.mapper = mapper;
    }


}