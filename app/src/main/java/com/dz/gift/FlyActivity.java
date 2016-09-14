package com.dz.gift;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jaeger.library.StatusBarUtil;
import com.race604.flyrefresh.FlyRefreshLayout;


public class FlyActivity extends AppCompatActivity {

    private FlyRefreshLayout mFlyRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly);
        setSystemStatusBg(this);

        mFlyRefreshLayout = (FlyRefreshLayout) findViewById(R.id.fly_layout);
        mFlyRefreshLayout.setOnPullRefreshListener(new FlyRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh(FlyRefreshLayout view) {

            }

            @Override
            public void onRefreshAnimationEnd(FlyRefreshLayout view) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFlyRefreshLayout.onRefreshFinish();
            }
        },3000);

    }

    public void setSystemStatusBg(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = activity.getWindow();
            win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
