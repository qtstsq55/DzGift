package com.dz.gift;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.dz.gift.view.FlakeView;
import com.dz.gift.view.SaverUnlockTextView;
import com.dz.gift.view.ScratchImageView;
import com.jaeger.library.StatusBarUtil;
import com.yalantis.starwars.TilesFrameLayout;
import com.yalantis.starwars.interfaces.TilesFrameLayoutListener;


public class StarActivity extends Activity {

    private TilesFrameLayout mTilesFrameLayout;
    private TextView tv_1,tv_2,tv_3;
    private SaverUnlockTextView tv_prize;
    private ScratchImageView mScratchImageView;
    private FlakeView flakeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        setSystemStatusBg(this);
        mTilesFrameLayout  = (TilesFrameLayout) findViewById(R.id.tessellation_frame_layout);
        mTilesFrameLayout.setOnAnimationFinishedListener(new TilesFrameLayoutListener() {
            @Override
            public void onAnimationFinished() {

            }
        });


        flakeView = new FlakeView(this);

        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_prize = (SaverUnlockTextView) findViewById(R.id.prize);
        tv_2.setAlpha(0f);
        Animator animator1 = ObjectAnimator.ofFloat(tv_1,"Alpha",0f,1f);
        animator1.setDuration(5000);
        animator1.setInterpolator(new AccelerateDecelerateInterpolator());
        animator1.start();

        Animator animator2 = ObjectAnimator.ofFloat(tv_2,"Alpha",0f,1f);
        animator2.setDuration(5000);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.setStartDelay(4000);
        animator2.start();

        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTilesFrameLayout.startAnimation();
                StatusBarUtil.setColor(StarActivity.this, getResources().getColor(R.color.normal_bg),0);
            }
        });
        SpannableString spannableString = new SpannableString("好");
        spannableString.setSpan(new UnderlineSpan(), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv_3.setText(spannableString);
        tv_3.setAlpha(0f);
        Animator animator3 = ObjectAnimator.ofFloat(tv_3,"Alpha",0f,1f);
        animator3.setDuration(2000);
        animator3.setStartDelay(9000);
        animator3.start();

        mScratchImageView = (ScratchImageView) findViewById(R.id.srca_im);
        mScratchImageView.setRevealListener(new ScratchImageView.IRevealListener() {
            @Override
            public void onRevealed(ScratchImageView tv) {
                tv_prize.setEffectColor(Color.parseColor("#FF3003"));
                tv_prize.setText("Happy Birthday!");
                flakeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                ((ViewGroup)findViewById(R.id.con)).addView(flakeView);
                flakeView.resume();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flakeView.pause();
                        startActivity(new Intent(StarActivity.this,MusicActivity.class));
                        finish();
                    }
                },8000);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTilesFrameLayout.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTilesFrameLayout.onPause();
    }

    public void setSystemStatusBg(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = activity.getWindow();
            win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
