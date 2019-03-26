package com.hchenpan.util;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.util.BaseMetaObjectHandler
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/2 07:56 下午
 **/
public class BaseMetaObjectHandler extends MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("creatorId", "creatorId", metaObject);
        setFieldValByName("creator", "creator", metaObject);
        setFieldValByName("createTime", System.currentTimeMillis(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updaterId", "updaterId", metaObject);
        setFieldValByName("updater", "updater", metaObject);
        setFieldValByName("updaterTime", System.currentTimeMillis(), metaObject);
    }
}
