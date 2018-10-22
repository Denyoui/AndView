package org.andcreator.andview.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.andcreator.andview.R;
import org.andcreator.andview.adapter.RecyclerOpenAppsAdapter;
import org.andcreator.andview.bean.RecyclerOpenAppsBean;
import org.andcreator.andview.uilt.SetTheme;

import java.util.ArrayList;
import java.util.List;

public class OpenSourceActivity extends AppCompatActivity {

    private List<RecyclerOpenAppsBean> mListApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_open_source);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView openApps = findViewById(R.id.open_apps_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        openApps.setLayoutManager(layoutManager);
        RecyclerOpenAppsAdapter appsAdapter = new RecyclerOpenAppsAdapter(loadApps());
        openApps.setAdapter(appsAdapter);

    }

    private List<RecyclerOpenAppsBean> loadApps() {
        mListApps = new ArrayList<>();
        mListApps.add(new RecyclerOpenAppsBean(R.drawable.app_12,"Amaze File Manager","简单、有吸引力、漂亮、支持root，是一款强大开源的文件管理器","https://github.com/TeamAmaze/AmazeFileManager","com.amaze.filemanager"));
        mListApps.add(new RecyclerOpenAppsBean(R.drawable.app_13,"强力检测","实时检测手机CPU、RAM、ROM、电池等，是一款美丽以及强大的工具","","com.amaze.filemanager"));
        mListApps.add(new RecyclerOpenAppsBean(R.drawable.app_14,"Ribbble","Rippple是一款非常漂亮的软件，设计师的天堂",""," mathieumaree.rippple"));
        mListApps.add(new RecyclerOpenAppsBean(R.drawable.app_15,"Plaid","Plaid是来自谷歌的一名开发者引导员制作的一款设计师新闻应用，可以查看Designer News、Dribbble、Product Hunt中的新鲜内容","https://github.com/nickbutcher/plaid","io.plaidapp"));
        mListApps.add(new RecyclerOpenAppsBean(R.drawable.app_16,"Root Explorer","root文件管理器，一款全能的文件管理器，简单强大","","com.speedsoftware.rootexplorer"));
        mListApps.add(new RecyclerOpenAppsBean(R.drawable.app_17,"3C Toolbox Pro","Android上一款非常强大的控制工具","","ccc71.at"));
        mListApps.add(new RecyclerOpenAppsBean(R.drawable.app_18,"Designer Tools","开源的设计师工具","https://github.com/0xD34D/DesignerTools","com.scheffsblend.designertools"));
        mListApps.add(new RecyclerOpenAppsBean(R.drawable.app_19,"照片编辑器","一款功能与体积成反比的强大图片编辑器","","com.iudesk.android.photo.editor"));
        mListApps.add(new RecyclerOpenAppsBean(R.drawable.app_20,"Advanced Download Manager","非常强大的多线程下载管理器","","com.dv.adm.pay"));
        return mListApps;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_open_apps, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                    finish();
                break;
            case R.id.prompt:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("注意");
                builder.setMessage("您所看到的这些应用不会给开发者带来任何收益，纯属开发者自己推荐.");
                builder.setPositiveButton("了解", null);
                builder.show();
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


}
