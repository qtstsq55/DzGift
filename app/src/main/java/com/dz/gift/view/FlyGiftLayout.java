package com.dz.gift.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.dz.gift.R;
import com.race604.flyrefresh.PullHeaderLayout;
import com.race604.flyrefresh.internal.MountanScenceView;
import com.race604.flyrefresh.internal.SimpleAnimatorListener;
import com.race604.utils.UIUtils;

/**
 * Created by jing on 15-5-27.
 */
public class FlyGiftLayout extends PullHeaderLayout {

    private AnimatorSet mFlyAnimator = null;
    private OnPullRefreshListener mListener;

    public FlyGiftLayout(Context context) {
        super(context);
        init(context);
    }

    public FlyGiftLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FlyGiftLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public FlyGiftLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundResource(R.mipmap.dandelion);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeaderController.getMaxHeight());
        setHeaderView(imageView, lp);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Set default action icon if user not override
        if (getIconView() == null) {
            setActionDrawable(getResources().getDrawable(R.mipmap.ic_send));
        }
    }

    @Override
    public void onStartRefreshAnimation() {

        if (mFlyAnimator != null) {
            mFlyAnimator.end();
        }

        final View iconView = getIconView();
        UIUtils.clearAnimator(iconView);

        AnimatorSet flyUpAnim = new AnimatorSet();
        flyUpAnim.setDuration(1600);

        ObjectAnimator transX = ObjectAnimator.ofFloat(iconView, "translationX", 0, getWidth());
        ObjectAnimator transY = ObjectAnimator.ofFloat(iconView, "translationY", 0, -mHeaderController.getHeight());
        transY.setInterpolator(PathInterpolatorCompat.create(0.7f, 1f));
        ObjectAnimator rotation = ObjectAnimator.ofFloat(iconView, "rotation", -45, 0);
        rotation.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator rotationX = ObjectAnimator.ofFloat(iconView, "rotationX", 0, 60);
        rotationX.setInterpolator(new DecelerateInterpolator());

        flyUpAnim.playTogether(transX, transY, rotationX,
                ObjectAnimator.ofFloat(iconView, "scaleX", 1, 0.5f),
                ObjectAnimator.ofFloat(iconView, "scaleY", 1, 0.5f),
                rotation
        );

        mFlyAnimator = flyUpAnim;
        mFlyAnimator.start();

        if (mListener != null) {
            mListener.onRefresh(FlyGiftLayout.this);
        }
    }

    public void setOnPullRefreshListener(OnPullRefreshListener listener) {
        mListener = listener;
    }

    public void onRefreshFinish() {
        if (mFlyAnimator != null) {
            mFlyAnimator.cancel();
        }

        final View iconView = getIconView();
        UIUtils.clearAnimator(iconView);

        final int offDistX = -iconView.getRight();
        final int offDistY = -UIUtils.dpToPx(10);
        AnimatorSet flyDownAnim = new AnimatorSet();
        flyDownAnim.setDuration(1600);
        ObjectAnimator transX1 = ObjectAnimator.ofFloat(iconView, "translationX", getWidth(), offDistX);
        ObjectAnimator transY1 = ObjectAnimator.ofFloat(iconView, "translationY", -mHeaderController.getHeight(), offDistY);
        transY1.setInterpolator(PathInterpolatorCompat.create(0.1f, 1f));
        ObjectAnimator rotation1 = ObjectAnimator.ofFloat(iconView, "rotation", iconView.getRotation(), 0);
        rotation1.setInterpolator(new AccelerateInterpolator());
        flyDownAnim.playTogether(transX1, transY1,
                ObjectAnimator.ofFloat(iconView, "scaleX", 0.5f, 0.9f),
                ObjectAnimator.ofFloat(iconView, "scaleY", 0.5f, 0.9f),
                rotation1
        );
        flyDownAnim.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                iconView.setRotationY(180);
            }
        });

        AnimatorSet flyInAnim = new AnimatorSet();
        flyInAnim.setDuration(400);
        flyInAnim.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator tranX2 = ObjectAnimator.ofFloat(iconView, "translationX", offDistX, 0);
        ObjectAnimator tranY2 = ObjectAnimator.ofFloat(iconView, "translationY", offDistY, 0);
        ObjectAnimator rotationX2 = ObjectAnimator.ofFloat(iconView, "rotationX", 30, 0);
        flyInAnim.playTogether(tranX2, tranY2, rotationX2,
                ObjectAnimator.ofFloat(iconView, "scaleX", 0.9f, 1f),
                ObjectAnimator.ofFloat(iconView, "scaleY", 0.9f, 1f));
        flyInAnim.setStartDelay(100);
        flyInAnim.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                iconView.setRotationY(0);
            }
        });

        mFlyAnimator = new AnimatorSet();
        mFlyAnimator.playSequentially(flyDownAnim, flyInAnim);
        mFlyAnimator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mListener != null) {
                    mListener.onRefreshAnimationEnd(FlyGiftLayout.this);
                }
            }
        });
        mFlyAnimator.start();
    }

    @Override
    protected void onMoveHeader(int state, float progress) {
        super.onMoveHeader(state, progress);
        if (mHeaderController.isOverHeight()) {
            getIconView().setRotation((-45) * progress);
        }
    }

    public interface OnPullRefreshListener {
        void onRefresh(FlyGiftLayout view);
        void onRefreshAnimationEnd(FlyGiftLayout view);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setActionDrawable(Drawable actionDrawable) {
        super.setActionDrawable(actionDrawable);
    }
}
