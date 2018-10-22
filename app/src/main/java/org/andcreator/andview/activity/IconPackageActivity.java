package org.andcreator.andview.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.andcreator.andview.R;
import org.andcreator.andview.adapter.RecyclerAppsAdapter;
import org.andcreator.andview.adapter.RecyclerIconsAdapter;
import org.andcreator.andview.bean.RecyclerIconsBean;
import org.andcreator.andview.uilt.SetTheme;

import java.util.ArrayList;
import java.util.List;

public class IconPackageActivity extends AppCompatActivity {

    private List<RecyclerIconsBean> mListIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        setContentView(R.layout.activity_icon_package);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView icons = findViewById(R.id.icon_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        icons.setLayoutManager(layoutManager);
        RecyclerIconsAdapter adapter = new RecyclerIconsAdapter(load());
        icons.setAdapter(adapter);
    }

    private List<RecyclerIconsBean> load() {
        mListIcon = new ArrayList<>();
        mListIcon.add(new RecyclerIconsBean("Chrome",R.drawable.ic_chrome));
        mListIcon.add(new RecyclerIconsBean("电话",R.drawable.ic_dialer));
        mListIcon.add(new RecyclerIconsBean("短信",R.drawable.ic_message));
        mListIcon.add(new RecyclerIconsBean("设置",R.drawable.ic_settings));
        return mListIcon;
    }

}
