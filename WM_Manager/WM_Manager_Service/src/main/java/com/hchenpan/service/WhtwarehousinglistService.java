package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.Whtwarehousinglist;

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.WhtwarehousinglistService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface WhtwarehousinglistService extends BaseService<Whtwarehousinglist> {

    int getkongnum(String rkcode);

    int getkongnum1(String rkcode);

    List<Whtwarehousinglist> getallrkmx(String rkcode);

    Whtwarehousinglist getallbyidzjx(String id);

    int getwznum(String wzcode,String flag,String rkcode);
}
