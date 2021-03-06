package org.andcreator.andview.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.andcreator.andview.R;
import org.andcreator.andview.uilt.SetTheme;
import org.andcreator.andview.view.RandomShowTextView;

public class RandomShowTextActivity extends AppCompatActivity {

    private RandomShowTextView text;
    private Boolean isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        setContentView(R.layout.activity_random_show_text);

        isShow = true;
        text = findViewById(R.id.text);
        Button toggle = findViewById(R.id.toggle);
        text.show();
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow){
                    text.hide();
                    isShow = !isShow;
                }else {
                    text.show();
                    isShow = !isShow;
                }
            }
        });
    }
}
