package com.example.finaltest.test4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.finaltest.utils.DateUtil;
import com.example.finaltest.utils.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 自定义的日历控件
 * Created by xiaozhu on 2016/8/1.
 */
public class CalendarView extends View {
    
    private Context context;
    
    private Paint paint;
    /***
     * 当前的时间
     */
    private Calendar calendar;
    private OnSelectChangeListener listener;
    
    private DayManager mDayManager;
    
    
    /**
     * 改变日期，并更改当前状态，由于绘图是在calendar基础上进行绘制的，所以改变calendar就可以改变图片
     *
     * @param calendar
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        if (DateUtil.calendar2YM(calendar).equals(DateUtil.calendar2YM(mDayManager.getCurrentTime()))) {
            mDayManager.setCurrent(mDayManager.getTempcurrent());
        } else {
            mDayManager.setCurrent(-1);
        }
        invalidate();
    }
    
    public CalendarView(Context context) {
        super(context);
        this.context = context;
        //初始化控件
        initView();
    }
    
    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //初始化控件
        initView();
    }
    
    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //初始化控件
        initView();
    }
    
    /***
     * 初始化控件
     */
    private void initView() {
        //初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        //初始化日期
        mDayManager = DayManager.getInstance();
        calendar = Calendar.getInstance();
        mDayManager.setCurrent(calendar.get(Calendar.DAY_OF_MONTH));
        mDayManager.setTempcurrent(calendar.get(Calendar.DAY_OF_MONTH));
        mDayManager.setCurrentTime(calendar);
    }
    
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取day集合并绘制
        List<Day> days = mDayManager.createDayByCalendar(calendar, getMeasuredWidth(), getMeasuredHeight());
        for (Day day : days) {
            day.drawDays(canvas, context, paint);
        }
    }
    
    float downX = 0;
    float downY = 0;
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            //记录点下的坐标位置
            downX = event.getX();
            downY = event.getY();
        }
        
        if (MotionEvent.ACTION_UP == event.getAction()) {
            //记录抬起来的坐标位置
            float upX = event.getX();
            float diffX = upX - downX;
            if (Math.abs(diffX) > 100) {
                //获取当前日期
                if (diffX > 0) {//右边滑动 -1
                    calendar.add(Calendar.MONTH, -1);
                } else {// 左边滑动 +1
                    calendar.add(Calendar.MONTH, 1);
                }
                
                if (listener != null) {
                    listener.selectChange(this, calendar.getTime());
                }
                
                invalidate();
            } else {
                //计算点击的是哪个日期
                int locationX = (int) (downX * 7 / getMeasuredWidth());
                int locationY = (int) ((calendar.getActualMaximum(Calendar.WEEK_OF_MONTH) + 1) * downY / getMeasuredHeight());
                
                if (locationY == 0) {
                    return super.onTouchEvent(event);
                } else if (locationY == 1) {
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    System.out.println("xiaozhu" + calendar.get(Calendar.DAY_OF_WEEK) + ":" + locationX);
                    if (locationX < calendar.get(Calendar.DAY_OF_WEEK) - 1) {
                        return super.onTouchEvent(event);
                    }
                } else if (locationY == calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)) {
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                    if (locationX > calendar.get(Calendar.DAY_OF_WEEK) + 1) {
                        return super.onTouchEvent(event);
                    }
                }
                
                calendar.set(Calendar.WEEK_OF_MONTH, locationY);
                calendar.set(Calendar.DAY_OF_WEEK, (locationX + 1));
                
                mDayManager.setSelect(calendar.get(Calendar.DAY_OF_MONTH));
                if (listener != null) {
                    listener.selectChange(this, calendar.getTime());
                }
                
                invalidate();
            }
            
            
        }
        return true;
    }
    
    /**
     * 设置日期选择改变监听
     *
     * @param listener
     */
    public void setOnSelectChangeListener(OnSelectChangeListener listener) {
        this.listener = listener;
    }
    
    /**
     * 日期选择改变监听的接口
     */
    public interface OnSelectChangeListener {
        void selectChange(CalendarView calendarView, Date date);
    }
    
    /**
     * @param dayModels
     */
    public void initPointState(List<DayModel> dayModels) {
        mDayManager.setDayModels(dayModels);
    }
    
    /**
     * 设置上个月
     */
    public void setLastMonth() {
        calendar.add(Calendar.MONTH, -1);
        if (listener != null) {
            listener.selectChange(this, calendar.getTime());
        }
        invalidate();
    }
    
    /**
     * 设置下个月
     */
    public void setNextMonth() {
        calendar.add(Calendar.MONTH, 1);
        if (listener != null) {
            listener.selectChange(this, calendar.getTime());
        }
        invalidate();
    }
    
    public void setOneDate(String calendars) {
        Calendar c = DateUtil.string2Calendar(calendars);
        calendar.setTime(c.getTime());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            sdf.parse(calendars);
        } catch (ParseException e) {
            ToastUtil.show("您输入的日期错误");
            e.printStackTrace();
            return;
        }
        
        if (listener != null) {
            listener.selectChange(this, calendar.getTime());
        }
        invalidate();
    }
}
