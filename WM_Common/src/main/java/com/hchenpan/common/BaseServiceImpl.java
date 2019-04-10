package com.hchenpan.common;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.*;

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

    protected static List<Map<String, Object>> transformUpperCase(List<Map<String, Object>> list) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Map<String, Object> orgMap : list) {
            Map<String, Object> resultMap = new HashMap<>(20);
            if (orgMap == null || orgMap.isEmpty()) {
                mapList.add(resultMap);
            } else {
                Set<String> keySet = orgMap.keySet();
                for (String key : keySet) {
                    String newKey = key.toLowerCase();
                    newKey = newKey.replace("_", "");
                    resultMap.put(newKey, orgMap.get(key));
                }
                mapList.add(resultMap);
            }
        }
        return mapList;
    }
}