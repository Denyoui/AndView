package org.andcreator.andview.activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.andcreator.andview.R;
import org.andcreator.andview.uilt.SetTheme;

/**
 * @author hawvu
 */
public class AnimatorActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        setContentView(R.layout.activity_animator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button1,button2,button5,button6,button7,button8;
        TextView button3,button4,button9;
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "alpha", 1,0,1);
                animator1.setDuration(2000);
                animator1.start();
                break;
            case R.id.button2:
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "rotation", 0,180,360);
                animator2.setDuration(2000);
                animator2.start();
                break;
            case R.id.button3:
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "rotationX", 0,180,360);
                animator3.setDuration(2000);
                animator3.start();
                break;
            case R.id.button4:
                ObjectAnimator animator4 = ObjectAnimator.ofFloat(view, "rotationY", 0,180,360);
                animator4.setDuration(2000);
                animator4.start();
                break;
            case R.id.button5:
                ObjectAnimator animator5 = ObjectAnimator.ofFloat(view, "translationX", 0,200,-200,0);
                animator5.setDuration(2000);
                animator5.start();
                break;
            case R.id.button6:
                ObjectAnimator animator6 = ObjectAnimator.ofFloat(view, "translationY", 0,200,-200,0);
                animator6.setDuration(2000);
                animator6.start();
                break;
            case R.id.button7:
                ObjectAnimator animator7 = ObjectAnimator.ofFloat(view, "scaleX", 1,3,2,1);
                animator7.setDuration(2000);
                animator7.start();
                break;
            case R.id.button8:
                ObjectAnimator animator8 = ObjectAnimator.ofFloat(view, "scaleY", 1,3,2,1);
                animator8.setDuration(2000);
                animator8.start();
                break;
            case R.id.button9:
                ObjectAnimator animator = ObjectAnimator.ofInt(view, "backgroundColor", 0xffec407a, 0xffffff00, 0xffec407a);
                animator.setDuration(2000);
                animator.setEvaluator(new ArgbEvaluator());
                animator.start();
                break;
                default:
                break;
        }
    }
}
