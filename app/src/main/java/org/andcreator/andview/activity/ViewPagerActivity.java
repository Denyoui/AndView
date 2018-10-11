package org.andcreator.andview.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.andcreator.andview.R;
import org.andcreator.andview.uilt.SetTheme;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<View> mListView;
    private ViewPageAdapter mAdapter;
    private TextView pagerNumber;
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        setContentView(R.layout.activity_view_pager);
        mListView = new ArrayList<>();
        mViewPager = findViewById(R.id.view_pager);
        pagerNumber = findViewById(R.id.pager_number);

//Disable clip to padding
        mViewPager.setClipToPadding(false);
//set padding manually, the more you set the padding the more you see of prev & next page
        mViewPager.setPadding(200, 0, 200, 0);
//sets a margin b/w individual pages to ensure that there is a gap b/w them
        mViewPager.setPageMargin(70);
//      这个值主要负责两边控件与中间控件的距离
//        mViewPager.setPageMargin(-300);
        mViewPager.setPageTransformer(false, new DepthPageTransformer());

        for (int i = 0; i < 5; i++) {
            View viewItem = LayoutInflater.from(this).inflate(R.layout.pager_item, null);
            CardView cardView = viewItem.findViewById(R.id.card);
            ImageView communityImage = (ImageView) viewItem.findViewById(R.id.community_image);

            if (i == 0){
                Glide.with(this).load(R.drawable.img).into(communityImage);
            }else if (i == 1){
                Glide.with(this).load(R.drawable.img).into(communityImage);
            }else if (i == 2){
                Glide.with(this).load(R.drawable.img).into(communityImage);
            }else if (i == 3){
                Glide.with(this).load(R.drawable.img).into(communityImage);
            }else if (i == 4){
                Glide.with(this).load(R.drawable.img).into(communityImage);
            }
            mListView.add(viewItem);
        }

        mAdapter = new ViewPageAdapter(mListView);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        if (number > 0){
                            backAnimator(2,1);
                        }
                        number = 0;

                        break;
                    case 1:
                        if (number > 1){
                            backAnimator(3,2);
                        }else {
                            goAnimator(1,2);
                        }
                        number = 1;

                        break;
                    case 2:
                        if (number > 2){
                            backAnimator(4,3);
                        }else {
                            goAnimator(2,3);
                        }
                        number = 2;

                        break;
                    case 3:
                        if (number > 3){
                            backAnimator(5,4);
                        }else {
                            goAnimator(3,4);
                        }
                        number = 3;
                        break;
                    case 4:
                        if (number > 4){
                            backAnimator(6,5);
                        }else {
                            goAnimator(4,5);
                        }
                        number = 5;
                        break;
                        default:
                            break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //ViewPager正向滑动文字动画
    private void goAnimator(int start, final int end){
        ObjectAnimator animator = ObjectAnimator.ofFloat(pagerNumber, "translationY", 0,260,260,260);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(pagerNumber, "alpha", 1,0);
        animator.setDuration(300);
        animator2.setDuration(300);
        animator.start();
        animator2.start();


        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                pagerNumber.setText(end+"");
                ObjectAnimator animator = ObjectAnimator.ofFloat(pagerNumber, "translationY", -260,0,0,0);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(pagerNumber, "alpha", 0,1);
                animator.setDuration(400);
                animator2.setDuration(400);
                animator.start();
                animator2.start();
            }
        });
    }

    //ViewPager反向滑动文字动画
    private void backAnimator(int start, final int end){
        ObjectAnimator animator = ObjectAnimator.ofFloat(pagerNumber, "translationY", 0,-260,-260,-260);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(pagerNumber, "alpha", 1,0);
        animator.setDuration(300);
        animator2.setDuration(300);
        animator.start();
        animator2.start();

        animator.addListener(new AnimatorListenerAdapter() {
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                pagerNumber.setText(end+"");

                ObjectAnimator animator = ObjectAnimator.ofFloat(pagerNumber, "translationY", 260,0,0,0);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(pagerNumber, "alpha", 0,1);
                animator.setDuration(400);
                animator2.setDuration(400);
                animator.start();
                animator2.start();
            }
        });
    }

    //动态添加View的ViewPager Adapter
    class ViewPageAdapter extends PagerAdapter {
        private List<View> list_view;

        public ViewPageAdapter(List<View> list_view) {
            this.list_view = list_view;
        }

        @Override
        public int getCount() {
            return list_view.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

//        @Override
//        public float getPageWidth(int position) {
//            return (float) 0.6;
//        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(list_view.get(position));
            return list_view.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list_view.get(position));
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.8f;
        private static final float MIN_ALPHA = 0.6f;

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @SuppressLint("NewApi")
        public void transformPage(View view, float position) {
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setScaleY(MIN_SCALE);
                view.setAlpha(MIN_ALPHA);
            } else if (position == 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setScaleY(MIN_SCALE);
                view.setAlpha(MIN_ALPHA);
            } else if (position <= 1) { // (0,1]
                // Fade the page out.

                // Counteract the default slide transition

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)* (1 - Math.abs(position));
                float alphaFactor = MIN_ALPHA + (1 - MIN_ALPHA)* (1 - Math.abs(position));
                view.setScaleY(scaleFactor);
                view.setAlpha(alphaFactor);
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setScaleY(MIN_SCALE);
                view.setAlpha(MIN_ALPHA);
            }
        }
    }
}
