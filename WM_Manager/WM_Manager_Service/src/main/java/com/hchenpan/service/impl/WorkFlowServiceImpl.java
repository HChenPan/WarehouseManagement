package com.hchenpan.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.WorkFlowMapper;
import com.hchenpan.pojo.WorkFlow;
import com.hchenpan.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.WorkFlowServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("WorkFlow")
public class WorkFlowServiceImpl extends BaseServiceImpl<WorkFlowMapper, WorkFlow> implements WorkFlowService {
    private final WorkFlowMapper mapper;

    @Autowired
    public WorkFlowServiceImpl(WorkFlowMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public String getnextspnode(String sptypecode, String spnode, String money, String spresult) {
        WorkFlow workFlow;
        String nextspnode;
        //根据上一级查询下一级审批流程
        if (!"0".equals(money)) {
            workFlow = selectOne(new EntityWrapper<WorkFlow>().eq("sptypecode", sptypecode).eq("spnode", spnode).ge("spmoneylowlimit", money).le("spmoneyuplimit", money));
        } else {
            workFlow = selectOne(new EntityWrapper<WorkFlow>().eq("sptypecode", sptypecode).eq("spnode", spnode));
        }
        if ("同意".equals(spresult)) {
            return workFlow.getNextnode();
        } else {
            return workFlow.getBacknode();
        }
    }
}