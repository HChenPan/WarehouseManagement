package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.Buy;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.BuyService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface BuyService extends BaseService<Buy> {

    String createbuycode(String buytype);
}
