package com.dz.gift;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dz.gift.view.FlyGiftLayout;
import com.race604.flyrefresh.internal.SimpleAnimatorListener;


public class FlyActivity extends AppCompatActivity {

    private Button mBtn;
    private EditText mEditText;
    private FlyGiftLayout mFlyRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly);
        setSystemStatusBg(this);
        mFlyRefreshLayout = (FlyGiftLayout) findViewById(R.id.fly_layout);
        mFlyRefreshLayout.setOnPullRefreshListener(new FlyGiftLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh(FlyGiftLayout view) {
                if(!isEmptyString(mEditText.getText().toString())) {
                    startFly();
                    Toast.makeText(getApplicationContext(), "将愿望折纸飞机寄成信", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mFlyRefreshLayout.onRefreshFinish();
                            Toast.makeText(getApplicationContext(), "童年的纸飞机,现在终于飞回我们手里.....", Toast.LENGTH_LONG).show();
                            endFly();
                        }
                    }, 1600);
                }else{
                    Toast.makeText(getApplicationContext(), "听话!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mFlyRefreshLayout.onRefreshFinish();
                        }
                    }, 1600);
                }
            }

            @Override
            public void onRefreshAnimationEnd(FlyGiftLayout view) {

            }
        });

        mBtn = (Button) findViewById(R.id.complete);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlyRefreshLayout.onStartRefreshAnimation();
            }
        });

        mEditText = (EditText) findViewById(R.id.edit);
    }

    public void setSystemStatusBg(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = activity.getWindow();
            win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public  boolean isEmptyString(String aStr) {
        return aStr == null || "".equals(aStr) || "".equals(aStr.trim())
                || "null".equalsIgnoreCase(aStr);
    }

    private void startFly(){
        AnimatorSet flyUpAnim = new AnimatorSet();
        flyUpAnim.setDuration(1600);
        flyUpAnim.playTogether(ObjectAnimator.ofFloat(mEditText, "scaleX", 1, 0f),ObjectAnimator.ofFloat(mEditText, "scaleY", 1, 0f));
        flyUpAnim.start();
    }

    private void endFly(){
        AnimatorSet flyUpAnim = new AnimatorSet();
        flyUpAnim.setDuration(1600);
        mEditText.setEnabled(false);
        mEditText.setText("你,是我唯一坚持的任性!");
        mEditText.setTextSize(getResources().getDimension(R.dimen.dn));
        mEditText.setGravity(Gravity.CENTER);
        flyUpAnim.playTogether(ObjectAnimator.ofFloat(mEditText, "scaleX", 0f, 1f),ObjectAnimator.ofFloat(mEditText, "scaleY", 0f, 1f));
        flyUpAnim.addListener(new SimpleAnimatorListener(){
            @Override
            public void onAnimationEnd(Animator animation) {
                gotoStarActivity();
            }
        });
        flyUpAnim.start();
    }

    private void gotoStarActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(FlyActivity.this, StarActivity.class));
                finish();
            }
        },500);
    }

}
