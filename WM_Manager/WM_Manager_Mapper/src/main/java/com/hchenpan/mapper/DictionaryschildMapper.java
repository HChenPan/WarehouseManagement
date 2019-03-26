package com.hchenpan.mapper;

import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Department;
import com.hchenpan.pojo.Dictionaryschild;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DictionaryschildMapper extends BaseMapper<Dictionaryschild> {
    int deleteByPrimaryKey(String id);


    Dictionaryschild selectByPrimaryKey(String id);

    List<Dictionaryschild> selectAll();

    int updateByPrimaryKey(Dictionaryschild record);
}