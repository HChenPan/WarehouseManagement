package com.hchenpan.service.impl;

import com.github.pagehelper.PageHelper;
import com.hchenpan.mapper.TestMapper;
import com.hchenpan.pojo.Test;
import com.hchenpan.service.TestService;
import com.hchenpan.util.DataPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.TestServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("testService")
public class TestServiceImpl implements TestService {
    private final TestMapper mapper;

    @Autowired
    public TestServiceImpl(TestMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean insertTest(Test test) {
        System.out.println(test.toString());
        return mapper.insert(test) > 0;
    }

    @Override
    public DataPage<Test> selectPage(Integer pageNum, Integer pageSize, Integer indexCount) {
        PageHelper.startPage(pageNum, pageSize);
        List<Test> movies = mapper.selectByExample(null);

        Integer pageCount = movies.size();
        pageNum = pageNum > pageCount ? pageCount : pageNum;

        DataPage<Test> page = new DataPage<>();
        page.setData(movies);
        page.setIndexCount(indexCount);
        page.setPageCount(pageCount);
        page.setPageSize(pageSize);
        page.setPageNum(pageNum);

        return page;
    }

}