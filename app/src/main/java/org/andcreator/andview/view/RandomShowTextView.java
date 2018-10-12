package org.andcreator.andview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

public class RandomShowTextView extends android.support.v7.widget.AppCompatTextView {

    private int mDuration = 2500;
    private double[] mAlphas;
    private String mTextString;

    ValueAnimator animator;
    ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Float percent = (Float)valueAnimator.getAnimatedValue();
            resetSpannableString(percent);
            resetSpannableString(mIsVisible ? percent : 2.0f - percent);
        }
    };

    public RandomShowTextView(Context context) {
        super(context);
        init();
    }

    public RandomShowTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        this.mIsVisible = false;
        animator = ValueAnimator.ofFloat(0.0f, 2.0f);
        animator.addUpdateListener(listener);
        animator.setDuration(mDuration);
    }

    private boolean mIsReset = false;
    private void resetSpannableString(double percent){
        SpannableString mSpannableString = new SpannableString(this.mTextString);
        int color = getCurrentTextColor();
        for(int i=0; i < mSpannableString.length(); i++){
            mSpannableString.setSpan(
                    new ForegroundColorSpan(Color.argb(clamp(mAlphas[i] + percent), Color.red(color), Color.green(color), Color.blue(color))), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mIsReset = true;
        setText(mSpannableString);
        invalidate();
    }

    private int clamp(double f){
        return (int)(255*Math.min(Math.max(f, 0), 1));
    }

    private void resetAlphas(int length){
        mAlphas = new double[length];
        for(int i=0; i < mAlphas.length; i++){
            mAlphas[i] = Math.random()-1;
        }
    }

    private void resetIfNeeded(){
        if (!mIsReset){
            mTextString = getText().toString();
            resetAlphas(mTextString.length());
            resetSpannableString(0);
            mIsReset = false;
        }
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        super.setText(text, type);
        mTextString = String.valueOf(text);
        resetIfNeeded();
    }

    private boolean mIsVisible;
    public void toggle(){
        if (mIsVisible) {
            hide();
        } else {
            show();
        }
    }

    public void show(){
        mIsVisible = true;
        animator.start();
    }

    public void hide(){
        mIsVisible = false;
        animator.start();
    }
}
