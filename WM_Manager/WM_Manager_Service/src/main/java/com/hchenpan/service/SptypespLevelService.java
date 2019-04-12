package com.hchenpan.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseService;
import com.hchenpan.model.CommboxList;
import com.hchenpan.model.PageContainer;
import com.hchenpan.pojo.SptypespLevel;

import java.util.List;

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

    List<CommboxList> getusersplevelnode(String sptypecodes, String id);

    int getspusernum(String sptypecode, String spnodecode);

    int gettyspnum(String sptypecode, String spnodecode, String spid);
}
