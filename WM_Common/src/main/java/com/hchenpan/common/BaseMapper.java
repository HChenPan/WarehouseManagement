package com.hchenpan.common;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.common.BaseMapper
 * Description :mapper层继承本类获得mybatis plus 功能
 * public interface ****Mapper extends BaseMapper<****>
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/4 02:20 下午
 **/
public abstract interface BaseMapper<T> extends com.baomidou.mybatisplus.mapper.BaseMapper<T> {
}