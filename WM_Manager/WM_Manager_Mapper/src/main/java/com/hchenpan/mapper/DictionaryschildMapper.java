package com.hchenpan.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hchenpan.common.BaseMapper;
import com.hchenpan.model.DictionaryschildVO;
import com.hchenpan.pojo.Department;
import com.hchenpan.pojo.Dictionaryschild;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DictionaryschildMapper extends BaseMapper<Dictionaryschild> {

    int deleteByPrimaryKey(String id);


    Dictionaryschild selectByPrimaryKey(String id);

    List<Dictionaryschild> selectAll();

    int updateByPrimaryKey(Dictionaryschild record);


    /**
     * description(描述) 多表查询 数据字典
     *
     * @param page 翻页对象，可以作为 xml 参数直接使用，传递参数 Page 即自动分页
     * @return
     * @author Huangcp
     * @date 2019/3/27 05:16 下午
     **/
    @Select("SELECT C.ID,C.DCODE,C.CODE,C.NAME,C.UPDATETIME,D.DNAME,C.NOTE FROM WMDB.WM_DICTIONARYSCHILD C , WMDB.WM_DICTIONARYS D WHERE 1 = 1 AND C.FLAG = 'E' AND C.DCODE = '#{decode} ' AND C.DICTIONARYS_ID = D.ID")
    List<DictionaryschildVO> selectDicDictionaryschildVO(Pagination page, String decode);
}