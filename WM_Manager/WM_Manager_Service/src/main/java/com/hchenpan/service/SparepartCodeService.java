package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.model.PageContainer;
import com.hchenpan.model.Spart;
import com.hchenpan.pojo.SparepartCode;

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.SparepartCodeService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface SparepartCodeService extends BaseService<SparepartCode> {


    PageContainer<Spart> getroot();

    List<Spart> getsons(Spart spart);

    String getexpandid(String parentid);

    List<SparepartCode> getallsparepart();
}
