package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.model.CommboxList;
import com.hchenpan.pojo.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper extends BaseMapper<User> {


    List<CommboxList> getdeptuserlist();
}