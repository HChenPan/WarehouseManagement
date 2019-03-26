package com.hchenpan.common;

import com.baomidou.mybatisplus.service.IService;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.common.BaseService
 * Description :service层的接口继承本类获得 mybatis plus 功能
 * public interface ****Service extends BaseService<****>
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/4 02:25 下午
 **/
public abstract interface BaseService<T> extends IService<T> {
}