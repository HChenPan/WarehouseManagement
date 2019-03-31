package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.DictionaryschildMapper;
import com.hchenpan.pojo.Dictionaryschild;
import com.hchenpan.service.DictionaryschildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.TestServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("dictionaryschildService")
public class DictionaryschildServiceImpl extends BaseServiceImpl<DictionaryschildMapper, Dictionaryschild> implements DictionaryschildService {
    private final DictionaryschildMapper mapper;

    @Autowired
    public DictionaryschildServiceImpl(DictionaryschildMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public Page<Map<String, Object>> getChildPage(Page<Map<String, Object>> page, Map<String, Object> params) {
        page.setRecords(mapper.getChildPage(page, params));
        return page;
    }

    @Override
    public List<Dictionaryschild> selectchildList(String id) {
        return mapper.selectchildList(id);
    }

    @Override
    public List<Dictionaryschild> getdchildlistbydecode(String dcode) {
        return mapper.selectList(new EntityWrapper<Dictionaryschild>().eq("flag", "E").eq("dcode", dcode).orderBy("code"));
    }
}