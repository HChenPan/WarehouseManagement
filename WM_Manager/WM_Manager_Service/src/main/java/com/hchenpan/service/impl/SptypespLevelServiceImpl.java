package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.ApprovalRecordMapper;
import com.hchenpan.mapper.DictionaryschildMapper;
import com.hchenpan.mapper.SptypespLevelMapper;
import com.hchenpan.mapper.SptypespLevelUserMapper;
import com.hchenpan.model.CommboxList;
import com.hchenpan.model.PageContainer;
import com.hchenpan.pojo.ApprovalRecord;
import com.hchenpan.pojo.Dictionaryschild;
import com.hchenpan.pojo.SptypespLevel;
import com.hchenpan.pojo.SptypespLevelUser;
import com.hchenpan.service.SptypespLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
@Service("sptypespLevelService")
public class SptypespLevelServiceImpl extends BaseServiceImpl<SptypespLevelMapper, SptypespLevel> implements SptypespLevelService {
    private final SptypespLevelMapper sptypespLevelMapper;
    private final ApprovalRecordMapper approvalRecordMapper;
    private final SptypespLevelUserMapper sptypespLevelUserMapper;
    private final DictionaryschildMapper dictionaryschildMapper;

    @Autowired
    public SptypespLevelServiceImpl(SptypespLevelMapper sptypespLevelMapper, ApprovalRecordMapper approvalRecordMapper, SptypespLevelUserMapper sptypespLevelUserMapper, DictionaryschildMapper dictionaryschildMapper) {
        this.sptypespLevelMapper = sptypespLevelMapper;
        this.approvalRecordMapper = approvalRecordMapper;
        this.sptypespLevelUserMapper = sptypespLevelUserMapper;
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

    @Override
    public List<CommboxList> getusersplevelnode(String sptypecodes, String id) {
        Map<String, Object> params = new HashMap<>(10);
        params.put("sptypecodes", sptypecodes);
        params.put("id", id);
        List<CommboxList> getusersplevelnode = sptypespLevelMapper.getusersplevelnode(params);
        return getusersplevelnode;
    }

    @Override
    public int getspusernum(String sptypecode, String spnodecode) {
        SptypespLevel sptypespLevel = selectOne(new EntityWrapper<SptypespLevel>().eq("sptypecode", sptypecode).eq("splevelcode", spnodecode));
        return sptypespLevelUserMapper.selectCount(new EntityWrapper<SptypespLevelUser>().eq("SPTYPESPLEVELID", sptypespLevel.getId()));
    }

    @Override
    public int gettyspnum(String sptypecode, String spnodecode, String spid) {
        List<ApprovalRecord> approvalRecords = approvalRecordMapper.selectList(new EntityWrapper<ApprovalRecord>().setSqlSelect("DISTINCT spuserid ").eq("sptypecode", sptypecode).eq("spnodecode", spnodecode).eq("spresult", "同意").eq("spid", spid).groupBy("spuserid"));
        return approvalRecords.size();
    }

    @Override
    public List<Map<String, Object>> selectSPJBList(String sptypecode) {
        Map<String, Object> params = new HashMap<>(10);
        params.put("sptypecode", sptypecode);
        return transformUpperCase(sptypespLevelMapper.selectSPJBList(params));
    }
}