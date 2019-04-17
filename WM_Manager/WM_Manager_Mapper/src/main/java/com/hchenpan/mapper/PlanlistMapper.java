package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Planlist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface PlanlistMapper extends BaseMapper<Planlist> {
    List<Map<String, Object>> selectWZList();


    List<Map<String, Object>> selectWZ(Map<String, Object> map);

    List<Map<String, Object>> selectSum(String wzcode);
}