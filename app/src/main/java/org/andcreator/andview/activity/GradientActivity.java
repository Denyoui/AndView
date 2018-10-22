package org.andcreator.andview.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.andcreator.andview.R;
import org.andcreator.andview.adapter.RecyclerGradientButtonAdapter;
import org.andcreator.andview.bean.RecyclerGradientButtonBean;
import org.andcreator.andview.uilt.SetTheme;

import java.util.ArrayList;
import java.util.List;

public class GradientActivity extends AppCompatActivity {

    List<RecyclerGradientButtonBean> mListGroup;

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

        setContentView(R.layout.activity_gradient);

        RecyclerView gradient1 = findViewById(R.id.recycler_gradient1);
        RecyclerView gradient2 = findViewById(R.id.recycler_gradient2);
        RecyclerView gradient3 = findViewById(R.id.recycler_gradient3);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        gradient1.setLayoutManager(layoutManager1);
        gradient2.setLayoutManager(layoutManager2);
        gradient3.setLayoutManager(layoutManager3);
        RecyclerGradientButtonAdapter adapter1 = new RecyclerGradientButtonAdapter(groupList(),0xff5c6bc0,0xffe91e63);
        RecyclerGradientButtonAdapter adapter2 = new RecyclerGradientButtonAdapter(groupList(),0xff7e57c2,0xff42a5f5);
        RecyclerGradientButtonAdapter adapter3 = new RecyclerGradientButtonAdapter(groupList(),0xff009688,0xff8bc34a);
        gradient1.setAdapter(adapter1);
        gradient2.setAdapter(adapter2);
        gradient3.setAdapter(adapter3);
    }


    private List<RecyclerGradientButtonBean> groupList() {
        mListGroup = new ArrayList<>();
        mListGroup.add(new RecyclerGradientButtonBean("排行榜"));
        mListGroup.add(new RecyclerGradientButtonBean("评分最高"));
        mListGroup.add(new RecyclerGradientButtonBean("小编推荐"));
        mListGroup.add(new RecyclerGradientButtonBean("热门"));
        mListGroup.add(new RecyclerGradientButtonBean("创收最高"));
        mListGroup.add(new RecyclerGradientButtonBean("新鲜上架"));
        return mListGroup;
    }

}
