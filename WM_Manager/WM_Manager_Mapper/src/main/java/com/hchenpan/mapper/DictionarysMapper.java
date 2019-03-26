package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Dictionarys;
import com.hchenpan.pojo.Dictionaryschild;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DictionarysMapper extends BaseMapper<Dictionarys> {
    int deleteByPrimaryKey(String id);

    Dictionarys selectByPrimaryKey(String id);

    List<Dictionarys> selectAll();

    int updateByPrimaryKey(Dictionarys record);
}