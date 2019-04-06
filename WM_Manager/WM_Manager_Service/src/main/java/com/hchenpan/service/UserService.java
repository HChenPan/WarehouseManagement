package com.hchenpan.service;

import com.hchenpan.common.BaseService;
import com.hchenpan.model.CommboxList;
import com.hchenpan.pojo.User;
import com.hchenpan.util.RedisPage;

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
public interface UserService extends BaseService<User> {
    int login(User user);

    User getlogin(User user);


    RedisPage<User> selectPageRedis(int current, int size, String orderByField, boolean isAsc, User user);


    List<CommboxList> getdeptuserlist();
}
