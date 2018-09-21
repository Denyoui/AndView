package org.andcreator.andview.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.andcreator.andview.R;
import org.andcreator.andview.adapter.RecyclerChatAdapter;
import org.andcreator.andview.bean.RecyclerChatBean;
import org.andcreator.andview.fragment.ChatCameraFragment;
import org.andcreator.andview.fragment.ChatEmojiListFragment;
import org.andcreator.andview.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnLayoutChangeListener,GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    private Boolean Show;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private RelativeLayout contentLayout;
    private RecyclerView recyclerChat;
    private CardView cardView;
    //Activity最外层的Layout视图
    private CoordinatorLayout coordinatorLayout;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private List<RecyclerChatBean> mListChat;
    private EditText input;
    private RecyclerChatAdapter adapter;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton send;

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;

    private int mTextSize = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDetector = new GestureDetectorCompat(this,this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);

        appBarLayout = (AppBarLayout) findViewById(R.id.action_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cardView = (CardView) findViewById(R.id.shit_b);

        contentLayout = (RelativeLayout) findViewById(R.id.content_layout);
        recyclerChat = (RecyclerView) findViewById(R.id.recycler_chat);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.root_layout);

        viewPager = (ViewPager) findViewById(R.id.text_pager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.text_tab);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.chat_ic_insert_emoticon_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.chat_ic_image_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.chat_ic_photo_camera_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.chat_ic_videocam_black_24dp);
        tabLayout.getTabAt(4).setIcon(R.drawable.chat_ic_loyalty_black_24dp);
        tabLayout.getTabAt(5).setIcon(R.drawable.chat_ic_location_on_black_24dp);
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        TypedValue typedValue = new  TypedValue();
                        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
                        final  int color = typedValue.data;
                        tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);

                        if (viewPager.getVisibility() == View.GONE){

                            viewPager.setVisibility(View.VISIBLE);
                            tabLayout.setSelectedTabIndicatorHeight(dip2px(ChatActivity.this,4));

                            ObjectAnimator.ofFloat(viewPager,"translationY",viewPager.getHeight()+viewPager.getPaddingBottom(),0).setDuration(300).start();

                        }

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(ChatActivity.this, R.color.tabUnselectedIconColor);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

                        if (viewPager.getVisibility() == View.GONE){

                            viewPager.setVisibility(View.VISIBLE);
                            tabLayout.setSelectedTabIndicatorHeight(dip2px(ChatActivity.this,4));
                            ObjectAnimator.ofFloat(viewPager,"translationY",viewPager.getHeight()+viewPager.getPaddingBottom(),0).setDuration(300).start();
                        }
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);

                        TypedValue typedValue = new  TypedValue();
                        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
                        final  int color = typedValue.data;
                        tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);

                        if (viewPager.getVisibility() == View.VISIBLE){

                            viewPager.setVisibility(View.GONE);
                            tabLayout.setSelectedTabIndicatorHeight(dip2px(ChatActivity.this,0));

                        }else {

                            viewPager.setVisibility(View.VISIBLE);
                            tabLayout.setSelectedTabIndicatorHeight(dip2px(ChatActivity.this,4));
                            ObjectAnimator.ofFloat(viewPager,"translationY",viewPager.getHeight()+viewPager.getPaddingBottom(),0).setDuration(300).start();

                        }
                    }
                }
        );


        send = (FloatingActionButton) findViewById(R.id.send_message);

        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;

        Show = false;

        send.setBackgroundColor(getResources().getColor(R.color.black));
        input = (EditText) findViewById(R.id.input_comment_content);

        if (TextUtils.isEmpty(input.getText())){
            send.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#eceff1")));
            send.setColorFilter(Color.parseColor("#98aab4"));
            send.invalidate();
        }else {
            TypedValue typedValue = new  TypedValue();
            getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            send.setBackgroundTintList(ColorStateList.valueOf(typedValue.data));
            send.setColorFilter(Color.parseColor("#98aab4"));
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(input.getText())){
//                viewPager.setVisibility(View.GONE);
                    String inputText = input.getText().toString();

                    RecyclerChatBean a = new RecyclerChatBean(2,R.drawable.icon,inputText,px2sp(ChatActivity.this,input.getTextSize()));
                    mListChat.add(a);

                    adapter.notifyDataSetChanged();
                    recyclerChat.scrollToPosition(adapter.getItemCount()-1);
                    input.setText(null);
                }
            }
        });
        send.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // ... Respond to touch events
                mDetector.onTouchEvent(event);
                return ChatActivity.super.onTouchEvent(event);
            }
        });
        input.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                    // 获得焦点
                    if (viewPager.getVisibility() == View.VISIBLE){
                        viewPager.setVisibility(View.GONE);
                        tabLayout.setSelectedTabIndicatorHeight(dip2px(ChatActivity.this,0));
                    }
                } else {

                    // 失去焦点

                }

            }


        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true); //显示在最底部
        recyclerChat.setLayoutManager(layoutManager);
        adapter = new RecyclerChatAdapter(listChat());
        recyclerChat.setAdapter(adapter);

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃   神兽保佑
//    ┃　　　┃   代码无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛


    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getVisibility() == View.VISIBLE){
            viewPager.setVisibility(View.GONE);
            tabLayout.setSelectedTabIndicatorHeight(dip2px(ChatActivity.this,0));
        }else {
            super.onBackPressed();
        }
    }


    private List<RecyclerChatBean> listChat() {
        mListChat = new ArrayList<>();

        RecyclerChatBean a = new RecyclerChatBean(1,R.drawable.icon,"主界面向上拖动有惊喜哦",px2sp(ChatActivity.this,input.getTextSize()));
        mListChat.add(a);

        RecyclerChatBean b = new RecyclerChatBean(2,R.drawable.icon,"这设计我也是醉了",px2sp(ChatActivity.this,input.getTextSize()));
        mListChat.add(b);

        RecyclerChatBean c = new RecyclerChatBean(2,R.drawable.icon,"Logo挺漂亮的吖",px2sp(ChatActivity.this,input.getTextSize()));
        mListChat.add(c);
        RecyclerChatBean d = new RecyclerChatBean(1,R.drawable.icon,"过奖过奖",px2sp(ChatActivity.this,input.getTextSize()));
        mListChat.add(d);

        RecyclerChatBean e = new RecyclerChatBean(1,R.drawable.icon,"您可以试试下面的相机按钮，可以拍照的哦",px2sp(ChatActivity.this,input.getTextSize()));
        mListChat.add(e);

        RecyclerChatBean f = new RecyclerChatBean(2,R.drawable.icon,"我试试，",px2sp(ChatActivity.this,input.getTextSize()));
        mListChat.add(f);

        RecyclerChatBean g = new RecyclerChatBean(1,R.drawable.icon,"求捐赠ヾ(✿ﾟ▽ﾟ)ノ",px2sp(ChatActivity.this,input.getTextSize()));
        mListChat.add(g);

        RecyclerChatBean h = new RecyclerChatBean(2,R.drawable.icon,"已转入0.99$",px2sp(ChatActivity.this,input.getTextSize()));
        mListChat.add(h);

        RecyclerChatBean i = new RecyclerChatBean(1,R.drawable.icon,"Wow！太感谢了٩(๑❛ᴗ❛๑)۶٩(๑❛ᴗ❛๑)۶",px2sp(ChatActivity.this,input.getTextSize()));
        mListChat.add(i);

        return mListChat;
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //添加layout大小发生改变监听器
        coordinatorLayout.addOnLayoutChangeListener(this);
    }


    private void ShowOrHideTitle(){//标题栏
        int fromY;//0表示控件Y轴起点
        int toY;//正值表示下移，负值上移
        if (Show) {//显示
            fromY = -viewPager.getHeight();
            toY = 0;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

//                    viewPager.setVisibility(View.VISIBLE);
                }
            },120);
        } else {//隐藏
            fromY = 0;
            toY = viewPager.getHeight();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    viewPager.setVisibility(View.GONE);
                }
            },120);
        }
        final TranslateAnimation animation;//平移动画
        animation = new TranslateAnimation(0, 0, fromY, toY);
        animation.setDuration(120);//设置动画持续毫秒
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        viewPager.startAnimation(animation);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值

//      System.out.println(oldLeft + " " + oldTop +" " + oldRight + " " + oldBottom);
//      System.out.println(left + " " + top +" " + right + " " + bottom);


        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){

            if (viewPager.getVisibility() == View.VISIBLE){
//                ObjectAnimator.ofFloat(cardView,"translationY",0,viewPager.getHeight()+viewPager.getPaddingBottom()).setDuration(200).start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        viewPager.setVisibility(View.GONE);
                        tabLayout.setSelectedTabIndicatorHeight(dip2px(ChatActivity.this,4));

                    }
                },100);
            }
//                Show = true;

        }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > keyHeight)){
//            viewPager.setVisibility(View.VISIBLE);
//            ShowOrHideTitle();
//            Show = false;
            if (viewPager.getVisibility() == View.GONE){
//                ObjectAnimator.ofFloat(cardView,"translationY",viewPager.getHeight()+viewPager.getHeight()+viewPager.getPaddingBottom(),0).setDuration(200).start();
//                viewPager.setVisibility(View.VISIBLE);

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                },100);
            }
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChatEmojiListFragment(),null);
        adapter.addFragment(new TestFragment(),null);
        adapter.addFragment(new ChatCameraFragment(),null);
        adapter.addFragment(new TestFragment(),null);
        adapter.addFragment(new TestFragment(),null);
        adapter.addFragment(new TestFragment(),null);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);

        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        if (!TextUtils.isEmpty(input.getText())){
//                viewPager.setVisibility(View.GONE);
            String inputText = input.getText().toString();

            RecyclerChatBean a = new RecyclerChatBean(2,R.drawable.icon,inputText,px2sp(ChatActivity.this,input.getTextSize()));
            mListChat.add(a);

            adapter.notifyDataSetChanged();
            recyclerChat.scrollToPosition(adapter.getItemCount()-1);
            input.setText(null);
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (distanceY > 0) {
            mTextSize = mTextSize+1;
            input.setTextSize(mTextSize);
        }else {
            mTextSize = mTextSize-1;
            input.setTextSize(mTextSize);
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!TextUtils.isEmpty(input.getText())){
//                viewPager.setVisibility(View.GONE);
            String inputText = input.getText().toString();

            RecyclerChatBean a = new RecyclerChatBean(2,R.drawable.icon,inputText,px2sp(ChatActivity.this,input.getTextSize()));
            mListChat.add(a);

            adapter.notifyDataSetChanged();
            recyclerChat.scrollToPosition(adapter.getItemCount()-1);
            input.setText(null);
        }
        return false;
    }

    //ViewPager Adapter
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
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
    }
}
