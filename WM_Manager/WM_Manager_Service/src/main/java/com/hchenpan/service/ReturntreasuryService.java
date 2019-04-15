package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.Returntreasury;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.ReturntreasuryService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface ReturntreasuryService extends BaseService<Returntreasury> {

    String createtkcode();

    String gettkstatus(String tkcode);
}
