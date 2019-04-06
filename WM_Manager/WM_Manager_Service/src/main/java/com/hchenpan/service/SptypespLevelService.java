package com.hchenpan.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseService;
import com.hchenpan.model.PageContainer;
import com.hchenpan.pojo.SptypespLevel;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.SptypespLevelService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface SptypespLevelService extends BaseService<SptypespLevel> {


    PageContainer<SptypespLevel> selectSPPage(Page<SptypespLevel> page, SptypespLevel sptypeSplevel);
}
