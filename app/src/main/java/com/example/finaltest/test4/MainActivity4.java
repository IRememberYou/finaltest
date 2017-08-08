package com.example.finaltest.test4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finaltest.R;
import com.example.finaltest.app.BaseActivity;
import com.example.finaltest.utils.DateUtil;
import com.example.finaltest.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pinan on 2017/7/12.
 */

public class MainActivity4 extends BaseActivity  implements CalendarView.OnSelectChangeListener{
    
    @Bind(R.id.cv)
    CalendarView mCalendarView;
    @Bind(R.id.date)
    TextView mTextView;
    @Bind(R.id.et_one_day)
    EditText oneday;
    
    private static final String TAG = "MainActivity4";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        
        //设置小圆点的颜色
        mCalendarView.initPointState(datas());
        //设置日期回调监听
        mCalendarView.setOnSelectChangeListener(this);
        //初始化日历
        mTextView.setText(DateUtil.calendar2String(Calendar.getInstance()));
    }
    
    @OnClick(R.id.last)//上个月
    void last() {
        mCalendarView.setLastMonth();
    }
    
    @OnClick(R.id.next)//下个月
    void next() {
        mCalendarView.setNextMonth();
    }
    
    
    @OnClick(R.id.btn_today)//今日
    void btn_today() {
        mCalendarView.setOneDate(DateUtil.calendar2String(Calendar.getInstance()));
    }
    
    
    @OnClick(R.id.btn_oneday)//指定定某日
    void btn_oneday() {
        String trim = oneday.getText().toString().trim();
        if (trim == null) {
            ToastUtil.show("您输入的日期错误");
            return;
        }
        mCalendarView.setOneDate(trim);
    }
    
    @Override
    public void selectChange(CalendarView calendarView, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mTextView.setText(sdf.format(date));
        ToastUtil.singleShow(date.toString());
        Log.d(TAG, "selectChange: " + date.toString());
    }
    
    
    
    //初始化数据
    private List<DayModel> datas() {
        List<DayModel> dayModels = new ArrayList<>();
        
        dayModels.add(new DayModel(DateUtil.string2Calendar("2016-12-3"),"1"));
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-12-7"),"2"));
        
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-09-3"),"1"));
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-09-5"),"3"));
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-09-6"),"1"));
        
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-10-5"),"2"));
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-10-9"),"3"));
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-10-9"),"2"));
        
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-06-3"),"1"));
        
        
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-07-6"),"1"));
        
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-07-10"),"1"));
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-07-10"),"3"));
        
        
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-07-19"),"1"));
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-07-19"),"2"));
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-07-19"),"3"));
        
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-07-1"),"3"));
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-07-1"),"3"));
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-07-1"),"1"));
        
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-07-23"),"1"));
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-07-23"),"2"));
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-07-23"),"2"));
        dayModels.add(new DayModel(DateUtil.string2Calendar("2017-07-23"),"3"));
        return dayModels;
    }
   
}