package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.ContractTempContentMapper;
import com.hchenpan.pojo.ContractTempContent;
import com.hchenpan.service.ContractTempContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl. ContractTempContentServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("contractTempContentService")
public class ContractTempContentServiceImpl extends BaseServiceImpl<ContractTempContentMapper, ContractTempContent> implements ContractTempContentService {
    private final ContractTempContentMapper mapper;

    @Autowired
    public ContractTempContentServiceImpl(ContractTempContentMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public Page<Map<String, Object>> selectPage(Page<Map<String, Object>> page, String tempnameId) {

        page.setRecords(transformUpperCase(mapper.selectPage(page, tempnameId)));
        return page;
    }

}