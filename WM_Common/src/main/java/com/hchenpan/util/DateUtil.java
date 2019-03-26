package com.hchenpan.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.util.DateUtil
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/4 02:11 下午
 **/
public class DateUtil {
    public static String formatString(long time, String style) {
        if (style == null || style.equals("")) {
            style = "yyyy-MM-dd HH:mm:ss";
        }

        return new SimpleDateFormat(style).format(new Date(time));
    }

    public static String formatString(long time) {
        return formatString(time, "yyyy-MM-dd");
    }

    public static String formatString(Date date, String style) {
        return formatString(date.getTime(), style);
    }

    public static String formatString(Date date) {
        return formatString(date, "yyyy-MM-dd");
    }

    public static long formatLong(String time, String style) {
        return formatDate(time, style).getTime();
    }

    public static long formatLong(String time) {
        return formatLong(time, "yyyy-MM-dd");
    }

    public static long formatLong(Date date) {
        return date.getTime();
    }

    public static Date formatDate(String time, String style) {
        Date date;

        try {
            date = new SimpleDateFormat(style).parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date(0L);
        }

        return date;
    }

    public static Date formatDate(String time) {
        return formatDate(time, "");
    }

    public static Date formatDate(long time, String style) {
        if (style == null || style.equals("")) {
            style = "yyyy-MM-dd HH:mm:ss";
        }

        return formatDate(formatString(time, style), style);
    }

    public static Date formatDate(long time) {
        return formatDate(time, "");
    }
}