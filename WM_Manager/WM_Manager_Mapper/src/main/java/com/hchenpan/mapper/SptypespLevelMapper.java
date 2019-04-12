package com.hchenpan.mapper;


import com.hchenpan.common.BaseMapper;
import com.hchenpan.model.CommboxList;
import com.hchenpan.pojo.SptypespLevel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface SptypespLevelMapper extends BaseMapper<SptypespLevel> {


    List<CommboxList> getusersplevelnode(Map<String, Object> params);
}