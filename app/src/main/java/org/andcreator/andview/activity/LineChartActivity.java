package org.andcreator.andview.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.andcreator.andview.R;
import org.andcreator.andview.option.LLineChartViewOption;
import org.andcreator.andview.uilt.SetTheme;
import org.andcreator.andview.view.LLineChartView;

import java.util.ArrayList;

public class LineChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        setContentView(R.layout.activity_line_chart);

        LLineChartView lLineChartView = findViewById(R.id.linechart);
        LLineChartView lLineChartView2 = findViewById(R.id.linechart2);
        LLineChartViewOption.Builder bu = new LLineChartViewOption.Builder();
        bu.setLable(new String[]{"哈哈","嘻嘻","呵呵","呼呼","拉拉","呜呜"});
        lLineChartView.setOption(new LLineChartViewOption(bu));
        bu.setIsCurve(false);
        lLineChartView2.setOption(new LLineChartViewOption(bu));
        ArrayList<LLineChartView.LLineChartBean> beans = new ArrayList<>();
        ArrayList<LLineChartView.LLineChartBean> beans2 = new ArrayList<>();
        LLineChartView.LLineChartBean b = lLineChartView.new LLineChartBean(new float[]{32,64,95,0,89,54},Color.WHITE);
        LLineChartView.LLineChartBean b2 = lLineChartView.new LLineChartBean(new float[]{85,36,18,56,32,17},Color.GREEN);
        beans.add(b);
        beans.add(b2);
        beans2.add(b2);
        beans2.add(b);
        lLineChartView.setBeans(beans);
        lLineChartView2.setBeans(beans2);
    }
}
