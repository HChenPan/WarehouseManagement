package com.hchenpan.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Returntreasurylist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ReturntreasurylistMapper extends BaseMapper<Returntreasurylist> {

    List<Map<String, Object>> selectMXPage(Page<Map<String, Object>> page, Map<String, Object> params);
}