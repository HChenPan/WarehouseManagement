package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.ContractGoodsMapper;
import com.hchenpan.pojo.ContractGoods;
import com.hchenpan.service.ContractGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.ContractGoodsServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("contractGoodsService")
public class ContractGoodsServiceImpl extends BaseServiceImpl<ContractGoodsMapper, ContractGoods> implements ContractGoodsService {
    private final ContractGoodsMapper mapper;

    @Autowired
    public ContractGoodsServiceImpl(ContractGoodsMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public Page<Map<String, Object>> selectContractPage(Page<Map<String, Object>> page, Map<String, Object> params) {
        page.setRecords(transformUpperCase(mapper.selectContractPage(page, params)));
        return page;
    }

    @Override
    public List<Map<String, Object>> selectallList() {
        return transformUpperCase(mapper.selectallList());
    }
}