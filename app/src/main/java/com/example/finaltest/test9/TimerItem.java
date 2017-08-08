package com.example.finaltest.test9;

import java.util.Comparator;

public class TimerItem implements Comparator<TimerItem> {
    //其他属性
    public String name;
    //倒计时长，单位毫秒
    public long expirationTime;
    
    public TimerItem(String name, long expirationTime) {
        this.name = name;
        this.expirationTime = expirationTime;
    }
    
    
    @Override
    public int compare(TimerItem lhs, TimerItem rhs) {
        long l = lhs.expirationTime - rhs.expirationTime;
        int i = lhs.name.compareTo(rhs.name);
        int ii = i > 0 ? 1 : (i < 0) ? -1 : 0;
        return l > 0 ? 1 : (l < 0) ? -1 : ii;
    }
}
 