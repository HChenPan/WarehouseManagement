package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Warehousinglist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface WarehousinglistMapper extends BaseMapper<Warehousinglist> {

    List<Map<String, Object>> selectWZList(Map<String, Object> params);
}