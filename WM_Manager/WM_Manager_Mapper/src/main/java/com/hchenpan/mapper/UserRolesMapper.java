package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.UserRoles;
import org.springframework.stereotype.Component;

@Component
public interface UserRolesMapper extends BaseMapper<UserRoles> {
    int deleteByPrimaryKey(String id);

    int insertSelective(UserRoles record);

    UserRoles selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserRoles record);

    int updateByPrimaryKey(UserRoles record);
}