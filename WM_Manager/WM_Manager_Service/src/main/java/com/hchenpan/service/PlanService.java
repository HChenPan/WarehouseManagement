package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.Plan;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.PlanService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface PlanService extends BaseService<Plan> {

    String createplancode(String plantype);
}
