package org.andcreator.andview.activity;

import android.animation.ObjectAnimator;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import org.andcreator.andview.R;
import org.andcreator.andview.adapter.SatelliteAdapter;
import org.andcreator.andview.view.SatelliteView;

public class SatelliteActivity extends AppCompatActivity {

    private SatelliteView satelliteView1;
    private SatelliteView satelliteView2;
    private SatelliteView satelliteView3;
    private SatelliteView satelliteView4;
    private SatelliteView satelliteView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        setContentView(R.layout.activity_satellite);

        satelliteView1 = findViewById(R.id.satellite1);
        satelliteView2 = findViewById(R.id.satellite2);
        satelliteView3 = findViewById(R.id.satellite3);
        satelliteView4 = findViewById(R.id.satellite4);
        satelliteView5 = findViewById(R.id.satellite5);
        satelliteView1.setAdapter(getAdapter());
        satelliteView1.setRadius(200);
        satelliteView2.setAdapter(getAdapter());
        satelliteView2.setRadius(200);
        satelliteView3.setAdapter(getAdapter());
        satelliteView3.setRadius(200);
        satelliteView4.setAdapter(getAdapter());
        satelliteView4.setRadius(200);
        satelliteView5.setAdapter(getAdapter());
        satelliteView5.setRadius(200);
    }


    @NonNull
    private SatelliteAdapter getAdapter() {
        return new SatelliteAdapter() {
            @Override
            public View createMenuItem(ViewGroup parent, int index) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_menu_item, parent, false);

                FloatingActionButton fab = v.findViewById(R.id.fabs);
                if (index == 1){
                    fab.setImageResource(R.drawable.ic_group_work_white_24dp);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            satelliteView1.close();
                            joinQQGroup("a-pWwOHzOhvaQQeYtr9oPbYxuIF7VTT9");
                        }
                    });
                }else if (index == 0){
                    fab.setImageResource(R.drawable.ic_code_black_24dp);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            satelliteView1.close();
                            startHttp("https://github.com/hujincan/AndView");
                        }
                    });
                }else if (index == 2){
                    fab.setImageResource(R.drawable.ic_bug_report_black_24dp);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            satelliteView1.close();
                            startHttp("https://nightfarmer.top");
                        }
                    });
                }else if (index == 3){
                    fab.setImageResource(R.drawable.l);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            satelliteView1.close();
                            startHttp("https://www.lollipoppp.com/");
                        }
                    });
                }

                return v;
            }

            @Override
            public View createToggleItem(ViewGroup parent) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_menu_toggle, parent, false);
                FloatingActionButton fab = v.findViewById(R.id.toggle);
                fab.setImageResource(R.drawable.ic_toys_black_24dp);
                fabAnimator(fab);
                return v;
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    //悬浮按钮旋转动画
    private void fabAnimator(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0,360);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.start();
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


            ClipboardManager cmb = (ClipboardManager)SatelliteActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
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

}
