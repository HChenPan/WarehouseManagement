package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.EmployeeMapper;
import com.hchenpan.pojo.Employee;
import com.hchenpan.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.EmployeeServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("employeeService")
public class EmployeeServiceImpl extends BaseServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    private final EmployeeMapper mapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public Page<Map<String, Object>> getlistPage(Page<Map<String, Object>> page, Map<String, Object> params) {
        page.setRecords(transformUpperCase(mapper.getlistPage(page, params)));
        return page;
    }
}