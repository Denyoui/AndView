package org.andcreator.andview.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.andcreator.andview.R;
import org.andcreator.andview.uilt.SetTheme;
import org.andcreator.andview.view.LSlideButtonView;

public class SlideSwitchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        setContentView(R.layout.activity_slide_switch);

        LSlideButtonView buttonView =  findViewById(R.id.b);
        buttonView.setOnSlideListener(new LSlideButtonView.SlideButtonViewListener() {
            @Override
            public void SlideButtonOnClick(LSlideButtonView SlideButtonView, boolean isOpen) {
                Toast.makeText(SlideSwitchActivity.this, isOpen + "", Toast.LENGTH_SHORT).show();
            }
        });
        LSlideButtonView buttonView2 = findViewById(R.id.btn);
        buttonView2.setOnSlideListener(new LSlideButtonView.SlideButtonViewListener() {
            @Override
            public void SlideButtonOnClick(LSlideButtonView SlideButtonView, boolean isOpen) {
                Toast.makeText(SlideSwitchActivity.this, isOpen + "", Toast.LENGTH_SHORT).show();
            }
        });
        LSlideButtonView buttonView3 = findViewById(R.id.btn2);
        buttonView3.setBtnType(false);
        buttonView3.setBtnColor(Color.WHITE);
        buttonView3.setOffColor(Color.GRAY);
        buttonView3.setTextColor(Color.GRAY);
        buttonView3.setOnSlideListener(new LSlideButtonView.SlideButtonViewListener() {
            @Override
            public void SlideButtonOnClick(LSlideButtonView SlideButtonView, boolean isOpen) {
                Toast.makeText(SlideSwitchActivity.this, isOpen + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
