package org.andcreator.andview.activity;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.andcreator.andview.R;
import org.andcreator.andview.option.LScratchCardOption;
import org.andcreator.andview.uilt.SetTheme;
import org.andcreator.andview.view.LScratchCardView;

public class ScratchCardActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,Switch.OnCheckedChangeListener,View.OnClickListener {

    private LScratchCardOption.Builder builder;
    private LScratchCardView scratchCard;
    private TextView mulchText,valueText;
    private Button mulchBtn,valueBtn,mulchTextSizeBarBtn,valueTextSizeBarBtn,touchWidthBarBtn,autoCleanSizeBarBtn,roundRadiusBarBtn;
    private SeekBar mulchTextSizeBar,valueTextSizeBar,touchWidthBar,autoCleanSizeBar,roundRadiusBar;
    private Switch transparentBg,autoClean;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        setContentView(R.layout.activity_scratch_card);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        res = getResources();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        scratchCard = findViewById(R.id.scratchcard);
        mulchText = findViewById(R.id.mulch_text);
        valueText = findViewById(R.id.value_text);
        mulchBtn = findViewById(R.id.mulch_btn);
        valueBtn = findViewById(R.id.value_btn);
        mulchTextSizeBar = findViewById(R.id.mulch_size);
        valueTextSizeBar = findViewById(R.id.value_size);
        touchWidthBar = findViewById(R.id.touch_width);
        autoCleanSizeBar = findViewById(R.id.auto_clean_size);
        roundRadiusBar = findViewById(R.id.round_radius);
        mulchTextSizeBarBtn = findViewById(R.id.mulch_size_btn);
        valueTextSizeBarBtn = findViewById(R.id.value_size_btn);
        touchWidthBarBtn = findViewById(R.id.touch_width_btn);
        autoCleanSizeBarBtn = findViewById(R.id.auto_clean_size_btn);
        roundRadiusBarBtn = findViewById(R.id.round_radius_btn);
        transparentBg = findViewById(R.id.transparent_bg);
        autoClean = findViewById(R.id.auto_clean);
        mulchBtn.setOnClickListener(this);
        valueBtn.setOnClickListener(this);
        mulchTextSizeBarBtn.setOnClickListener(this);
        valueTextSizeBarBtn.setOnClickListener(this);
        touchWidthBarBtn.setOnClickListener(this);
        autoCleanSizeBarBtn.setOnClickListener(this);
        roundRadiusBarBtn.setOnClickListener(this);
        mulchTextSizeBar.setOnSeekBarChangeListener(this);
        valueTextSizeBar.setOnSeekBarChangeListener(this);
        touchWidthBar.setOnSeekBarChangeListener(this);
        autoCleanSizeBar.setOnSeekBarChangeListener(this);
        roundRadiusBar.setOnSeekBarChangeListener(this);
        transparentBg.setOnCheckedChangeListener(this);
        autoClean.setOnCheckedChangeListener(this);

        builder = new LScratchCardOption.Builder();
        builder.setBackgroundColor(Color.parseColor("#00E3E3"),Color.parseColor("#6FB7B7"),Color.parseColor("#84C1FF"))
                .setBackgroundColorAngle(30)
                .setMulchColorAngle(90)
                .setBackgroundImg(BitmapFactory.decodeResource(res,R.drawable.lollipop))
                .setBackgroundImgScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRoundRadius(20)
                .setValueImg(BitmapFactory.decodeResource(res,R.drawable.ic_launch_152px))
                .setValueImgScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setText(valueText.getText().toString())
                .setTextColor(Color.BLUE,Color.RED,Color.CYAN)
                .setMulchColor(Color.BLACK,Color.WHITE,Color.GRAY,Color.WHITE,Color.BLACK)
                .setMulchImg(BitmapFactory.decodeResource(res,R.mipmap.ic_launcher))
                .setMulchImgScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setMulchText(mulchText.getText().toString())
                .setMulchTextColor(Color.parseColor("#FF5809"),Color.parseColor("#B15BFF"))
                .setMulchTextSize(mulchTextSizeBar.getProgress()*3)
                .setRoundRadius(roundRadiusBar.getProgress()*3)
                .setTouchWdth(touchWidthBar.getProgress()*3);
        scratchCard.setOption(new LScratchCardOption(builder));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
//        switch (seekBar.getId()){
//            case R.id.mulch_size:
//                builder.setMulchTextSize(seekBar.getProgress());
//                break;
//            case R.id.value_size:
//                builder.setTextSize(seekBar.getProgress());
//                break;
//            case R.id.touch_width:
//                builder.setTouchWdth(seekBar.getProgress());
//                break;
//            case R.id.auto_clean_size:
//                builder.setAutoClean(seekBar.getProgress()*1.0f/100);
//                break;
//            case R.id.round_radius:
//                builder.setRoundRadius(seekBar.getProgress());
//                break;
//        }
//        scratchCard.setOption(new LScratchCardOption(builder));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.transparent_bg:
                builder.setTransparentBg(isChecked);
                break;
            case R.id.auto_clean:
                builder.setAutoCleanEnable(isChecked);
                break;
        }
        scratchCard.setOption(new LScratchCardOption(builder));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mulch_btn:
                builder.setMulchText(mulchText.getText().toString());
                break;
            case R.id.value_btn:
                builder.setText(valueText.getText().toString());
                break;
            case R.id.mulch_size_btn:
                builder.setMulchTextSize(mulchTextSizeBar.getProgress()*3);
                break;
            case R.id.value_size_btn:
                builder.setTextSize(valueTextSizeBar.getProgress()*3);
                break;
            case R.id.touch_width_btn:
                builder.setTouchWdth(touchWidthBar.getProgress()*3);
                break;
            case R.id.auto_clean_size_btn:
                builder.setAutoClean(autoCleanSizeBar.getProgress()*1.0f/100);
                break;
            case R.id.round_radius_btn:
                builder.setRoundRadius(roundRadiusBar.getProgress());
                break;
        }
        scratchCard.setOption(new LScratchCardOption(builder));
    }
}
