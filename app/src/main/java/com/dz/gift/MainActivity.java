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
        long time2 = convert2long("2016-10-19 15:30:00") - System.currentTimeMillis();
        time2 = Math.max(time2,5000);
        if(time2 <= 0){
            gotoSvgActivity();
        }else{
            mCvCountdownViewTest2.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                @Override
                public void onEnd(CountdownView cv) {
                    gotoSvgActivity();
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

    private void gotoSvgActivity(){
        startActivity(new Intent(this, PagerActivity.class));
        finish();
    }
}
