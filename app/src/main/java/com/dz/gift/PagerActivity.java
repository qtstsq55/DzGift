package com.dz.gift;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dz.gift.view.Love;
import com.dz.gift.view.NaviIndicatorView;
import com.dz.gift.view.SVG;
import com.dz.gift.view.SaverUnlockTextView;
import com.github.ybq.parallaxviewpager.ParallaxViewPager;
import com.jrummyapps.android.widget.AnimatedSvgView;

public class PagerActivity extends AppCompatActivity {

    private ParallaxViewPager mParallaxViewPager;
    private NaviIndicatorView indicatorView;

    private int[] mImages = new int[]{R.mipmap.q1,R.mipmap.q2,R.mipmap.q3,R.mipmap.d1,R.mipmap.d2,R.mipmap.d3,R.mipmap.qd1,R.mipmap.qd2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        setSystemStatusBg(this);
        getSupportActionBar().hide();
        mParallaxViewPager = (ParallaxViewPager) findViewById(R.id.viewpager);
        initViewPager();
        initAnimationView();
    }

    private void initViewPager() {
        PagerAdapter adapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,Object obj) {
                container.removeView((View) obj);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = null;
                if(position < mImages.length) {
                    view = View.inflate(container.getContext(), R.layout.pager_item, null);
                    ImageView imageView = (ImageView) view.findViewById(R.id.item_img);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(PagerActivity.this).load(mImages[position % mImages.length]).into(imageView);
                }else{
                    view = View.inflate(container.getContext(), R.layout.item_svg, null);
                    AnimatedSvgView animatedSvgView = (AnimatedSvgView) view.findViewById(R.id.animated_svg_view);
                    SaverUnlockTextView textView = (SaverUnlockTextView) view.findViewById(R.id.svg_tv);
                    textView.setEffectColor(getResources().getColor(R.color.normal_bg));
                    setSvg(animatedSvgView,SVG.LOVE);
                }
                container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                return view;
            }

            @Override
            public int getCount() {
                return mImages.length + 1;
            }
        };
        mParallaxViewPager.setAdapter(adapter);
    }

    private void initAnimationView() {
        indicatorView = (NaviIndicatorView) findViewById(R.id.animation_indicator_view);
        indicatorView.setCirclesCounts(mImages.length + 1);
        indicatorView.setCircleRadius(getResources().getDimensionPixelSize(R.dimen.indicator_radius));
        indicatorView.setCircleStoken(getResources().getDimensionPixelSize(R.dimen.indicator_margin));

        mParallaxViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (indicatorView != null) {
                    indicatorView.onScroll(position, positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setSystemStatusBg(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = activity.getWindow();
            win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void setSvg(final AnimatedSvgView svgView, SVG svg) {
        svgView.setGlyphStrings(svg.glyphs);
        svgView.setFillColors(svg.colors);
        svgView.setViewportSize(svg.width, svg.height);
        svgView.setTraceResidueColor(0x32000000);
        svgView.setTraceColors(svg.colors);
        svgView.rebuildGlyphData();
        svgView.setOnStateChangeListener(new AnimatedSvgView.OnStateChangeListener() {
            @Override
            public void onStateChange(int state) {
                if(state == AnimatedSvgView.STATE_FINISHED){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gotoFlyActivity();
                        }
                    },600);
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                svgView.start();
            }
        },500);
    }

    private void gotoFlyActivity(){
        startActivity(new Intent(this, FlyActivity.class));
        finish();
    }


}
