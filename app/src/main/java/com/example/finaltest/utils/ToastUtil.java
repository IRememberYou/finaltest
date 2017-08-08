package com.example.finaltest.utils;

import android.widget.Toast;

import com.example.finaltest.app.MyApp;

/**
 * Created by pinan on 2017/7/7.
 */

public class ToastUtil {
    
    private static Toast mToast = null;
    private static long mFirstTime;
    private static long mSecendTime;
    private static String mOldMsg;
    
    public static void show(String msg) {
        Toast.makeText(MyApp.sContext, msg, Toast.LENGTH_SHORT).show();
    }
    
    public static void singleShow(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApp.sContext, msg, Toast.LENGTH_SHORT);
            mToast.show();
            mFirstTime = System.currentTimeMillis();
        } else {
            mSecendTime = System.currentTimeMillis();
            if (msg.equals(mOldMsg)) {
                if (mSecendTime - mFirstTime > Toast.LENGTH_SHORT) {
                    mToast.show();
                }
            } else {
                mOldMsg = msg;
                mToast.setText(msg);
                mToast.show();
            }
        }
        mFirstTime = mSecendTime;
    }
}
