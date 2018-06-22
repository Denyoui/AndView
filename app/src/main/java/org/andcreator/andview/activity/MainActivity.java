package org.andcreator.andview.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.andcreator.andview.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_main);

        intoView();
    }

    private void intoView() {

        fab = findViewById(R.id.fab);
        //对话界面
        CardView chat = findViewById(R.id.chat_layout);
        //ViewPager
        CardView pager = findViewById(R.id.pager_layout);
        ImageView chatImg = findViewById(R.id.chat_img);
        ImageView pagerImg = findViewById(R.id.pager_img);

        Glide.with(this).load(R.drawable.a).into(chatImg);
        Glide.with(this).load(R.drawable.b).into(pagerImg);

        chat.setOnClickListener(this);
        pager.setOnClickListener(this);

        //悬浮按钮旋转动画
        fabAnimator(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinQQGroup("a-pWwOHzOhvaQQeYtr9oPbYxuIF7VTT9");
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // 未安装手Q或安装的版本不支持
                Snackbar.make(fab, "加入创艺者QQ群", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            }
        });
    }

    //悬浮按钮旋转动画
    private void fabAnimator(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0,360);
        animator.setDuration(2800);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.chat_layout:

                startActivity(new Intent(MainActivity.this,ChatActivity.class));

                break;
            case R.id.pager_layout:

                startActivity(new Intent(MainActivity.this,ViewPagerActivity.class));

                break;

                default:
                    break;
        }
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
            Snackbar.make(fab, "未安装QQ或安装的版本不支持,群号已复制到剪贴板", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
    }
}
