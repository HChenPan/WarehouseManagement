package com.hchenpan.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseService;
import com.hchenpan.pojo.Dictionaryschild;

import java.util.List;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.service.UserService
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/5 09:39 上午
 **/
public interface DictionaryschildService extends BaseService<Dictionaryschild> {


    Page<Map<String, Object>> getChildPage(Page<Map<String, Object>> page, Map<String, Object> params);

    List<Dictionaryschild> selectchildList(String id);

    List<Dictionaryschild> getdchildlistbydecode(String dcode);

    String getsptypecodesbynote(String note);

    String getSpnodenamebycode(String dcode, String code);
}
