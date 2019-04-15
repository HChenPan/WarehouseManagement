package com.hchenpan.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.Returntreasurylist;

import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.ReturntreasurylistService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface ReturntreasurylistService extends BaseService<Returntreasurylist> {

    Page<Map<String, Object>> selectMXPage(Page<Map<String, Object>> page, Map<String, Object> params);
}
