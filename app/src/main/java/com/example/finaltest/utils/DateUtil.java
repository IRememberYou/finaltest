package com.example.finaltest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by pinan on 2017/7/14.
 */

public class DateUtil {
    
    /**
     * String 转化Calendar
     */
    public static Calendar string2Calendar(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Calendar转化String
     */
    public static String calendar2String(Calendar calendat) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendat.getTime());
    }
    
    /**
     * 将日历转化为 yyyy-MM（ eg: 2017-07 ）
     *
     * @param calendar
     * @return
     */
    public static String calendar2YM(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(calendar.getTime());
    }
    
    /**
     * 获取当月的几号
     * @param calendar
     * @return
     */
    public static int calendar2D(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 比较是否是同一个月
     * @param calendar1
     * @param calendar2
     * @return
     */
    public static boolean isSameCalendar(Calendar calendar1 , Calendar calendar2) {
        return calendar2YM(calendar1).equals(calendar2YM(calendar2));
    }
}
