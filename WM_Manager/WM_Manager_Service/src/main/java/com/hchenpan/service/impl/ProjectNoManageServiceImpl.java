package com.hchenpan.service.impl;

import com.hchenpan.common.BaseServiceImpl;
import com.hchenpan.mapper.ProjectNoManageMapper;
import com.hchenpan.pojo.ProjectNoManage;
import com.hchenpan.service.ProjectNoManageService;
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
@Service("projectNoManageService")
public class ProjectNoManageServiceImpl extends BaseServiceImpl<ProjectNoManageMapper, ProjectNoManage> implements ProjectNoManageService {
    private final ProjectNoManageMapper mapper;

    @Autowired
    public ProjectNoManageServiceImpl(ProjectNoManageMapper mapper) {
        this.mapper = mapper;
    }


}