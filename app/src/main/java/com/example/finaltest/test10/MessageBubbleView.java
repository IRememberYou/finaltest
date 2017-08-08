package com.example.finaltest.test10;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * description: 消息气泡拖拽的 View
 * author: Darren on 2017/7/21 10:40
 * email: 240336124@qq.com
 * version: 1.0
 */
public class MessageBubbleView extends View {
    // 拖拽圆的圆心点
    private PointF mDragPoint;
    // 固定圆的圆心点
    private PointF mFixationPoint;
    // 拖拽圆的半径
    private int mDragRadius = 10;
    // 固定圆的半径
    private int mFixationRadius = 7;
    // 固定圆的最小半径
    private int FIXATION_RADIUS_MIN = 3;
    // 固定圆的最大半径
    private int FIXATION_RADIUS_MAX = 7;
    // 用来绘制的画笔
    private Paint mPaint;
    
    public MessageBubbleView(Context context) {
        this(context, null);
    }
    
    public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化画笔
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        // 初始化一些距离
        mDragRadius = dip2px(mDragRadius);
        mFixationRadius = dip2px(mFixationRadius);
        FIXATION_RADIUS_MIN = dip2px(FIXATION_RADIUS_MIN);
        FIXATION_RADIUS_MAX = dip2px(FIXATION_RADIUS_MAX);
    }
    
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        if (mDragPoint == null && mFixationPoint == null) {
            return;
        }
        
        // 1.绘制拖拽圆
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius, mPaint);
        
        // 计算两个圆之间的距离
        int distance = getDistance(mDragPoint, mFixationPoint);
        
        // 计算固定圆的半径，距离越大圆半径越小
        mFixationRadius = FIXATION_RADIUS_MAX - distance / 14;
        
        if (mFixationRadius > FIXATION_RADIUS_MIN) {
            // 2.绘制固定圆
            canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixationRadius, mPaint);
        }
    }
    
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initPoint(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                updateDragPoint(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                
                break;
        }
        invalidate();
        return true;
    }
    
    /**
     * 更新拖拽圆的位置
     *
     * @param x
     * @param y
     */
    private void updateDragPoint(float x, float y) {
        mDragPoint.x = x;
        mDragPoint.y = y;
    }
    
    /**
     * 初始化圆的位置
     *
     * @param x
     * @param y
     */
    private void initPoint(float x, float y) {
        mDragPoint = new PointF(x, y);
        mFixationPoint = new PointF(x, y);
    }
    
    
    /**
     * 获取两点之间的距离
     *
     * @param pointF1
     * @param pointF2
     * @return
     */
    private int getDistance(PointF pointF1, PointF pointF2) {
        double powX = Math.abs(pointF1.x - pointF2.x);
        double powY = Math.abs(pointF1.y - pointF2.y);
        return (int) Math.sqrt(powX * powX +powY * powY);
    }
    
    
    
    /**
     * 获取 Bezier 曲线
     *
     * @return
     */
    public Path getBezierPath() {
        
        if (mFixationRadius < 3) {
            return null;
        }
        
        Path bezierPath = new Path();
        
        
        // 计算斜率
        float dx = mFixationPoint.x - mDragPoint.x;
        float dy = mFixationPoint.y - mDragPoint.y;
        if (dx == 0) {
            dx = 0.001f;
        }
        float tan = dy / dx;
        // 获取角a度值
        float arcTanA = (float) Math.atan(tan);
        
        // 依次计算 p0 , p1 , p2 , p3 点的位置
        float P0X = (float) (mFixationPoint.x + mFixationRadius * Math.sin(arcTanA));
        float P0Y = (float) (mFixationPoint.y - mFixationRadius * Math.cos(arcTanA));
        
        float P1X = (float) (mDragPoint.x + mDragRadius * Math.sin(arcTanA));
        float P1Y = (float) (mDragPoint.y - mDragRadius * Math.cos(arcTanA));
        
        float P2X = (float) (mDragPoint.x - mDragRadius * Math.sin(arcTanA));
        float P2Y = (float) (mDragPoint.y + mDragRadius * Math.cos(arcTanA));
        
        float P3X = (float) (mFixationPoint.x - mFixationRadius * Math.sin(arcTanA));
        float P3Y = (float) (mFixationPoint.y + mFixationRadius * Math.cos(arcTanA));
        // 求控制点 两个点的中心位置作为控制点
        PointF controlPoint = getPointByPercent(mDragPoint, mFixationPoint, 0.5f);
        
        // 整合贝塞尔曲线路径
        bezierPath.moveTo(P0X, P0Y);
        bezierPath.quadTo(controlPoint.x, controlPoint.y, P1X, P1Y);
        bezierPath.lineTo(P2X, P2Y);
        bezierPath.quadTo(controlPoint.x, controlPoint.y, P3X, P3Y);
        bezierPath.close();
        
        return bezierPath;
    }
    
    private PointF getPointByPercent(PointF dragPoint, PointF fixationPoint, float v) {
        float x = (dragPoint.x + fixationPoint.x) / 2;
        float y = (dragPoint.y + fixationPoint.y) / 2;
        return new PointF(x, y);
    }
    
}