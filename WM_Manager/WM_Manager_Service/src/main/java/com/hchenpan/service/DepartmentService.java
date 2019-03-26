package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.model.DepartJSON;
import com.hchenpan.pojo.Department;

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.UserService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface DepartmentService extends BaseService<Department> {

    List<DepartJSON> getrootlist();

    List<DepartJSON> getsonlist(DepartJSON d);
}
