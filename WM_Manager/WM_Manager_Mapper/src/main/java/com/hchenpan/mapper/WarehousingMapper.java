package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Warehousing;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface WarehousingMapper extends BaseMapper<Warehousing> {

    List<Map<String, Object>> selectUserList(Map<String, Object> params);
}