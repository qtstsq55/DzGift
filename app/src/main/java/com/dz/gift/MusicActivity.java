package com.dz.gift;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.jaeger.library.StatusBarUtil;

import co.mobiwise.library.AnimationCompleteListener;
import co.mobiwise.library.MaskProgressView;
import co.mobiwise.library.OnProgressDraggedListener;


public class MusicActivity extends AppCompatActivity {

    private MaskProgressView cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_music);
        StatusBarUtil.setTranslucent(this,0);
        cover = (MaskProgressView) findViewById(R.id.ipv);
        cover.setAnimationCompleteListener(new AnimationCompleteListener() {
            @Override
            public void onAnimationCompleted() {

            }
        });
        cover.setOnProgressDraggedListener(new OnProgressDraggedListener() {
            @Override
            public void onProgressDragged(int i) {

            }

            @Override
            public void onProgressDragging(int i) {

            }
        });
        cover.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        cover.start();
    }

}
