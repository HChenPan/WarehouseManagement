package com.hchenpan.shiro;

import java.util.LinkedHashMap;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.shiro.FilterChainDefinitionMapBuilder
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/23 07:22 下午
 **/
public class FilterChainDefinitionMapBuilder {
    public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("/css/**", "anon");
        map.put("/JavaScript/**", "anon");
        map.put("/statics/**", "anon");
        map.put("/login.do", "anon");
        map.put("/login", "anon");
        map.put("/logout", "logout");
        map.put("/**", "authc");

        return map;
    }
}