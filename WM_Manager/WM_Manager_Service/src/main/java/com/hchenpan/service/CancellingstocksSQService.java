package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.CancellingstocksSQ;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.CancellingstocksSQService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface CancellingstocksSQService extends BaseService<CancellingstocksSQ> {

    String createTKcode();

    CancellingstocksSQ getbycode(String tKcode);
}
