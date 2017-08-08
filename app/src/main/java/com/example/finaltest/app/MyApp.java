package com.example.finaltest.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by pinan on 2017/7/7.
 */

public class MyApp extends Application {
    public static Context sContext;
    
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
