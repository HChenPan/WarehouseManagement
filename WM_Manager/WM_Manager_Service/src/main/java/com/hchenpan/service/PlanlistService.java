package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.Planlist;

import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.PlanlistService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface PlanlistService extends BaseService<Planlist> {


    List<Map<String, Object>> selectWZList();

    List<Planlist> selectWZ(String zjcode, String zjname, String code, String name);
}
