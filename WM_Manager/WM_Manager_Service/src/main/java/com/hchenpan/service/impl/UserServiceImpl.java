package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.UserMapper;
import com.hchenpan.model.CommboxList;
import com.hchenpan.pojo.User;
import com.hchenpan.service.UserService;
import com.hchenpan.util.RedisPage;
import com.hchenpan.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.UserServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:41 上午
 **/
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int login(User user) {
        return userMapper.selectCount(new EntityWrapper<User>().eq("username", user.getUsername()).eq("password", user.getPassword()));
    }

    @Override
    public User getlogin(User user) {
        User u = new User();
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        return userMapper.selectOne(u);
    }

    @Override
    public RedisPage<User> selectPageRedis(int current, int size, String orderByField, boolean isAsc, User user) {

        EntityWrapper<User> ew = new EntityWrapper<>();
        // 添加创建时间区间条件createTime between (startTime, endTime)
//        ew.between("createTime", startTime, endTime)
        if (StringUtil.notEmpty(user.getRealname())) {
            ew.like("realname", user.getRealname(), SqlLike.DEFAULT);
        }
        Page<User> userPage1 = selectPage(new Page<>(current, size, orderByField, isAsc), ew);
        RedisPage<User> userPage = new RedisPage<>();
        userPage.setTotal(Math.toIntExact(userPage1.getTotal()));
        userPage.setRecords(userPage1.getRecords());
        userPage.setSize(userPage1.getSize());
        return userPage;
    }

    @Override
    public List<CommboxList> getdeptuserlist() {
        return userMapper.getdeptuserlist();
    }


}