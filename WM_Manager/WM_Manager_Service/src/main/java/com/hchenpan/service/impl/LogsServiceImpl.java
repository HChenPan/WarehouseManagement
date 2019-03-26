package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.LogsMapper;
import com.hchenpan.mapper.UserMapper;
import com.hchenpan.pojo.Logs;
import com.hchenpan.pojo.User;
import com.hchenpan.service.LogsService;
import com.hchenpan.service.UserService;
import com.hchenpan.util.RedisPage;
import com.hchenpan.util.StringUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.UserServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:41 上午
 **/
@Service("logsService")
public class LogsServiceImpl extends BaseServiceImpl<LogsMapper, Logs> implements LogsService {
    private final LogsMapper logsMapper;

    @Autowired
    public LogsServiceImpl(LogsMapper logsMapper) {
        this.logsMapper = logsMapper;
    }

}