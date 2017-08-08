package com.example.finaltest.test4;

import java.util.Calendar;

/**
 * Created by pinan on 2017/7/14.
 */

public class DayModel {
    public Calendar calendar;//记录日期
    /**
     * 小圆点的类型
     * {@link  (DayConfig)}
     *
     */
    public String type;
    
    public DayModel(Calendar calendar, String type) {
        this.calendar = calendar;
        this.type = type;
    }
}
