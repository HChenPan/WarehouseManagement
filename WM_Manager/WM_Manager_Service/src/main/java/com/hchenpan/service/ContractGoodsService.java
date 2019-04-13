package com.hchenpan.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.ContractGoods;

import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.ContractGoodsService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface ContractGoodsService extends BaseService<ContractGoods> {

    Page<Map<String, Object>> selectContractPage(Page<Map<String, Object>> page, Map<String, Object> params);

    List<Map<String, Object>> selectallList();
}
