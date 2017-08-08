package com.example.finaltest.test4;

import android.graphics.Color;

import com.example.finaltest.utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 日期的管理类
 * Created by xiaozhu on 2016/8/7.
 */
public class DayManager {
    private static DayManager instance = null;
    
    public static DayManager getInstance() {
        if (instance == null) {
            instance = new DayManager();
        }
        return instance;
    }
    
    /**
     * 记录当前的时间
     */
    private Calendar currentTime;
    
    /**
     * 当前的日期
     */
    private int current = -1;
    
    /**
     * 储存当前的日期
     */
    private int tempcurrent = -1;
    /**
     * 日期设置文本
     */
    static String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
    static String[] dayArray = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
        "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    
    /**
     * 记录需要设置不同小圆点的集合
     */
    private List<DayModel> dayModels = new ArrayList<>();
    
    public void setDayModels(List<DayModel> dayModels) {
        this.dayModels = dayModels;
    }
    /**
     * 储存月经期（red）
     */
    private Set<Integer> menstrualDays = new HashSet<>();
    /**
     * 储存易经期(yellow)
     */
    private Set<Integer> easyDays = new HashSet<>();
    /**
     * 储存排卵期(blue)
     */
    private Set<Integer> ovipositDays = new HashSet<>();
    
    /**
     * 储存同房(black)
     */
    private Set<Integer> makeLoveDays = new HashSet<>();
    
    
    /**
     * 设置当前的时间
     *
     * @param currentTime
     */
    public void setCurrentTime(Calendar currentTime) {
        this.currentTime = currentTime;
//        if (this.currentTime == null) {
//            this.currentTime= new GregorianCalendar(currentTime.getTimeZone());
//        }
    }
    
    /**
     * 获取当前的时间
     *
     * @return
     */
    public Calendar getCurrentTime() {
        return currentTime;
    }
    
    
    public void setTempcurrent(int tempcurrent) {
        this.tempcurrent = tempcurrent;
    }
    
    public int getTempcurrent() {
        return tempcurrent;
    }
    
    
    public void setCurrent(int current) {
        this.current = current;
    }
    
    
    private int select = -1;
    
    public void setSelect(int select) {
        this.select = select;
    }
    
    /**
     * 根据日历对象创建日期集合
     *
     * @param calendar 日历
     * @param width    控件的宽度
     * @param heigh    控件的高度
     * @return 返回的天数的集合
     */
    public List<Day> createDayByCalendar(Calendar calendar, int width, int heigh) {
        
        //初始化小圆点
        initPointState();
        
        //存储当月的day的样式
        List<Day> days = new ArrayList<>();
        Day day = null;
        
        int dayWidth = width / 7;
        int dayHeight = heigh / (calendar.getActualMaximum(Calendar.WEEK_OF_MONTH) + 1);
        //添加星期标识，
        for (int i = 0; i < 7; i++) {
            day = new Day(dayWidth, dayHeight);
            //为星期设置位置，为第0行，
            day.location_x = i;
            day.location_y = 0;
            day.text = weeks[i];
            //设置日期颜色
            day.textClor = 0xFF699CF0;
            days.add(day);
        }
        
        
        int count = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        
        //生成每一天的对象，其中第i次创建的是第i+1天
        for (int i = 0; i < count; i++) {
            day = new Day(dayWidth, dayHeight);
            day.text = dayArray[i];
            
            calendar.set(Calendar.DAY_OF_MONTH, i + 1);
            
            //设置每个天数的位置
            day.location_y = calendar.get(Calendar.WEEK_OF_MONTH);
            day.location_x = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            //设置日期的背景颜色和文本的颜色
            if (i == current - 1 && DateUtil.isSameCalendar(calendar,Calendar.getInstance())) {
                //设置日期选择状态
                day.backgroundStyle = Color.parseColor("#FEFF6040");
                day.textClor = Color.WHITE;
            } else if (i == select - 1) {
                day.paintBgStyle = 2;
                day.backgroundStyle = Color.BLACK;
                day.textClor = Color.BLACK;
            } else {
                day.backgroundStyle = Color.parseColor("#FFF2A7C1");
                day.textClor = Color.BLACK;
            }
            
            
            //设置小圆点颜色的状态
            //月经期（red）
            if (menstrualDays.contains(1 + i)) {
                day.addColor(Color.RED);
            }
            //易经期(yellow)
            if (easyDays.contains(i + 1)) {
                day.addColor(Color.YELLOW);
            }
            //排卵期(blue)
            if (ovipositDays.contains(i + 1)) {
                day.addColor(Color.BLUE);
            }
            //同房(black)
            if (makeLoveDays.contains(i + 1)) {
                day.addColor(Color.BLACK);
            }
            
            days.add(day);
        }
        
        return days;
    }
    
    /**
     * 当月设置小圆点的颜色状态
     */
    public void initPointState() {
        if (dayModels == null) {
            return;
        }
        initPointList();
        for (DayModel dayModel : dayModels) {
            //确认是否是当前年月份
            boolean b = (DateUtil.isSameCalendar(dayModel.calendar,currentTime));
            int day = DateUtil.calendar2D(dayModel.calendar);
            if (b) {
                switch (dayModel.type) {
                    case DayConfig.MENSTRUAL+"":
                        menstrualDays.add(day);
                        break;
                    case DayConfig.EASY+"":
                        easyDays.add(day);
                        break;
                    case DayConfig.OVIPOSIT+"":
                        ovipositDays.add(day);
                        break;
                    case DayConfig.MAKELOVE+"":
                        makeLoveDays.add(day);
                        break;
                }
            }
        }
    }
    
    /**
     * 添加时，清理存储的日期
     */
    private void initPointList() {
        menstrualDays.clear();
        easyDays.clear();
        ovipositDays.clear();
        makeLoveDays.clear();
    }
}
