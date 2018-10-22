package org.andcreator.andview.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.andcreator.andview.R;
import org.andcreator.andview.option.LProgressButtonOption;
import org.andcreator.andview.uilt.SetTheme;
import org.andcreator.andview.view.LProgressButton;

public class ProgressButtonActivity extends AppCompatActivity {

    private LProgressButton progressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        setContentView(R.layout.activity_progress_button);

        progressButton =  findViewById(R.id.pb);
        LProgressButtonOption.Builder builder = new LProgressButtonOption.Builder();

        builder.setBtnStartText("开始")
                .setBtnEndText("完成")
                .setShowPercent(true)
                .setBtnErrorIcon(R.drawable.ic_lview);

        progressButton.setOption(new LProgressButtonOption(builder));
        progressButton.setClickListener(new LProgressButton.LProgressButtonOnClickListener() {
            @Override
            public void LPBOnClick(LProgressButton btn) {
                switch (btn.getType()) {
                    case LProgressButton.TYPE_END:
                        progressButton.reset();
                        break;
                    case LProgressButton.TYPE_ERROR:
                        progressButton.onEnd();
                        break;
                    case LProgressButton.TYPE_LOADING:
                        progressButton.onError();
                        break;
                    case LProgressButton.TYPE_PREPARE:
                        progressButton.onLoad(100, 100);
                        break;
                    case LProgressButton.TYPE_START:
                        progressButton.onPrepare();
                        // progressButton.onError();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
