package com.hchenpan.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseMapper;
import com.hchenpan.pojo.Callslip;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface CallslipMapper extends BaseMapper<Callslip> {

    List<Map<String, Object>> getallOrderByApplication(Map<String, Object> params);

    List<Map<String, Object>> selectPagelist(Page<Map<String, Object>> page, Map<String, Object> params);

    List<Map<String, Object>> selectPagelistck(Page<Map<String, Object>> page, Map<String, Object> params);

    List<Map<String, Object>> selectSPlistck(Page<Map<String, Object>> page, Map<String, Object> params);
}