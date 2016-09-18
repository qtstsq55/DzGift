package com.dz.gift.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.TextView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SaverUnlockTextView extends TextView implements ValueAnimator.AnimatorUpdateListener {

    private static final int TEXT_EFFECT_COLOR = 0xFF66E0F3;
    private static final int TEXT_NORMAL_COLOR = 0xffffffff;
    private static final int REFRESH_TIME = 2250;

    private int effectColor = TEXT_EFFECT_COLOR;

	private LinearGradient mLinearGradient;
	private Matrix mGradientMatrix;
	private int mViewWidth = 0;
	private int mTranslate = 0;
    private ValueAnimator mAnimator;

    public SaverUnlockTextView(Context context) {
        super(context);
        init();
    }

	public SaverUnlockTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
        init();
	}

    public SaverUnlockTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void stopAnimation(){
        if(mAnimator.isStarted()){
            mAnimator.cancel();
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void startAnimation(){
        if(!mAnimator.isStarted()){
            mAnimator.start();
        }
    }

    private void init(){
        mGradientMatrix = new Matrix();
        mAnimator = ObjectAnimator.ofFloat(0, 3);
        mAnimator.setDuration(REFRESH_TIME);
        mAnimator.setRepeatCount(Animation.INFINITE);
        mAnimator.addUpdateListener(this);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float progress= (Float)valueAnimator.getAnimatedValue();
        mTranslate = (int) (mViewWidth * progress);
        postInvalidate();
    }

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = getMeasuredWidth() / 2;
        if (mViewWidth > 0) {
            Paint paint = getPaint();
            mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0,
                                                  new int[] {effectColor , TEXT_NORMAL_COLOR , effectColor },
                                                  new float[] { 0, 0.5f, 1 },
                                                  Shader.TileMode.CLAMP);
            paint.setShader(mLinearGradient);
        }
        startAnimation();
	}

	@Override
	protected void onDraw(Canvas canvas) {
        mGradientMatrix.setTranslate(mTranslate, 0);
        mLinearGradient.setLocalMatrix(mGradientMatrix);
        super.onDraw(canvas);
	}

    public void setEffectColor(int effectColor) {
        this.effectColor = effectColor;
        mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0,
                new int[] {effectColor , TEXT_NORMAL_COLOR , effectColor },
                new float[] { 0, 0.5f, 1 },
                Shader.TileMode.CLAMP);
    }
}
