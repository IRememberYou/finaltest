package com.example.finaltest.test6;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created by pinan on 2017/8/4.
 */

public class MyView extends View {
    private String TAG = "MyView";
    //两条圆弧的起始角度
    private double floatAngel = 65;
    //刻度的个数
//    private int[] str = {0, 5, 15, 20};
    private int[] str = {6, 7, 8, 9, 10, 11, 12, 1, 2, 3, 4, 5};
    //间隔的角度
    private double spaceAngle = (180 + 2 * floatAngel) * 1.0 / (str.length - 1);
    
    private Paint mPaint;//画笔
    private Paint mPaintText;//画笔画文字
    private Paint mPaintCircle;//画笔画圆
    
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private int mRadius;
    
    private int mWidth;
    private int mHeight;
    
    private float mProgess = 64;
    private float moveAngel = 0;
    
    private ValueAnimator mValueAnimator;//动画
    private Path mPath;
    private Paint mPaintFill;
    
    private int mTopSpace = 8;
    
    
    public MyView(Context context) {
        super(context);
        init();
    }
    
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        mPath = new Path();
        //画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        
        //画刻度
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setColor(Color.BLACK);
        mPaintText.setTextSize(20);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        
        //小球
        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setColor(Color.GREEN);
        mPaintCircle.setStrokeWidth(4);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        
        //实心小球
        mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintFill.setColor(Color.WHITE);
        
    }
    
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取宽高
        mWidth = getWidth();
        mHeight = getHeight();
        
        //半径
        mRadius = Math.min(mWidth, mHeight) / 2;
        //创造画布画板
        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        
        Log.d(TAG, "width: " + mWidth + "  /  height: " + mHeight);
        //画弧线
        paintPercentBack(mCanvas);
        //画文字，刻度
        paintPercentText(mCanvas);
        //画小球
        drawCircle(mCanvas);
        //最后将其画在view上
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }
    
    
    /**
     * 画弧线
     *
     * @param canvas
     */
    private void paintPercentBack(Canvas canvas) {
        //设置画笔渐变色
        mPaint.setShader(new LinearGradient((float) (mWidth / 2 - Math.cos(floatAngel) * mRadius), (float) (mRadius + Math.sin(floatAngel) * mRadius)
            , (float) (mWidth / 2 + Math.cos(floatAngel) * mRadius), (float) (mRadius + Math.sin(floatAngel) * mRadius), new int[]{Color.parseColor("#FFF97F1C"), Color.YELLOW, Color.GREEN}, null, Shader.TileMode.MIRROR));
        
        RectF outerArea = new RectF(mWidth / 2 - mRadius + mPaint.getStrokeWidth() / 2 + mTopSpace, mPaint.getStrokeWidth() / 2 + mTopSpace, mWidth / 2 + mRadius - mPaint.getStrokeWidth() / 2 - mTopSpace, 2 * mRadius - mPaint.getStrokeWidth() / 2 - mTopSpace);
        canvas.drawArc(outerArea, (float) (180 - floatAngel), (float) (180 + 2 * floatAngel), false, mPaint);
    }
    
    /**
     * 旋转画布画刻度
     *
     * @param canvas 画布
     */
    private void paintPercentText(Canvas canvas) {
        for (int i = 0; i < str.length; i++) {
            //保存画布
            canvas.save();
            //旋转角度，第一个参数是旋转的角度、第二个参数和第三个参数是旋转中心点x和y
            canvas.rotate((float) (spaceAngle * i - 90 - floatAngel), mWidth / 2, mRadius);
            //画文字
            
            canvas.drawLine(mWidth / 2, mPaint.getStrokeWidth() + mTopSpace, mWidth / 2, mPaint.getStrokeWidth() + mPaintText.getStrokeWidth() + mTopSpace + 8, mPaintText);
            canvas.drawText(str[i] + "", mWidth / 2, mPaint.getStrokeWidth() + mPaintText.getStrokeWidth() + mPaintText.getTextSize() + 8 + 2 + mTopSpace, mPaintText);
            canvas.restore();
        }
    }
    
    /**
     * 画圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        canvas.rotate((float) (-90 - floatAngel) + moveAngel, mWidth / 2, mRadius);
        canvas.drawCircle(mWidth / 2, mPaint.getStrokeWidth() / 2 + mTopSpace, mPaint.getStrokeWidth() / 2 + mTopSpace - mPaintCircle.getStrokeWidth() / 2, mPaintCircle);
        canvas.drawCircle(mWidth / 2, mPaint.getStrokeWidth() / 2 + mTopSpace, mPaint.getStrokeWidth() / 2 + mTopSpace - mPaintCircle.getStrokeWidth() / 2 - mPaintCircle.getStrokeWidth() / 2, mPaintFill);
        
    }
    
    /**
     * 必须调用此方法
     *
     * @param progess
     */
    public void setAngel(float progess) {
        mProgess = progess;
        if (progess < 0 || progess > 100) {
            new Throwable("progess没有设置或者不在范围内");
        }
        mValueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(10000);
        
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                moveAngel = (float) (value * (mProgess * (180 + 2 * floatAngel) / 100));
                invalidate();
            }
        });
        
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                
            }
            
            @Override
            public void onAnimationEnd(Animator animation) {
                mValueAnimator.removeAllUpdateListeners();
                mValueAnimator.removeAllListeners();
            }
            
            @Override
            public void onAnimationCancel(Animator animation) {
                
            }
            
            @Override
            public void onAnimationRepeat(Animator animation) {
                
            }
        });
        mValueAnimator.start();
    }
    
    /**
     * 实心圆的颜色
     *
     * @param color
     */
    public void setCircleColor(@ColorInt int color) {
        mPaintFill.setColor(color);
//        invalidate();
    }
}
