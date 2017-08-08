package com.example.finaltest.test4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期的类
 * Created by xiaozhu on 2016/8/7.
 */
public class Day {
    /**
     * 记录小圆点颜色
     */
    public List<Integer> pointColors = new ArrayList<>();
    
    /**
     * 设置画日期背景的状态
     * 1,表示实心
     * 2,表示空心
     */
    public int paintBgStyle =1;
    
    /**
     * 单个日期格子的宽
     */
    public int width;
    /**
     * 单个日期格子的高
     */
    public int height;
    /**
     * 日期的文本
     */
    public String text;
    /**
     * 文本字体的颜色
     */
    public int textClor;
    
    /**
     * 日期背景的类型 0代表无任何背景，1代表正常打卡，2代表所选日期，3代表当前日期 4,代表即是当前日期，也被选中
     */
    public int backgroundStyle;
    
    /**
     * 字体的大小
     */
    public float textSize;
    
    
    /**
     * 背景的半径
     */
    public int backgroundR;
    
    /**
     * 小圆点状态的半径
     */
    public int workStateR = 5;
    /**
     * 小圆点状态的之间的间距
     */
    public int workStateD = 3;
    
    
    /**
     * 字体在第几行
     */
    public int location_x;
    /**
     * 字体在第几列
     */
    public int location_y;
    
    
    /**
     * 创建日期对象
     *
     * @param width  每个日期格子的宽度
     * @param height 每个日期格子的高度
     */
    public Day(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     * 画天数
     *
     * @param canvas  要画的画布
     * @param paint   画笔
     * @param context 画布的上下文对象
     */
    public void drawDays(Canvas canvas, Context context, Paint paint) {
        //取窄的边框为圆的半径
        backgroundR = Math.min(width, height);
        
        //画背景
        drawBackground(canvas, paint);
        
        //画数字
        drawText(canvas, paint);
        
        //画考勤
        drawColorState(canvas, paint);
        
    }
    
    /**
     * 画小圆点的颜色状态
     *
     * @param canvas
     */
    private void drawColorState(Canvas canvas, Paint paint) {
        int typeSize = pointColors.size();
        //没有设置工作状态，不画
        if (typeSize <= 0) {
            return;
        }
        
        //确定圆心位置
        float cx = location_x * width + width / 2;
        float xy = location_y * height + height * 44 / 60;
        paint.setStyle(Paint.Style.FILL);//实心
        
        //根据不同个数画小圆点，使其居中
        int k = typeSize / 2;
        if (typeSize % 2 == 0) {//偶数
            for (int i = 0; i < typeSize; i++) {
                paint.setColor(pointColors.get(i));
                canvas.drawCircle((float) (cx - (k - 0.5 - i) * (2 * workStateR + workStateD)), xy, workStateR, paint);
            }
        } else {
            for (int i = 0; i < typeSize; i++) {
                paint.setColor(pointColors.get(i));
                canvas.drawCircle(cx - (k - i) * (2 * workStateR + workStateD), xy, workStateR, paint);
            }
        }
    }
    
    /**
     * 画文本
     *
     * @param canvas
     * @param paint
     */
    private void drawText(Canvas canvas, Paint paint) {
        //根据圆的半径设置字体的大小
        textSize = backgroundR / 3;
        paint.setTextSize(textSize);
        paint.setColor(textClor);
        paint.setStyle(Paint.Style.FILL);
        //计算文字的宽度
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int w = rect.width();
        //计算画文字的位置
        float x = location_x * width + (width - w) / 2;
        float y = location_y * height + (height + textSize / 2) / 2;
        canvas.drawText(text, x, y, paint);
    }
    
    /**
     * 画背景
     *
     * @param canvas
     * @param paint
     */
    public void drawBackground(Canvas canvas, Paint paint) {
        if (paintBgStyle ==1) {
            paint.setStyle(Paint.Style.FILL);
        }else {
            paint.setStyle(Paint.Style.STROKE);
        }
        //计算圆心的位置
        float cx = location_x * width + width / 2;
        float cy = location_y * height + height / 2;
        float radius = backgroundR * 9 / 20;
        /**
         * 日期背景的类型
         */
        paint.setColor(backgroundStyle);
        canvas.drawCircle(cx, cy, radius, paint);
    }
    
    
    /**
     * 添加小圆点颜色的类型
     *
     * @param colors
     */
    public void addColor(int colors) {
        pointColors.add(colors);
    }
    
    /**
     * 画笔setting
     */
    public void setPaint(Paint paint,int color,Paint.Style style) {
        paint.setColor(color);
        paint.setStyle(style);
    }
}
