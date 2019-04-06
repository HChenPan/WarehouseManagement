package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.DictionaryschildMapper;
import com.hchenpan.mapper.SptypespLevelMapper;
import com.hchenpan.model.PageContainer;
import com.hchenpan.pojo.Dictionaryschild;
import com.hchenpan.pojo.SptypespLevel;
import com.hchenpan.service.SptypespLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
@Service("sptypespLevelService")
public class SptypespLevelServiceImpl extends BaseServiceImpl<SptypespLevelMapper, SptypespLevel> implements SptypespLevelService {
    private final SptypespLevelMapper sptypespLevelMapper;
    private final DictionaryschildMapper dictionaryschildMapper;

    @Autowired
    public SptypespLevelServiceImpl(SptypespLevelMapper sptypespLevelMapper, DictionaryschildMapper dictionaryschildMapper) {
        this.sptypespLevelMapper = sptypespLevelMapper;
        this.dictionaryschildMapper = dictionaryschildMapper;
    }

    @Override
    public PageContainer<SptypespLevel> selectSPPage(Page<SptypespLevel> page, SptypespLevel sptypeSplevel) {
        PageContainer<SptypespLevel> pc = new PageContainer<>();
        List<SptypespLevel> splist = new ArrayList<>();
        List<SptypespLevel> sptypespLevels = sptypespLevelMapper.selectPage(page, new EntityWrapper<>(sptypeSplevel));
        for (SptypespLevel sptypespLevel : sptypespLevels) {
            Dictionaryschild dictionaryschild = new Dictionaryschild();
            dictionaryschild.setCode(sptypespLevel.getSptypecode());
            sptypespLevel.setSptypename(dictionaryschildMapper.selectOne(dictionaryschild).getName());
            dictionaryschild.setCode(sptypespLevel.getSplevelcode());
            sptypespLevel.setSplevelname(dictionaryschildMapper.selectOne(dictionaryschild).getName());
            splist.add(sptypespLevel);
        }
        pc.setRows(splist);
        pc.setTotal(sptypespLevels.size());
        return pc;
    }
}