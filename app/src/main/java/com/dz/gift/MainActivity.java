package com.dz.gift;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;

import java.text.SimpleDateFormat;

import cn.iwgang.countdownview.CountdownView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTranslucent(this,0);

        CountdownView mCvCountdownViewTest2 = (CountdownView)findViewById(R.id.cv_countdownViewTest2);
        long time2 = convert2long("2015-09-19 14:00:00") - System.currentTimeMillis();
        if(time2 <= 0){
            gotoFlyActivity();
        }else{
            mCvCountdownViewTest2.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                @Override
                public void onEnd(CountdownView cv) {
                    gotoFlyActivity();
                }
            });
            mCvCountdownViewTest2.start(time2);
        }
    }

    private long convert2long(String date) {
        try {
            String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sf = new SimpleDateFormat(format);
            return sf.parse(date).getTime();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }


    private void gotoFlyActivity(){
        startActivity(new Intent(this, FlyActivity.class));
        finish();
    }
}
