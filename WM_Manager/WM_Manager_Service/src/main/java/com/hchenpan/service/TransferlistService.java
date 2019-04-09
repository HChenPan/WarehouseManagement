package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.Transferlist;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.TransferlistService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface TransferlistService extends BaseService<Transferlist> {

    void updateMXById(String id, String applytransfercode, String sbunit, String sbunitid);
}
