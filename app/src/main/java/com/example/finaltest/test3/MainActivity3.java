package com.example.finaltest.test3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finaltest.R;
import com.example.finaltest.app.BaseActivity;

import java.lang.reflect.Method;

import butterknife.OnClick;

/**
 * Created by pinan on 2017/7/10.
 */

public class MainActivity3 extends BaseActivity {
    MethodHook methodHook;
    Method srcMethod;
    Method destMethod;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        methodHook = new MethodHook();
        try {
            srcMethod = getClass().getDeclaredMethod("showToast");
            destMethod = getClass().getDeclaredMethod("showHookToast");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    
    public void showToast() {
        Toast.makeText(this, "Hello!", Toast.LENGTH_SHORT).show();
    }
    
    public void showHookToast() {
        Toast.makeText(this, "Hello Hook!", Toast.LENGTH_SHORT).show();
    }
    
    @OnClick(R.id.click)
    void click() {
        //方法替换前显示Hello!，方法替换后实际调用showHookToast()显示Hello Hook!
        showToast();
    }
    
    @OnClick(R.id.hook)
    void hook() {
        methodHook.hook(srcMethod, destMethod);
    }
    
    @OnClick(R.id.restore)
    void restore() {
        //方法恢复
        methodHook.restore(srcMethod);
    }
    
    @OnClick(R.id.calculate)
    void calculate() {
        startActivity(new Intent(this, CJavaActivity.class));
    }
}