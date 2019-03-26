package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Roles;
import org.springframework.stereotype.Component;
@Component
public interface RolesMapper extends BaseMapper<Roles> {
    int deleteByPrimaryKey(String id);

    int insertSelective(Roles record);

    Roles selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Roles record);

    int updateByPrimaryKey(Roles record);
}