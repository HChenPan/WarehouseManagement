package com.hchenpan.util;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.util.StringUtil
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/20 12:48 上午
 **/
public class StringUtil {

    private static boolean isEmpty(String string) {
        return (string == null || string.isEmpty());
    }

    public static boolean notEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * 取得对象值
     *
     * @author lee
     */
    public static String getobjvalue(Object obj) throws Exception {
        String t = "";
        if (obj != null) {
            t = obj.toString();
        }
        return t;
    }
}