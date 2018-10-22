package org.andcreator.andview.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.andcreator.andview.R;
import org.andcreator.andview.adapter.RecyclerAppsAdapter;
import org.andcreator.andview.adapter.RecyclerMainLayoutAdapter;
import org.andcreator.andview.bean.RecyclerAppsBean;
import org.andcreator.andview.bean.RecyclerMainLayoutBean;
import org.andcreator.andview.uilt.SetTheme;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContributorActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private TextView titleName;
    private TextView text;

    private RelativeLayout mTitleContainer;
    AppBarLayout mAppBarLayout;

    private ArrayList<RecyclerAppsBean> mListApps;
    private ArrayList<RecyclerMainLayoutBean> mListProject;

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

        setContentView(R.layout.activity_contributor);

        initView();
}

    private void initView() {
        mAppBarLayout = findViewById(R.id.appBar_layout);
        mAppBarLayout.addOnOffsetChangedListener(this);
        text = findViewById(R.id.text);

        Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout background = findViewById(R.id.collapsing);
        ImageView titleImage = findViewById(R.id.background);
        CircleImageView icon = findViewById(R.id.icon);
        TextView name = findViewById(R.id.name);
        TextView motto = findViewById(R.id.motto);
        titleName = findViewById(R.id.title_name);
        mTitleContainer = findViewById(R.id.title_container);
        RecyclerView appsRecycler = findViewById(R.id.apps_recycler);
        RecyclerView projectRecycler = findViewById(R.id.project_recycler);

        LinearLayoutManager appsLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        appsRecycler.setLayoutManager(appsLayoutManager);
        RecyclerAppsAdapter appsAdapter = new RecyclerAppsAdapter(listApps(intent.getStringExtra("name")),this);
        appsRecycler.setAdapter(appsAdapter);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        projectRecycler.setLayoutManager(layoutManager);
        RecyclerMainLayoutAdapter adapter = new RecyclerMainLayoutAdapter(this,getLayoutInflater(),loadProject(intent.getStringExtra("name")),intent.getIntExtra("color",0xff5163cc));
        projectRecycler.setAdapter(adapter);

        Glide.with(this).load(R.drawable.logo).into(titleImage);
        Glide.with(this).load(intent.getIntExtra("icon",R.drawable.ic_group_attrs_24dp)).into(icon);
        name.setText(intent.getStringExtra("name"));
        titleName.setText(intent.getStringExtra("name"));
        motto.setText(intent.getStringExtra("motto"));
        background.setBackgroundColor(intent.getIntExtra("color",0xff5163cc));

        startAlphaAnimation(titleName, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
        mIsTheTitleVisible = true;

    }

    private List<RecyclerAppsBean> listApps(String name) {
        mListApps = new ArrayList<>();
        switch (name){
            case "and":
                mListApps.add(new RecyclerAppsBean(R.drawable.app_1,"任务卡片","https://www.coolapk.com/apk/org.andcreator.taskcard"));
                mListApps.add(new RecyclerAppsBean(R.drawable.app_2,"Marshmallow图标包","https://www.coolapk.com/apk/org.andcreator.marshmallow"));
                mListApps.add(new RecyclerAppsBean(R.drawable.app_3,"异原谅图标包","https://www.coolapk.com/apk/com.realityart.greenicon"));
                break;
            case "Lollipop":
                mListApps.add(new RecyclerAppsBean(R.drawable.app_4,"小屏保","https://www.coolapk.com/apk/liang.lollipop.ldream"));
                mListApps.add(new RecyclerAppsBean(R.drawable.app_5,"灰色空间","https://www.coolapk.com/apk/liang.lollipop.screenhelper"));
                mListApps.add(new RecyclerAppsBean(R.drawable.app_6,"守护者","https://www.coolapk.com/apk/com.liang.lollipop.lreader"));
                mListApps.add(new RecyclerAppsBean(R.drawable.app_7,"数独酷","https://www.coolapk.com/game/liang.lollipop.lsudoku"));
                mListApps.add(new RecyclerAppsBean(R.drawable.app_8,"倒计时","https://www.coolapk.com/apk/liang.lollipop.lcountdown"));
                mListApps.add(new RecyclerAppsBean(R.drawable.app_9,"二维码","https://www.coolapk.com/apk/liang.lollipop.lqrdemo"));
                mListApps.add(new RecyclerAppsBean(R.drawable.app_10,"LTool","https://www.coolapk.com/apk/xiaoliang.ltool"));
                mListApps.add(new RecyclerAppsBean(R.drawable.app_11,"指南针","https://www.coolapk.com/apk/com.liang.lollipop.lcompass"));
                break;
            case "Night Farmer":
                text.setVisibility(View.GONE);
                break;
                default:
                    break;
        }
        return mListApps;
    }

    private List<RecyclerMainLayoutBean> loadProject(String name) {
        mListProject = new ArrayList<>();
        switch (name){
            case "and":
                mListProject.add(new RecyclerMainLayoutBean("ObjectAnimator", "ObjectAnimator的简单使用演示", new int[]{R.drawable.and},14));
                mListProject.add(new RecyclerMainLayoutBean("Android助手应用[长按Home键]", "助手应用功能的简单使用演示，不建议用于全屏应用", new int[]{R.drawable.and},15));
                mListProject.add(new RecyclerMainLayoutBean("捕获屏幕内容", "调用系统截屏", new int[]{R.drawable.and},16));
                mListProject.add(new RecyclerMainLayoutBean("获取图片主题颜色", "Palette库的简单使用", new int[]{R.drawable.and},18));
                mListProject.add(new RecyclerMainLayoutBean("将视频作为壁纸", "使用一段视频作为桌面壁纸", new int[]{R.drawable.and},19));
                mListProject.add(new RecyclerMainLayoutBean("对话界面","简单的模仿环聊对话界面",new int[]{R.drawable.and},1));
                mListProject.add(new RecyclerMainLayoutBean("ViewPager动画","实现ViewPager两边小中间大的动画效果",new int[]{R.drawable.and},2));
                mListProject.add(new RecyclerMainLayoutBean("ScrollingLayout","一个属性即可实现炫酷的Material Design设计",new int[]{R.drawable.and},4));
                mListProject.add(new RecyclerMainLayoutBean("系列按钮渐变","模仿桌面版Google Plus渐变按钮",new int[]{R.drawable.and},6));
                mListProject.add(new RecyclerMainLayoutBean("Google+的图片文字列表","模仿桌面版Google Plus图片文字Item",new int[]{R.drawable.and},9));
                mListProject.add(new RecyclerMainLayoutBean("Material Design 登录与注册","以屏幕中心的单个卡片为主题的登录界面",new int[]{R.drawable.and},10));
                mListProject.add(new RecyclerMainLayoutBean("图标包", "转至桌面设置应用图标包，仅支持部分Launcher", new int[]{R.drawable.and},40));
                break;
            case "Lollipop":
                mListProject.add(new RecyclerMainLayoutBean("ViewPager滚动时改变颜色","监听ViewPager并实时改变背景颜色",new int[]{R.drawable.lollipop},5));
                mListProject.add(new RecyclerMainLayoutBean("Pixel Launcher效果","模仿Pixel Launcher以及获取应用程序列表",new int[]{R.drawable.lollipop},7));
                mListProject.add(new RecyclerMainLayoutBean("Material 顶部布局折叠菜单","点击后下滑整个布局出现选项菜单",new int[]{R.drawable.lollipop},8));
                mListProject.add(new RecyclerMainLayoutBean("打印程序Log并保持到Sdcard", "捕获Logcat，可用于反馈bug", new int[]{R.drawable.lollipop},17));
                mListProject.add(new RecyclerMainLayoutBean("指针时间选择器","表盘风格的时间选择器",new int[]{R.drawable.lollipop},20));
                mListProject.add(new RecyclerMainLayoutBean("加载等待动画","3个错开时间的圆环组成的动画",new int[]{R.drawable.lollipop},21));
                mListProject.add(new RecyclerMainLayoutBean("饼图","圆盘形的数据统计图",new int[]{R.drawable.lollipop},22));
                mListProject.add(new RecyclerMainLayoutBean("圆环进度图","圆润的进度显示器",new int[]{R.drawable.lollipop},23));
                mListProject.add(new RecyclerMainLayoutBean("雷达图","雷达形统计图",new int[]{R.drawable.lollipop},24));
                mListProject.add(new RecyclerMainLayoutBean("滑动开关","定制滑动开关",new int[]{R.drawable.lollipop},25));
                mListProject.add(new RecyclerMainLayoutBean("温度计","模拟温度计进度",new int[]{R.drawable.lollipop},26));
                mListProject.add(new RecyclerMainLayoutBean("进度条按钮","点击按钮变为进度条显示进度",new int[]{R.drawable.lollipop},27));
                mListProject.add(new RecyclerMainLayoutBean("ViewPager下方指示器","ViewPager下方指示器",new int[]{R.drawable.lollipop},28));
                mListProject.add(new RecyclerMainLayoutBean("Tab顶部小点指示器","Tab顶部小点指示器",new int[]{R.drawable.lollipop},29));
                mListProject.add(new RecyclerMainLayoutBean("Tab顶部条形指示器","Tab顶部条形指示器",new int[]{R.drawable.lollipop},30));
                mListProject.add(new RecyclerMainLayoutBean("滚动选择器","自定义上下滚动选择数据",new int[]{R.drawable.lollipop},31));
                mListProject.add(new RecyclerMainLayoutBean("联系人", "获取系统联系人数据", new int[]{R.drawable.lollipop},32));
                mListProject.add(new RecyclerMainLayoutBean("水滴加载动画","贝塞尔曲线无限滚动",new int[]{R.drawable.lollipop},33));
                mListProject.add(new RecyclerMainLayoutBean("折线图","继承自TextView的折线图",new int[]{R.drawable.lollipop},34));
                mListProject.add(new RecyclerMainLayoutBean("刮刮卡","捕获手指涂抹，模拟刮刮卡",new int[]{R.drawable.lollipop},35));
                mListProject.add(new RecyclerMainLayoutBean("带动画的返回箭头","-^-<-^-",new int[]{R.drawable.lollipop},36));
                break;
            case "Night Farmer":
                mListProject.add(new RecyclerMainLayoutBean("RecyclerView堆叠滚动","实现卡片堆叠滚动的效果",new int[]{R.drawable.night_farmer},3));
                mListProject.add(new RecyclerMainLayoutBean("高斯模糊", "对图片进行高斯模糊处理", new int[]{R.drawable.night_farmer},13));
                mListProject.add(new RecyclerMainLayoutBean("涟漪扩散效果","定时循环向外扩散涟漪",new int[]{R.drawable.night_farmer},11));
                mListProject.add(new RecyclerMainLayoutBean("卫星菜单","适用于RelativeLayout的卫星菜单，支持各种方向",new int[]{R.drawable.night_farmer},12));
                mListProject.add(new RecyclerMainLayoutBean("过渡色环形圆角进图条","过渡色环形圆角进图条",new int[]{R.drawable.night_farmer},37));
                mListProject.add(new RecyclerMainLayoutBean("轮子","仅仅是一个轮子",new int[]{R.drawable.night_farmer},38));
                mListProject.add(new RecyclerMainLayoutBean("显现/隐藏TextView","随机字符显现/隐藏的TextView",new int[]{R.drawable.night_farmer},39));
                mListProject.add(new RecyclerMainLayoutBean("进度条按钮","镂空的文字反色的加载进度按钮",new int[]{R.drawable.night_farmer},41));
                mListProject.add(new RecyclerMainLayoutBean("3D环形旋转","3D环形旋转效果",new int[]{R.drawable.night_farmer},42));
//                mListProject.add(new RecyclerMainLayoutBean());
                break;
            default:
                break;
        }
        return mListProject;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(titleName, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                titleName.setVisibility(View.VISIBLE);
                startAlphaAnimation(titleName, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
