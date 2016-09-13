package com.dz.gift;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.yalantis.starwars.TilesFrameLayout;
import com.yalantis.starwars.interfaces.TilesFrameLayoutListener;


public class StarActivity extends Activity {

    private TilesFrameLayout mTilesFrameLayout;
    private TextView tv_1,tv_2,tv_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        StatusBarUtil.setColor(this, Color.parseColor("#020202"));
        mTilesFrameLayout  = (TilesFrameLayout) findViewById(R.id.tessellation_frame_layout);
        mTilesFrameLayout.setOnAnimationFinishedListener(new TilesFrameLayoutListener() {
            @Override
            public void onAnimationFinished() {

            }
        });

        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
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

}
