package com.hchenpan.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hchenpan.common.BaseService;
import com.hchenpan.model.DictionaryschildVO;
import com.hchenpan.pojo.Dictionarys;
import com.hchenpan.pojo.Dictionaryschild;

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

    Page<DictionaryschildVO> selectDicDictionaryschildVO(Page<DictionaryschildVO> page,String decode);
}
