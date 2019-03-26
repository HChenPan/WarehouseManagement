package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Permission;
import org.springframework.stereotype.Component;

@Component
public interface PermissionMapper extends BaseMapper<Permission> {
    int deleteByPrimaryKey(String id);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}