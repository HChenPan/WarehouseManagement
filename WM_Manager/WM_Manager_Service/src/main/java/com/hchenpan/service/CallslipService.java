package com.hchenpan.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.Callslip;

import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.CallslipService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface CallslipService extends BaseService<Callslip> {

    List<Map<String, Object>> getallOrderByApplication(String tkrid);

    String createnumberid(String htInitials);

    Page<Map<String, Object>> selectPagelist(Page<Map<String, Object>> page, Map<String, Object> params);

    Page<Map<String, Object>> selectPagelistck(Page<Map<String, Object>> page, Map<String, Object> params);

    Page<Map<String, Object>> selectSPlist(Page<Map<String, Object>> page, String toString);

    String getstatus(String callslipcode);
}
