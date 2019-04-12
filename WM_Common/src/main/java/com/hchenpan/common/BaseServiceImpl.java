package com.hchenpan.common;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.text.SimpleDateFormat;
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

    /**
     * 获取前/后半年的开始时间
     *
     * @return
     */
    public static String getHalfYearStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();


        //过去三个月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -6);
        Date m3 = c.getTime();
        String mon3 = format.format(m3);
        System.out.println("过去六个月：" + mon3);
        return mon3;


    }
}