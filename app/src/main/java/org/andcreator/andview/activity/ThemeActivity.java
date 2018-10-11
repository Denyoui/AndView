package org.andcreator.andview.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.andcreator.andview.R;
import org.andcreator.andview.uilt.SetTheme;

import java.util.List;

public class ThemeActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private boolean isChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        setContentView(R.layout.activity_theme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        sharedPreferences = getSharedPreferences("item", MODE_PRIVATE);

        Button button1 = (Button) findViewById(R.id.theme_empty_blue);
        Button button2 = (Button) findViewById(R.id.theme_super_violet);
        Button button3 = (Button) findViewById(R.id.theme_google_red);
        Button button4 = (Button) findViewById(R.id.theme_powder);
        Button button5 = (Button) findViewById(R.id.theme_penicillium);
        Button button6 = (Button) findViewById(R.id.theme_bright_yellow);
        Button button7 = (Button) findViewById(R.id.theme_paint);

        if (sharedPreferences.getInt("theme", 0) == R.style.AppTheme_Blue) {
            button1.setText(R.string.button_used);
        } else if (sharedPreferences.getInt("theme", 0) == R.style.AppTheme_Purple) {
            button2.setText(R.string.button_used);
        } else if (sharedPreferences.getInt("theme", 0) == R.style.AppTheme_Red) {
            button3.setText(R.string.button_used);
        } else if (sharedPreferences.getInt("theme", 0) == R.style.AppTheme_Pink) {
            button4.setText(R.string.button_used);
        } else if (sharedPreferences.getInt("theme", 0) == R.style.AppTheme_Green) {
            button5.setText(R.string.button_used);
        } else if (sharedPreferences.getInt("theme", 0) == R.style.AppTheme_Yellow) {
            button6.setText(R.string.button_used);
        } else if (sharedPreferences.getInt("theme", 0) == R.style.AppTheme_Paint) {
            button7.setText(R.string.button_used);
        } else {
            button1.setText(R.string.button_used);
        }

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);

        if (getIntent().getBooleanExtra("isChange",false)){
            Toast.makeText(this, "已切换", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        dealWithIntent();
    }

    /**
     * 判断某一个类是否存在任务栈里面
     *
     * @return
     */
    private boolean isExsitMianActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        ComponentName cmpName = intent.resolveActivity(getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        return flag;
    }

    /**
     * 进行逻辑处理
     */
    public void dealWithIntent() {
        if (isExsitMianActivity(MainActivity.class)) {//存在这个类
            //进行操作
        } else {//不存在这个类
            //进行操作
        }

    }

    @Override
    public void onBackPressed() {
        if (isExsitMianActivity(MainActivity.class)) {//存在这个类

            finish();
        } else {//不存在这个类
            Intent intent = new Intent(ThemeActivity.this, MainActivity.class);
            intent.putExtra("isDestroy", true);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (isExsitMianActivity(MainActivity.class)) {//存在这个类

                    finish();
                } else {//不存在这个类
                    Intent intent = new Intent(ThemeActivity.this, MainActivity.class);
                    intent.putExtra("isDestroy", true);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    finish();
                }
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    @Override
    public void onClick(View v) {

        SharedPreferences.Editor sharedPreferences = getSharedPreferences("item", MODE_PRIVATE).edit();

        switch (v.getId()) {
            case R.id.theme_empty_blue:
                sharedPreferences.putInt("theme", R.style.AppTheme_Blue);
                sharedPreferences.putInt("dialogtheme", R.style.DialogActivity_Blue);
                sharedPreferences.putInt("translucent", R.style.AppTheme_Blue_Translucent);
                sharedPreferences.putInt("start", R.style.AppTheme_Blue_Start);
                break;
            case R.id.theme_super_violet:
                sharedPreferences.putInt("theme", R.style.AppTheme_Purple);
                sharedPreferences.putInt("dialogtheme", R.style.DialogActivity_Purple);
                sharedPreferences.putInt("translucent", R.style.AppTheme_Purple_Translucent);
                sharedPreferences.putInt("start", R.style.AppTheme_Purple_Start);
                break;
            case R.id.theme_google_red:
                sharedPreferences.putInt("theme", R.style.AppTheme_Red);
                sharedPreferences.putInt("dialogtheme", R.style.DialogActivity_Red);
                sharedPreferences.putInt("translucent", R.style.AppTheme_Red_Translucent);
                sharedPreferences.putInt("start", R.style.AppTheme_Red_Start);
                break;
            case R.id.theme_powder:
                sharedPreferences.putInt("theme", R.style.AppTheme_Pink);
                sharedPreferences.putInt("dialogtheme", R.style.DialogActivity_Pink);
                sharedPreferences.putInt("translucent", R.style.AppTheme_Pink_Translucent);
                sharedPreferences.putInt("start", R.style.AppTheme_Pink_Start);
                break;
            case R.id.theme_penicillium:
                sharedPreferences.putInt("theme", R.style.AppTheme_Green);
                sharedPreferences.putInt("dialogtheme", R.style.DialogActivity_Green);
                sharedPreferences.putInt("translucent", R.style.AppTheme_Green_Translucent);
                sharedPreferences.putInt("start", R.style.AppTheme_Green_Start);
                break;
            case R.id.theme_paint:
                sharedPreferences.putInt("theme", R.style.AppTheme_Paint);
                sharedPreferences.putInt("dialogtheme", R.style.DialogActivity_Paint);
                sharedPreferences.putInt("translucent", R.style.AppTheme_Paint_Translucent);
                sharedPreferences.putInt("start", R.style.AppTheme_Paint_Start);
                break;
            case R.id.theme_bright_yellow:
                sharedPreferences.putInt("theme", R.style.AppTheme_Yellow);
                sharedPreferences.putInt("dialogtheme", R.style.DialogActivity_Yellow);
                sharedPreferences.putInt("translucent", R.style.AppTheme_Yellow_Translucent);
                sharedPreferences.putInt("start", R.style.AppTheme_Yellow_Start);
                break;
        }
        sharedPreferences.apply();
        restart();
    }

    private void restart() {

        Intent intent = getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("isChange",true);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
