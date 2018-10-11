package org.andcreator.andview.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.andcreator.andview.R;
import org.andcreator.andview.uilt.BaseHolder;
import org.andcreator.andview.uilt.LItemTouchCallback;
import org.andcreator.andview.uilt.LItemTouchHelper;
import org.andcreator.andview.view.ArrowLineDrawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PixelLauncherActivity extends Activity implements LItemTouchCallback.OnItemTouchCallbackListener {

    private RecyclerView recyclerView;
    private View appsBar;
    private TextInputEditText editText;
    private View panelView;
    private int uiFlag = 0;
    private ArrowLineDrawable arrowLineDrawable;
    private ArrayList<AppInfo> appInfoList = null;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pixel_launcher);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        initView();
        initRecyclerView();
        queryAppInfo();
        adapter.notifyDataSetChanged();
    }

    private void initView(){
        View view = findViewById(R.id.sheet_view);
        appsBar = findViewById(R.id.app_bar);
        editText = (TextInputEditText) findViewById(R.id.edittext);
        panelView = findViewById(R.id.panel);
        ImageView arrowView = (ImageView) findViewById(R.id.arrow);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        appsBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PixelLauncherActivity.this, "dianji", Toast.LENGTH_SHORT).show();
            }
        });
        arrowView.setImageDrawable(arrowLineDrawable = new ArrowLineDrawable());
//        arrowView.setVisibility(View.GONE);
        arrowLineDrawable.setColor(Color.WHITE);
        arrowLineDrawable.upTo();
        appsBar.setAlpha(1);
        panelView.setAlpha(0);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setPeekHeight(bottomSheetBehavior.getPeekHeight()+getNavigationBarHeight());
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
                uiFlag = 0;
                switch (newState){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        decorView.setSystemUiVisibility(uiFlag);
                      break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        decorView.setSystemUiVisibility(uiFlag);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        // This example uses decor view, but you can use any visible view.
//                        int uiOptions = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//                        decorView.setSystemUiVisibility(uiOptions);
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        decorView.setSystemUiVisibility(uiFlag);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                float alpha = bottomSheet.getHeight()*slideOffset/height;
//                alpha = alpha>1?1:alpha;
//                alpha = alpha<0?0:alpha;
                panelView.setAlpha(slideOffset);
                appsBar.setAlpha(1-slideOffset*2);
//                Log.e("onSlide",slideOffset+"");
                arrowLineDrawable.setProgress(slideOffset-1);
            }
        });
    }

    private int getNavigationBarHeight() {
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        return height;
    }

    //初始化列表的方法
    private void initRecyclerView() {
        appInfoList = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,5);//初始化列表layout管理器
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//设定为纵向
        recyclerView.setLayoutManager(gridLayoutManager);//将管理器设置进列表
        recyclerView.setItemAnimator(new DefaultItemAnimator());//设置列表item动画
        LItemTouchHelper helper = LItemTouchHelper.newInstance(recyclerView, this);//设置控制帮助类
//        helper.setCanSwipe(true);//设置可以滑动删除
        adapter = new ItemAdapter(appInfoList, LayoutInflater.from(this), helper);//初始化列表适配器
        recyclerView.setAdapter(adapter);//为列表设置适配器
    }

    private void queryAppInfo() {
        if(appInfoList==null){
            appInfoList = new ArrayList<>();
        }
        appInfoList.clear();
        // 获得所有启动Activity的信息，类似于Launch界面
        PackageManager pm = this.getPackageManager(); // 获得PackageManager对象
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // 通过查询，获得所有ResolveInfo对象.
        List<ResolveInfo> resolveInfos = pm
                .queryIntentActivities(mainIntent, 0);
        // 调用系统排序 ， 根据name排序
        // 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
        Collections.sort(resolveInfos,new ResolveInfo.DisplayNameComparator(pm));
        for (ResolveInfo reInfo : resolveInfos) {
            String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
            String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
            String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
            Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标
            // 为应用程序的启动Activity 准备Intent
            Intent launchIntent = new Intent();
            launchIntent.setComponent(new ComponentName(pkgName,
                    activityName));
            // 创建一个AppInfo对象，并赋值
            appInfoList.add(new AppInfo(icon,launchIntent,appLabel,pkgName)); // 添加至列表中
        }
    }


    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder>{

        private ArrayList<AppInfo> beanArrayList;
        private LayoutInflater inflater;
        private LItemTouchHelper helper;

        public ItemAdapter(ArrayList<AppInfo> beanArrayList, LayoutInflater inflater, LItemTouchHelper helper) {
            this.beanArrayList = beanArrayList;
            this.inflater = inflater;
            this.helper = helper;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemHolder holder = new ItemHolder(inflater.inflate(ItemHolder.LAYOUT_ID,parent,false));
            holder.setHelper(helper);
            return holder;
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            if(holder!=null)
                holder.onBind(beanArrayList.get(position));
        }

        @Override
        public int getItemCount() {
            if(beanArrayList!=null)
                return beanArrayList.size();
            return 0;
        }
    }

    private class ItemHolder extends BaseHolder {

        private static final int LAYOUT_ID = R.layout.item_app;
        private ImageView logo;
        private TextView name;

        public ItemHolder(View itemView) {
            super(itemView);
            logo = (ImageView) itemView.findViewById(R.id.item_app_icon);
            name = (TextView) itemView.findViewById(R.id.item_app_name);
            itemView.setOnClickListener(this);
        }

        public void onBind(AppInfo bean) {
            logo.setImageDrawable(bean.appIcon);
            name.setText(bean.name);
        }
    }

    @Override
    public void onSwiped(int adapterPosition) {

    }

    @Override
    public boolean onMove(int srcPosition, int targetPosition) {
        return false;
    }

    @Override
    public void onItemViewClick(RecyclerView.ViewHolder holder, View v) {
        int index = holder.getAdapterPosition();
        startActivity(appInfoList.get(index).intent);
    }

    public class AppInfo {
        public String name;
        public Drawable appIcon;
        public Intent intent;
        public String pkgName;

        public AppInfo() {
        }

        public AppInfo(Drawable appIcon, Intent intent, String name, String pkgName) {
            this.appIcon = appIcon;
            this.intent = intent;
            this.name = name;
            this.pkgName = pkgName;
        }
    }
}
