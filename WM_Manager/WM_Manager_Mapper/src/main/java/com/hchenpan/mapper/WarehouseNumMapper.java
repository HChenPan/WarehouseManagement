package com.hchenpan.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.WarehouseNum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface WarehouseNumMapper extends BaseMapper<WarehouseNum> {

    List<Map<String, Object>> getPage(Page<Map<String, Object>> page, Map<String, Object> params);

    List<Map<String, Object>> selectCKbyfhrid(String fhrid);
}