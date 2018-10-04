package org.andcreator.andview.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import org.andcreator.andview.R;

public class MaterialRegisterActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private FloatingActionButton up;
    private CardView cvAdd;
    private EditText userName,password,repeatPassword,phone,verification;
    private Button next,go;

    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        setContentView(R.layout.activity_material_register);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ShowEnterAnimation();
        }


        fab = (FloatingActionButton) findViewById(R.id.fab);
        up = (FloatingActionButton) findViewById(R.id.up);
        cvAdd = (CardView) findViewById(R.id.cv_add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    animateRevealClose();
                }
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
            }
        });

        scrollView = (ScrollView) findViewById(R.id.scroll);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                //不能滑动
                return true;
            }
        });

        next = (Button)findViewById(R.id.bt_next);
        go = (Button) findViewById(R.id.et_go);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userName.getText().toString().length() == 0 && password.getText().toString().length() == 0 && password.getText().toString().length() == 0){
                    Snackbar.make(view, "名称、密码都不能为空", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else if (!(password.getText().toString().equals(repeatPassword.getText().toString()))){
                    Snackbar.make(view, "请核对密码", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }

            }
        });

        userName = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        repeatPassword = findViewById(R.id.et_repeat_password);
        phone = findViewById(R.id.et_phone);
        verification = findViewById(R.id.et_verification);


        //注册操作
        go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.ic_add_white_24dp);
                MaterialRegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
}
