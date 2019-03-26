package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Department;
import org.springframework.stereotype.Component;

@Component
public interface DepartmentMapper extends BaseMapper<Department> {
    int deleteByPrimaryKey(String id);

    int insertSelective(Department record);

    Department selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
}