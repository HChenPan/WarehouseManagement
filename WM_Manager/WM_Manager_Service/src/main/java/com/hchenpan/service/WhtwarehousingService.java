package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.Whtwarehousing;

import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.WhtwarehousingService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface WhtwarehousingService extends BaseService<Whtwarehousing> {

    String createnotecode();

    List<Map<String, Object>>  selectWHlist(String userid);

    String getrkstatus(String rkcode);

    Whtwarehousing getbyrkcode(String notecode);
}
