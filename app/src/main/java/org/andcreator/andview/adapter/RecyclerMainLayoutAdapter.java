package org.andcreator.andview.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.andcreator.andview.R;
import org.andcreator.andview.activity.AnimatorActivity;
import org.andcreator.andview.activity.BackActivity;
import org.andcreator.andview.activity.BlurActivity;
import org.andcreator.andview.activity.ChatActivity;
import org.andcreator.andview.activity.CircleWaveActivity;
import org.andcreator.andview.activity.ColorPagerActivity;
import org.andcreator.andview.activity.ColorRingActivity;
import org.andcreator.andview.activity.ContactActivity;
import org.andcreator.andview.activity.FoldActivity;
import org.andcreator.andview.activity.GooglePlusActivity;
import org.andcreator.andview.activity.GradientActivity;
import org.andcreator.andview.activity.IconPackageActivity;
import org.andcreator.andview.activity.LineChartActivity;
import org.andcreator.andview.activity.MaterialLoginActivity;
import org.andcreator.andview.activity.PaletteActivity;
import org.andcreator.andview.activity.PieActivity;
import org.andcreator.andview.activity.PixelLauncherActivity;
import org.andcreator.andview.activity.ProgressButtonActivity;
import org.andcreator.andview.activity.RadarActivity;
import org.andcreator.andview.activity.RandomShowTextActivity;
import org.andcreator.andview.activity.RecyclerActivity;
import org.andcreator.andview.activity.SatelliteActivity;
import org.andcreator.andview.activity.ScratchCardActivity;
import org.andcreator.andview.activity.ScreenshotActivity;
import org.andcreator.andview.activity.ScrollingActivity;
import org.andcreator.andview.activity.SlideSwitchActivity;
import org.andcreator.andview.activity.SpinningActivity;
import org.andcreator.andview.activity.TabIndicatorActivity;
import org.andcreator.andview.activity.ThermometerActivity;
import org.andcreator.andview.activity.ViewPagerActivity;
import org.andcreator.andview.activity.WheelActivity;
import org.andcreator.andview.bean.RecyclerContributorIconBean;
import org.andcreator.andview.bean.RecyclerMainLayoutBean;
import org.andcreator.andview.dialog.ClockDialog;
import org.andcreator.andview.dialog.LWheelDialog;
import org.andcreator.andview.service.VideoWallPaperService;
import org.andcreator.andview.uilt.DialogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecyclerMainLayoutAdapter extends RecyclerView.Adapter<RecyclerMainLayoutAdapter.MyHolder>{

    private List<RecyclerMainLayoutBean> data;
    private List<RecyclerContributorIconBean> contributorList;
    //保存的成员变量的LayoutInflater
    private LayoutInflater inflater;
    private Context mContext;
    private int mColor;
    private DialogUtil dialogUtil;
    private Calendar calendar;
    private Activity mActivity;

    //构造器传入Context来创建LayoutInflater，也可以直接传入一个LayoutInflater
    public RecyclerMainLayoutAdapter(Activity activity,LayoutInflater inflater, List<RecyclerMainLayoutBean> data, int color){
        this.data = data;
        this.inflater = inflater;
        mColor = color;
        mActivity = activity;
    }

    //这里代码变少了，直接使用MyHolder的静态构造方法
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dialogUtil = new DialogUtil();
        calendar = Calendar.getInstance();
        if (mContext == null) {
            mContext = parent.getContext();
        }
        return new MyHolder(inflater.inflate(R.layout.main_layout_item,parent,false));
    }

    //这里也变少了，使用Holder提供的绑定方法
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.onBind(data.get(position),mContext,inflater,contributorList);
    }

    //这里没变
    @Override
    public int getItemCount() {
        return data.size();
    }

    //变化最大的地方就是这里了
    class MyHolder extends RecyclerView.ViewHolder {

        //保存自己的View引用
        private CardView cardView;
        private TextView title;
        private TextView content;
        private RecyclerView contributor;

        //构造器
        private MyHolder(View itemView) {
            super(itemView);
            //既然构造器已经传入了View，为什么不顺便把需要用到的View保存一份呢？
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            contributor = itemView.findViewById(R.id.recycler_contributor);
            cardView = itemView.findViewById(R.id.card_a);
        }

        //申明一个数据绑定方法，只要求外部传入bean，绑定方式由自己决定
        void onBind(final RecyclerMainLayoutBean bean, Context context, final LayoutInflater layoutInflater, List<RecyclerContributorIconBean> contributorList){

            if (mColor != 0){
                cardView.setBackgroundTintList(ColorStateList.valueOf(mColor));
            }
            Animator animator = ObjectAnimator.ofFloat(cardView, "translationY", 140,0);
            animator.setDuration(400).start();

            title.setText(bean.getTitle());
            content.setText(bean.getDescription());
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            contributor.setLayoutManager(layoutManager);
            RecyclerContributorIconAdapter adapter = new RecyclerContributorIconAdapter(layoutInflater,loadData(contributorList,bean));
            contributor.setAdapter(adapter);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (bean.getIndex()){
                        case 1:
                            mContext.startActivity(new Intent(mContext,ChatActivity.class));
                            break;
                        case 2:
                            mContext.startActivity(new Intent(mContext,ViewPagerActivity.class));
                            break;
                        case 3:
                            mContext.startActivity(new Intent(mContext,RecyclerActivity.class));
                            break;
                        case 4:
                            mContext.startActivity(new Intent(mContext,ScrollingActivity.class));
                            break;
                        case 5:
                            mContext.startActivity(new Intent(mContext,ColorPagerActivity.class));
                            break;
                        case 6:
                            mContext.startActivity(new Intent(mContext,GradientActivity.class));
                            break;
                        case 7:
                            mContext.startActivity(new Intent(mContext,PixelLauncherActivity.class));
                            break;
                        case 8:
                            mContext.startActivity(new Intent(mContext,FoldActivity.class));
                            break;
                        case 9:
                            mContext.startActivity(new Intent(mContext,GooglePlusActivity.class));
                            break;
                        case 10:
                            mContext.startActivity(new Intent(mContext,MaterialLoginActivity.class));
                            break;
                        case 11:
                            mContext.startActivity(new Intent(mContext,CircleWaveActivity.class));
                            break;
                        case 12:
                            mContext.startActivity(new Intent(mContext,SatelliteActivity.class));
                            break;
                        case 13:
                            mContext.startActivity(new Intent(mContext,BlurActivity.class));
                            break;
                        case 14:
                            mContext.startActivity(new Intent(mContext,AnimatorActivity.class));
                            break;
                        case 15:
                            mContext.startActivity(new Intent(Settings.ACTION_VOICE_INPUT_SETTINGS));
                            break;
                        case 16:
                            mContext.startActivity(new Intent(mContext,ScreenshotActivity.class));
                            break;
                        case 17:
                            AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
                            builder.setTitle("#错误报告");
                            builder.setMessage("点击测试按钮使应用崩溃，崩溃日志会保存在sdcard根目录");
                            builder.setPositiveButton("测试", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    exception();
                                }
                            });
                            builder.show();
                            break;
                        case 18:
                            mContext.startActivity(new Intent(mContext,PaletteActivity.class));
                            break;
                        case 19:
                            Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,new ComponentName(mContext, VideoWallPaperService.class));
                            mContext.startActivity(intent);
                            break;
                        case 20:calendar.setTime(new Date());
                            dialogUtil.getClockDialog(mActivity, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), new ClockDialog.ClockDialogListener() {
                                @Override
                                public void onAffirmClock(int hour, int minute) {
                                    Toast.makeText(mContext, hour+":"+minute, Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 21:
                                dialogUtil.getLoadDialog(mActivity);
                            break;
                        case 22:
                            mContext.startActivity(new Intent(mContext,PieActivity.class));
                            break;
                        case 23:
                            dialogUtil.getProgressDialog(mActivity, 100, 100);
                            break;
                        case 24:
                            mContext.startActivity(new Intent(mContext,RadarActivity.class));
                            break;
                        case 25:
                            mContext.startActivity(new Intent(mContext,SlideSwitchActivity.class));
                            break;
                        case 26:
                            mContext.startActivity(new Intent(mContext,ThermometerActivity.class));
                            break;
                        case 27:
                            mContext.startActivity(new Intent(mContext,ProgressButtonActivity.class));
                            break;
                        case 28:
                            mContext.startActivity(new Intent(mContext,TabIndicatorActivity.class).putExtra("type", TabIndicatorActivity.thisType_bottom));
                            break;
                        case 29:
                            mContext.startActivity(new Intent(mContext,TabIndicatorActivity.class).putExtra("type", TabIndicatorActivity.thisType_top));
                            break;
                        case 30:
                            mContext.startActivity(new Intent(mContext,TabIndicatorActivity.class).putExtra("type", TabIndicatorActivity.thisType_top_line));
                            break;
                        case 31:
                            String[] singleChoiceItems = {"日期滚动","时间滚动","全套滚动"};
                            int itemSelected = 0;
                            new AlertDialog.Builder(mContext)
                                    .setTitle("选择")
                                    .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            switch (i){
                                                case 0:
                                                    dialogUtil.getWheelDialog(mActivity, LWheelDialog.LWheelDialogType.DATE, null);
                                                    break;
                                                case 1:
                                                    dialogUtil.getWheelDialog(mActivity, LWheelDialog.LWheelDialogType.TIME, null);
                                                    break;
                                                case 2:
                                                    dialogUtil.getWheelDialog(mActivity, LWheelDialog.LWheelDialogType.ALL, null);
                                                    break;
                                                    default:
                                                        break;
                                            }
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                            break;
                        case 32:
                            mContext.startActivity(new Intent(mContext,ContactActivity.class));
                            break;
                        case 33:
                            dialogUtil.getLoadDialog2(mActivity);
                            break;
                        case 34:
                            mContext.startActivity(new Intent(mContext,LineChartActivity.class));
                            break;
                        case 35:
                            mContext.startActivity(new Intent(mContext,ScratchCardActivity.class));
                            break;
                        case 36:
                            mContext.startActivity(new Intent(mContext,BackActivity.class));
                            break;
                        case 37:
                            mContext.startActivity(new Intent(mContext,ColorRingActivity.class));
                            break;
                        case 38:
                            mContext.startActivity(new Intent(mContext,WheelActivity.class));
                            break;
                        case 39:
                            mContext.startActivity(new Intent(mContext,RandomShowTextActivity.class));
                            break;
                        case 40:
                            mContext.startActivity(new Intent(mContext,IconPackageActivity.class));
                            break;
                        case 41:
                            mContext.startActivity(new Intent(mContext,ProgressButtonActivity.class));
                            break;
                        case 42:
                            mContext.startActivity(new Intent(mContext,SpinningActivity.class));
                            break;
                            default:
                                break;
                    }
                }
            });
        }

        private List<RecyclerContributorIconBean> loadData(List<RecyclerContributorIconBean> contributorList, RecyclerMainLayoutBean bean) {

            contributorList = new ArrayList<>();
            for (int i = 0;i < bean.getContributorList().length;i++){

                contributorList.add(new RecyclerContributorIconBean(bean.getContributorList()[i]));
            }

            return contributorList;
        }
    }

    public void exception(){
        int i = 0/0;
    }
}