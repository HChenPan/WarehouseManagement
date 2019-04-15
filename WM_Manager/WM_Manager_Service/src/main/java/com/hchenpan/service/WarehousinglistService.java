package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.Warehousinglist;

import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.WarehousinglistService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface WarehousinglistService extends BaseService<Warehousinglist> {

    int getkongnum(String rkcode);

    int getkongnum1(String rkcode);

    List<Warehousinglist> getallrkmx(String rkcode);

    int getwznum(String wzcode, String contractbasicid, String buycode, String plancode, String flag, String rkcode);

    List<Map<String, Object>> selectWZList(Warehousinglist warehousinglist);

    Warehousinglist getgoods(String plancode,String buycode,String contractbasicid,String rkcode,String wzcode) ;
}
