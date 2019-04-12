package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.ApprovalRecordMapper;
import com.hchenpan.pojo.ApprovalRecord;
import com.hchenpan.service.ApprovalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.impl.TestServiceImpl
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/3 09:07 下午
 **/
@Service("approvalRecordService")
public class ApprovalRecordServiceImpl extends BaseServiceImpl<ApprovalRecordMapper, ApprovalRecord> implements ApprovalRecordService {
    private final ApprovalRecordMapper mapper;

    @Autowired
    public ApprovalRecordServiceImpl(ApprovalRecordMapper mapper) {
        this.mapper = mapper;
    }


}