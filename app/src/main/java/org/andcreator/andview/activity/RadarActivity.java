package org.andcreator.andview.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.andcreator.andview.R;
import org.andcreator.andview.uilt.SetTheme;
import org.andcreator.andview.view.LRadarView;

import java.util.ArrayList;
import java.util.HashMap;

public class RadarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        setContentView(R.layout.activity_radar);

        LRadarView radarView = findViewById(R.id.r);
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 7; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put(LRadarView.VALUE_NAME, "小建" + i + "号");
            map.put(LRadarView.VALUE_PERCENT, i * 20);
            map.put(LRadarView.VALUE_VALUE, i * 20);
            list.add(map);
        }
        radarView.setDataArray(list);
    }
}
