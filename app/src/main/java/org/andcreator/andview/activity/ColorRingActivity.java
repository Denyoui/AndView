package org.andcreator.andview.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import org.andcreator.andview.R;
import org.andcreator.andview.uilt.SetTheme;
import org.andcreator.andview.view.ColorRingView;
import org.andcreator.andview.view.UnclosedColorRingView;

public class ColorRingActivity extends AppCompatActivity {

    private UnclosedColorRingView unclosedColorRingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_color_ring);

        ImageView shapeRing = findViewById(R.id.shape_ring);
        ColorRingView colorRingView = findViewById(R.id.view_ring);
        unclosedColorRingView = findViewById(R.id.view_unclosed_ring);

        rotation(shapeRing);
        rotation(colorRingView);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1, 0);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                unclosedColorRingView.setProgress(value);
            }
        });
        valueAnimator.start();
    }

    private void rotation(View view){
        ObjectAnimator rotation1 = ObjectAnimator.ofFloat(view, "rotation", -360);
        rotation1.setInterpolator(new LinearInterpolator());
        rotation1.setRepeatCount(ValueAnimator.INFINITE);
        rotation1.setDuration(1500);
        rotation1.start();
    }
}
