package com.hchenpan.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.WarehouseNum;

import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.WarehouseNumService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface WarehouseNumService extends BaseService<WarehouseNum> {

    Page<Map<String, Object>> getPage(Page<Map<String, Object>> page, Map<String, Object> params);

    List<Map<String, Object>> selectCKbyfhrid(String fhrid);
}
