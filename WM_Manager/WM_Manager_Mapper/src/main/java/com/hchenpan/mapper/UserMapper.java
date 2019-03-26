package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends BaseMapper<User> {
    int deleteByPrimaryKey(String id);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}