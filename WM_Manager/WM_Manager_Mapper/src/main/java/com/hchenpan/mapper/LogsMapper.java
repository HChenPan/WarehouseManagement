package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Logs;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LogsMapper extends BaseMapper<Logs> {
    int deleteByPrimaryKey(String id);

    Logs selectByPrimaryKey(String id);

    List<Logs> selectAll();

    int updateByPrimaryKey(Logs record);
}