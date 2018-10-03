package org.andcreator.andview.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.andcreator.andview.R;
import org.andcreator.andview.view.Menu2CloseDrawable;

public class FoldActivity extends AppCompatActivity {

    private LinearLayout view1;
    private RecyclerView view2;
    private boolean off = false;
    private float menuPosition = 0F;
    private long menuAnimationDuration = 200;
    int checkStatus = 1;
    int animatorHeight = 0;
    private Toolbar toolbar;

    private RadioGroup radioGroup;

    private Menu2CloseDrawable menu2CloseDrawable = new Menu2CloseDrawable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        setContentView(R.layout.activity_fold);
        //设置绘制尺寸为标准的24DP
        menu2CloseDrawable.setSizeDP(this,18)
                //设置颜色
                .setColor(getResources().getColor(R.color.white))
                //设置线条粗细
                .setBarThicknessDP(this, 2)
                //设置间隔高度
                .setBarGapDP(this,3)
                //设置menu线条左右缩进，三个值分别是三条横线的左右缩进
                .setMenuHorizontalPaddingDP(this,0,0,0);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("布局&UI");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(menu2CloseDrawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMenu();
            }
        });

        view1 = findViewById(R.id.view_a);
        view2 = findViewById(R.id.view_b);
        view2.post(new Runnable() {
            @Override
            public void run() {
                animatorHeight = view1.getHeight();
            }
        });
        radioGroup = findViewById(R.id.radio_group);

        RadioButton layouts = findViewById(R.id.layouts);
        RadioButton views = findViewById(R.id.views);
        RadioButton effects = findViewById(R.id.effects);
        RadioButton contributors = findViewById(R.id.contributors);


        if (!off) {

            layouts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (off) {
                        changeMenu();
                    }
                    checkStatus = 1;
                    toolbar.setTitle("布局&UI");
                }
            });

            views.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (off) {
                        changeMenu();
                    }
                    checkStatus = 2;
                    toolbar.setTitle("控件");
                }
            });

            effects.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (off) {
                        changeMenu();
                    }
                    checkStatus = 3;
                    toolbar.setTitle("效果");
                }
            });

            contributors.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (off) {
                        changeMenu();
                    }
                    checkStatus = 4;
                    toolbar.setTitle("贡献者");
                }
            });
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void changeMenu(){

        ValueAnimator animator = new ValueAnimator();

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                menuPosition = (float) valueAnimator.getAnimatedValue();
                menu2CloseDrawable.onPositionChange(menuPosition);
            }
        });

        //停止现有动画
        animator.cancel();

        if(off){
            //如果已经打开，那么就执行关闭动画
            animator.setFloatValues(menuPosition,0);
            //将总的动画时间和当前动画进度关联，保证就算动画打断也不会影响速度
            animator.setDuration((long) (menuAnimationDuration * menuPosition));

            ObjectAnimator animator5 = ObjectAnimator.ofFloat(view2, "translationY",animatorHeight,0);
            animator5.setInterpolator(new DecelerateInterpolator());
            animator5.setDuration(menuAnimationDuration);
            animator5.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view1.setVisibility(View.GONE);
                }
            });
            animator5.start();

        }else{
            view1.setVisibility(View.VISIBLE);
            //打开动画，同上
            animator.setFloatValues(menuPosition,1);
            animator.setDuration((long) (menuAnimationDuration * (1-menuPosition)));

            ObjectAnimator animator5 = ObjectAnimator.ofFloat(view2, "translationY", 0,animatorHeight);
            animator5.setInterpolator(new DecelerateInterpolator());
            animator5.setDuration(menuAnimationDuration);
            animator5.start();
        }
        //修改动画状态
        off = !off;
        //启动动画
        animator.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:
                startActivity(new Intent(this,MainActivity.class));
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

        return super.onOptionsItemSelected(item);
    }

}
