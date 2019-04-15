package com.hchenpan.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface EmployeeMapper extends BaseMapper<Employee> {

    List<Map<String, Object>> getlistPage(Page<Map<String, Object>> page, Map<String, Object> params);
}