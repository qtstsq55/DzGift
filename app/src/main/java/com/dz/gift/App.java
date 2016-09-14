package com.dz.gift;

import android.app.Application;

/**
 * Created by Administrator on 2016/9/14.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                int i = 0;
            }
        });
    }
}
