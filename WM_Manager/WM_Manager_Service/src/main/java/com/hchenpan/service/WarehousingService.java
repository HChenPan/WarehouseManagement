package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.Warehousing;

import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.WarehousingService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface WarehousingService extends BaseService<Warehousing> {

    String createnotecode();

    List<Map<String, Object>> selectUserList(String id);

    String getrkstatus(String contractbasicid);

    Warehousing getbyrkcode(String notecode);
}
