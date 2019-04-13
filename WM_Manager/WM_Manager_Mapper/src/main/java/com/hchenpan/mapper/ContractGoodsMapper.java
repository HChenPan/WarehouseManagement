package com.hchenpan.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.ContractGoods;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ContractGoodsMapper extends BaseMapper<ContractGoods> {

    List<Map<String, Object>> selectContractPage(Page<Map<String, Object>> page, Map<String, Object> params);

    List<Map<String, Object>> selectallList();
}