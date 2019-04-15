package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.CallslipGoods;

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.CallslipGoodsService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface CallslipGoodsService extends BaseService<CallslipGoods> {

    List<CallslipGoods> getwzlist(String callslipcode);

    int getwznum(String wzcode, String flag, String callslipcode, String comefrom, String plancode, String buycode, String contractbasicid, String rkcode);

    CallslipGoods getbycode(CallslipGoods callslipGoods);
}
