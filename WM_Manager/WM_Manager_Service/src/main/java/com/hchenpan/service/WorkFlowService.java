package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.WorkFlow;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.WorkFlowService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface WorkFlowService extends BaseService<WorkFlow> {

    String getnextspnode(String sptypecode, String spnode, String money, String spresult);
}
