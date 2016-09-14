package com.dz.gift;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.jaeger.library.StatusBarUtil;

import java.io.IOException;

import co.mobiwise.library.AnimationCompleteListener;
import co.mobiwise.library.MaskProgressView;
import co.mobiwise.library.OnProgressDraggedListener;


public class MusicActivity extends AppCompatActivity implements ServiceConnection{

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

        Intent music = new Intent(this, MusicService.class);
        startService(music);

        doBindService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mServ.pause();
        cover.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mServ != null){
            mServ.resume();
        }
        cover.start();
    }

    // indicates whether the activity is linked to service player.
    private boolean mIsBound = false;
    // Saves the binding instance with the service.
    private MusicService mServ;

    // interface connection with the service activity
    public void onServiceConnected(ComponentName name, IBinder binder)
    {
        mServ = ((MusicService.ServiceBinder) binder).getService();
        mServ.start();
    }

    public void onServiceDisconnected(ComponentName name)
    {
        mServ = null;
    }

    // local methods used in connection/disconnection activity with service.

    public void doBindService() {
        // activity connects to the service.
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    public void doUnbindService(){
        if(mIsBound){
            unbindService(this);
            mIsBound = false;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

}
