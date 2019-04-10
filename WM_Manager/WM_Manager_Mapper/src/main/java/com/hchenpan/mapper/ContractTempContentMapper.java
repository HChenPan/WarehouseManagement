package com.hchenpan.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.ContractTempContent;

import java.util.List;
import java.util.Map;

public interface ContractTempContentMapper extends BaseMapper<ContractTempContent> {
    List<Map<String, Object>> selectPage(Page<Map<String, Object>> page, String tempnameId);

}