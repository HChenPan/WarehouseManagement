package com.hchenpan.mapper;


import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Dictionaryschild;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DictionaryschildMapper extends BaseMapper<Dictionaryschild> {

    List<Map<String, Object>> getChildPage(Page<Map<String, Object>> page, Map<String, Object> params);

    List<Dictionaryschild> selectchildList(String id);
}