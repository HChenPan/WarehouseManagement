package com.hchenpan.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.ContractTempContent;

import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service. ContractTempContentService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface ContractTempContentService extends BaseService<ContractTempContent> {

    Page<Map<String, Object>> selectPage(Page<Map<String, Object>> page, String tempnameId);


}
