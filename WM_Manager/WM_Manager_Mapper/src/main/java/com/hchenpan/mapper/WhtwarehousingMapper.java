package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Whtwarehousing;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface WhtwarehousingMapper extends BaseMapper<Whtwarehousing> {

    List<Map<String, Object>> selectWHlist(Map<String, Object> params);
}