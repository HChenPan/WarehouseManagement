package com.hchenpan.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.util.ObjectUtil
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/4 02:09 下午
 **/
public class ObjectUtil {
    public static boolean isNull(Object object) {
        return (object == null);
    }

    public static boolean notNull(Object object) {
        return !isNull(object);
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object object) {
        if (isNull(object)) {
            return true;
        }

        if (object instanceof String) {
            return "".equals(object);
        }

        if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        }

        if (object instanceof Map) {
            return ((Map) object).isEmpty();
        }

        if (object.getClass().isArray()) {
            return (Array.getLength(object) == 0);
        }

        return true;
    }

    public static boolean notEmpty(Object object) {
        return !isEmpty(object);
    }

    public static <T> T nullSafe(T t, T s) {
        return (t == null ? s : t);
    }
}