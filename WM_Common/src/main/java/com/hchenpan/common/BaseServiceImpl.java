package com.hchenpan.common;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.common.BaseServiceImpl
 * Description :service层的实现类继承本类获取mybatis plus 功能
 * public class ****ServiceImpl extends BaseServiceImpl<****Mapper, ****> implements ****Service
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/4 02:26 下午
 **/

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
    public BaseServiceImpl() {
    }
}