package com.hchenpan.service;

import com.hchenpan.pojo.Test;
import com.hchenpan.util.DataPage;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.TestService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:05 下午
 **/
public interface TestService {


    boolean insertTest(Test test);

    DataPage<Test> selectPage(Integer pageNum, Integer pageSize, Integer indexCount);
}
