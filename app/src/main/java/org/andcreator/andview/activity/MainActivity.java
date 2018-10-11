package org.andcreator.andview.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.flask.floatingactionmenu.FadingBackgroundView;
import com.flask.floatingactionmenu.FloatingActionMenu;
import com.flask.floatingactionmenu.FloatingActionToggleButton;
import com.flask.floatingactionmenu.OnFloatingActionMenuSelectedListener;

import org.andcreator.andview.R;
import org.andcreator.andview.adapter.SatelliteAdapter;
import org.andcreator.andview.fragment.MainContributorFragment;
import org.andcreator.andview.fragment.MainEffectFragment;
import org.andcreator.andview.fragment.MainLayoutFragment;
import org.andcreator.andview.fragment.MainViewFragment;
import org.andcreator.andview.uilt.BottomNavigationViewHelper;
import org.andcreator.andview.uilt.DialogUtil;
import org.andcreator.andview.uilt.OtherUtil;
import org.andcreator.andview.uilt.SetTheme;
import org.andcreator.andview.view.CircleWaveView;
import org.andcreator.andview.view.SatelliteView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Application.OnProvideAssistDataListener {

    private int mViewPagerIndex;
    private long mPressedTime = 0;

    private Bitmap bitmap;
    private ImageView background;
    private ViewPager mViewPager;
    private BottomNavigationView mNavigationView;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private BottomSheetBehavior bottomSheetBehavior;

    private FloatingActionToggleButton fab_toggle;
    private FloatingActionMenu fam;

    private FadingBackgroundView fading;

    private FloatingActionButton star;
    private View appsBar;
    private View panelView;
    private boolean isOpen = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_layout:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_view:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_effect:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_contributor:
                    mViewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setStartTheme(this);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        setContentView(R.layout.activity_main);

        background = findViewById(R.id.background);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("example_switch",true)){

            Glide.with(MainActivity.this).load(R.drawable.background_a).asBitmap()//强制Glide返回一个Bitmap对象
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {

                            bitmap = resource;

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        bitmap  = blurBitmap(MainActivity.this, bitmap, 20.0f, 1f);
                                        Message msg = new Message();
                                        msg.what = 1;
                                        handler.sendMessage(msg);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                        }

                    });

        }else {

            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }
        initView();

        Toast.makeText(this, OtherUtil.getSDLogPath()+"", Toast.LENGTH_LONG).show();
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    background.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }
        }
    };

    private void initView() {
        star = findViewById(R.id.star);
        fading = findViewById(R.id.fading);
        fab_toggle = findViewById(R.id.fab_toggle);
        fam = findViewById(R.id.fam);
        fam.setFadingBackgroundView(fading);
        fam.setOnFloatingActionMenuSelectedListener(new OnFloatingActionMenuSelectedListener() {
            @Override
            public void onFloatingActionMenuSelected(com.flask.floatingactionmenu.FloatingActionButton floatingActionButton) {
                if (floatingActionButton instanceof FloatingActionToggleButton) {
                    FloatingActionToggleButton fatb = (FloatingActionToggleButton) floatingActionButton;
//                    if (fatb.isToggleOn()) toast(floatingActionButton.getLabelText());
                } else {
                    if (floatingActionButton.getLabelText().equals("加入贡献者")){
                        fab_toggle.toggleOff();
                        Intent intent = new Intent("org.andcreator.andview.activity.ChatActivity");
                        startActivity(intent);
                    }else if (floatingActionButton.getLabelText().equals("访问开源地址")){
                        fab_toggle.toggleOff();
                    }
                }
            }
        });

        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager = findViewById(R.id.view_pager);

        mViewPager.setOffscreenPageLimit(4);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MainLayoutFragment(),"");
        adapter.addFragment(new MainViewFragment(),"");
        adapter.addFragment(new MainEffectFragment(),"");
        adapter.addFragment(new MainContributorFragment(),"");

        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(pageChangeListener);
        mViewPager.setPageTransformer(false, new DepthPageTransformer());
//        viewPager.setPageTransformer(true, new MyPageTransformer());

        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(mNavigationView);

        mSectionsPagerAdapter = (SectionsPagerAdapter) mViewPager.getAdapter();


        appsBar = findViewById(R.id.app_bar);
        panelView = findViewById(R.id.panel);
        View view = findViewById(R.id.sheet_view);
        appsBar.setAlpha(1);
        panelView.setAlpha(0);
        bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback(){

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                /**
                 STATE_COLLAPSED： 默认的折叠状态， bottom sheets只在底部显示一部分布局。显示高度可以通过 app:behavior_peekHeight 设置（默认是0）
                 STATE_DRAGGING ： 过渡状态，此时用户正在向上或者向下拖动bottom sheet
                 STATE_SETTLING: 视图从脱离手指自由滑动到最终停下的这一小段时间
                 STATE_EXPANDED： bottom sheet 处于完全展开的状态：当bottom sheet的高度低于CoordinatorLayout容器时，整个bottom sheet都可见；或者CoordinatorLayout容器已经被bottom sheet填满。
                 STATE_HIDDEN ： 默认无此状态（可通过app:behavior_hideable 启用此状态），启用后用户将能通过向下滑动完全隐藏 bottom sheet
                 */
                View decorView = getWindow().getDecorView();
//                if(uiFlag==0)
//                    uiFlag = decorView.getSystemUiVisibility();
                switch (newState){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        isOpen = false;
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        if (sharedPreferences.getBoolean("example_switch",true)){
                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                        }else {

                        }
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        isOpen = true;
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                panelView.setAlpha(slideOffset);
                appsBar.setAlpha(1-slideOffset*2);
                fab_toggle.setTranslationY(-slideOffset*1000);
            }
        });

        ImageView logo = findViewById(R.id.logo);
        Glide.with(this).load(R.drawable.logo).into(logo);

        PrefsFragment prefsFragment = new PrefsFragment();
        getFragmentManager().beginTransaction().replace(R.id.content,prefsFragment).commit();

        prefsFragment.setClickListener(new PrefsFragment.OnItemClickListener() {
            @Override
            public void onClick(int msg,Bitmap bitmap) {
                if (msg == 1){
                    if (background.getVisibility() == View.GONE){
                        background.setVisibility(View.VISIBLE);
                    }
                    background.setImageBitmap(bitmap);
                }else if (msg == 2){
                    background.setVisibility(View.GONE);
                }
            }
        });
    }

    //第一个Fragment必须在整个界面加载完成时在获取Fragment对象
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (mSectionsPagerAdapter != null){
            //ViewPager加载完成，得到同一对象的Fragment
            MainLayoutFragment mainLayoutFragment = (MainLayoutFragment) mSectionsPagerAdapter.getFragment(0);
            mainLayoutFragment.setOnFragmentInteractionListener(new MainLayoutFragment.OnFragmentInteractionListener() {
                @Override
                public void onFragmentInteraction(int scroll) {
                    if (scroll == 0){
                        hide(fab_toggle);
                        ObjectAnimator.ofFloat(mNavigationView,"translationY",0,mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom()).setDuration(200).start();
                    }else if (scroll == 1){
                        show(fab_toggle);
                        ObjectAnimator.ofFloat(mNavigationView,"translationY",mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom(),0).setDuration(200).start();
                    }
                }
            });
        }else {
            Toast.makeText(this, "怎么可能", Toast.LENGTH_SHORT).show();
        }

    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mNavigationView.setSelectedItemId(R.id.navigation_layout);
                    break;
                case 1:
                    mNavigationView.setSelectedItemId(R.id.navigation_view);

                    if (mSectionsPagerAdapter != null){
                        //ViewPager加载完成，得到同一对象的Fragment
                        MainViewFragment mainViewFragment = (MainViewFragment) mSectionsPagerAdapter.getFragment(1);
                        mainViewFragment.setOnFragmentInteractionListener(new MainViewFragment.OnFragmentInteractionListener() {
                            @Override
                            public void onFragmentInteraction(int scroll) {
                                if (scroll == 0){
                                    hide(fab_toggle);
                                    ObjectAnimator.ofFloat(mNavigationView,"translationY",0,mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom()).setDuration(200).start();
                                }else if (scroll == 1){
                                    show(fab_toggle);
                                    ObjectAnimator.ofFloat(mNavigationView,"translationY",mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom(),0).setDuration(200).start();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(MainActivity.this, "怎么可能", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case 2:
                    mNavigationView.setSelectedItemId(R.id.navigation_effect);

                    if (mSectionsPagerAdapter != null){
                        //ViewPager加载完成，得到同一对象的Fragment
                        MainEffectFragment mainEffectFragment = (MainEffectFragment) mSectionsPagerAdapter.getFragment(2);
                        mainEffectFragment.setOnFragmentInteractionListener(new MainEffectFragment.OnFragmentInteractionListener() {
                            @Override
                            public void onFragmentInteraction(int scroll) {
                                if (scroll == 0){
                                    hide(fab_toggle);
                                    ObjectAnimator.ofFloat(mNavigationView,"translationY",0,mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom()).setDuration(200).start();
                                }else if (scroll == 1){
                                    show(fab_toggle);
                                    ObjectAnimator.ofFloat(mNavigationView,"translationY",mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom(),0).setDuration(200).start();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(MainActivity.this, "怎么可能", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case 3:
                    mNavigationView.setSelectedItemId(R.id.navigation_contributor);

                    if (mSectionsPagerAdapter != null){
                        //ViewPager加载完成，得到同一对象的Fragment
                        MainContributorFragment mainContributorFragment = (MainContributorFragment) mSectionsPagerAdapter.getFragment(3);
                        mainContributorFragment.setOnFragmentInteractionListener(new MainContributorFragment.OnFragmentInteractionListener() {
                            @Override
                            public void onFragmentInteraction(int scroll) {
                                if (scroll == 0){
                                    hide(fab_toggle);
                                    ObjectAnimator.ofFloat(mNavigationView,"translationY",0,mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom()).setDuration(200).start();
                                }else if (scroll == 1){
                                    show(fab_toggle);
                                    ObjectAnimator.ofFloat(mNavigationView,"translationY",mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom(),0).setDuration(200).start();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(MainActivity.this, "怎么可能", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if(state==1){//state有三种状态下文会将，当手指刚触碰屏幕时state的值为1，我们就在这个时候给mViewPagerIndex 赋值。
                mViewPagerIndex = mViewPager.getCurrentItem();
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (isOpen){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }else {
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackEntryCount == 0){
                long mNowTime = System.currentTimeMillis();//获取第一次按键时间

                if((mNowTime - mPressedTime) > 1450){//比较两次按键时间差

                    Snackbar snackbar = Snackbar.make(mNavigationView, R.string.app_exit, Snackbar.LENGTH_SHORT);
                    View view = snackbar.getView();
                    view.setBackgroundColor(getResources().getColor(R.color.white));
                    snackbar.show();

                    mPressedTime = mNowTime;
                } else{
                    //退出程序
                    finish();
                }
            }else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onProvideAssistData(Activity activity, Bundle data) {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public class DepthPageTransformer implements ViewPager.PageTransformer {

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @SuppressLint("NewApi")
        public void transformPage(View view, float position) {
             if (position <= 1) { // (0,1]

            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, "??????", Toast.LENGTH_SHORT).show();
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                }
                break;
            default:
                break;
        }
    }


    /**
     * 显示的动画
     */
    private void show(final View view) {
        view.animate().cancel();

        // If the view isn't visible currently, we'll animate it from a single pixel
        view.setAlpha(0f);
        view.setScaleY(0f);
        view.setScaleX(0f);

        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(200)
                .setInterpolator(new LinearOutSlowInInterpolator())
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }
                });
    }

    /**
     * 隐藏的动画
     */
    private void hide(final View view) {
        view.animate().cancel();
        view.animate()
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(new FastOutLinearInInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    private boolean mCancelled;

                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                        mCancelled = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        mCancelled = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!mCancelled) {
                            view.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }


    /****************
     *
     * 发起添加群流程。群号：创艺者开发设计(677026563) 的 key 为： a-pWwOHzOhvaQQeYtr9oPbYxuIF7VTT9
     * 调用 joinQQGroup(a-pWwOHzOhvaQQeYtr9oPbYxuIF7VTT9) 即可发起手Q客户端申请加群 创艺者开发设计(677026563)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {


            ClipboardManager cmb = (ClipboardManager)MainActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText("677026563");

            // 未安装手Q或安装的版本不支持
            Toast.makeText(this, "未安装QQ或安装的版本不支持,群号已复制到剪贴板", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void startHttp(String uri){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public Fragment currentFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            this.currentFragment= (Fragment) object;
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment,String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public Fragment getFragment(int position) {
            return mFragmentList.get(position);
        }
        public Fragment getCurrentFragment() {
            return currentFragment;
        }
    }


    /**
     * 处理bitmap为高斯模糊图片
     * @param context 上下文
     * @param image   图片源
     * @param radius  模糊程度 0到25之间
     * @param scale   图片缩放比例, 该值越小越节省内存,模糊程度越敏感,0到1之间
     * @return 模糊的图片
     */
    public static Bitmap blurBitmap(Context context, Bitmap image, float radius, float scale) {

        // 计算图片缩小后的长宽
        int width = Math.round(image.getWidth() * scale);
        int height = Math.round(image.getHeight() * scale);
        // 将缩小后的图片做为预渲染的图片。
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        // 创建一张渲染后的输出图片。
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(radius);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);
        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    private static SwitchPreference mMistakeTouchPreference;
    private static Preference mChangeTheme;
    private static final String MISTAKE_TOUCH_MODE_KEY = "example_switch";
    private static final String CHANGE_THEME_KEY = "theme_type_number";
    private static Bitmap bitmaps;

    public static class PrefsFragment extends PreferenceFragment {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            mMistakeTouchPreference = (SwitchPreference) findPreference(MISTAKE_TOUCH_MODE_KEY);
            mChangeTheme = (Preference) findPreference(CHANGE_THEME_KEY);
            mChangeTheme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity(),ThemeActivity.class);
                    startActivityForResult(intent,1);
                    return true;
                }
            });
            mMistakeTouchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (mMistakeTouchPreference.isChecked() != (Boolean)newValue) {
                        boolean value = (Boolean)(newValue);
                        mMistakeTouchPreference.setChecked(value);

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        if (sharedPreferences.getBoolean("example_switch",true)){

                            Glide.with(getActivity()).load(R.drawable.background_a).asBitmap()//强制Glide返回一个Bitmap对象
                                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {

                                            bitmaps = resource;

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        bitmaps  = blurBitmap(getActivity(), bitmaps, 20.0f, 1f);
                                                        Message msg = new Message();
                                                        msg.what = 1;
                                                        handlers.sendMessage(msg);

                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();

                                        }

                                    });

                        }else {
                            Message msg = new Message();
                            msg.what = 2;
                            handlers.sendMessage(msg);
                            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
                            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                        }
                    }

                    return true;
                }
            });
        }

        @SuppressLint("HandlerLeak")
        private Handler handlers = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        if (clickListener != null){
                            //传参
                            clickListener.onClick(1,bitmaps);
                        }
                        break;
                    case 2:
                        if (clickListener != null){
                            //传参
                            clickListener.onClick(2,null);
                        }
                        break;
                    default:
                        break;
                }
            }
        };


        //回调接口
        public interface OnItemClickListener{
            void onClick(int msg,Bitmap bitmap);
        }

        private OnItemClickListener clickListener;

        public void setClickListener(OnItemClickListener clickListener){
            this.clickListener = clickListener;
        }

    }
}
