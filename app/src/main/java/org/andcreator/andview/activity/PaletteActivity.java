package org.andcreator.andview.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import org.andcreator.andview.R;
import org.andcreator.andview.uilt.ImageUtil;
import org.andcreator.andview.uilt.SetTheme;
import org.andcreator.andview.uilt.SnackbarUtil;

import static org.andcreator.andview.uilt.ImageUtil.CHOOSE_PHOTO;
import static org.andcreator.andview.uilt.ImageUtil.drawableToBitmap;
import static org.andcreator.andview.uilt.ImageUtil.getRealFilePath;
import static org.andcreator.andview.uilt.ImageUtil.handleImageOnKitKat;

public class PaletteActivity extends AppCompatActivity implements View.OnClickListener{

    private Button vibrant,vibrantDark,vibrantLight,muted,mutedDark,mutedLight;
    private Bitmap bitmap;
    private ImageView image;
    private View showColor;
    private TextView rgb;
    private CardView imageCard;
    private FloatingActionButton chooseImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        setContentView(R.layout.activity_palette);

        image = findViewById(R.id.image);
        showColor = findViewById(R.id.show_color);
        rgb = findViewById(R.id.rgb);
        imageCard = findViewById(R.id.image_card);
        chooseImage = findViewById(R.id.choose_image);

        vibrant = findViewById(R.id.vibrant);
        vibrantDark = findViewById(R.id.vibrant_dark);
        vibrantLight = findViewById(R.id.vibrant_light);
        muted = findViewById(R.id.muted);
        mutedDark = findViewById(R.id.muted_dark);
        mutedLight = findViewById(R.id.muted_light);

        chooseImage.setOnClickListener(this);
        imageCard.setOnClickListener(this);
        vibrant.setOnClickListener(this);
        vibrantDark.setOnClickListener(this);
        vibrantLight.setOnClickListener(this);
        muted.setOnClickListener(this);
        mutedDark.setOnClickListener(this);
        mutedLight.setOnClickListener(this);

        Glide.with(this).load(R.drawable.blur).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                bitmap = drawableToBitmap(resource);
                image.setImageBitmap(bitmap);
                getPaletteColor();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_card:
                ClipboardManager cmb = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(rgb.getText());
                new SnackbarUtil(this,chooseImage,"已复制颜色到剪贴板");
                break;
            case R.id.choose_image:
                new ImageUtil(PaletteActivity.this,this).openAlbum();
                break;
            case R.id.vibrant:
                showColor.setBackgroundColor(vibrant.getBackgroundTintList().getDefaultColor());
                if (vibrant.getText() != "获取不到") {
                    rgb.setText(Integer.toHexString(vibrant.getBackgroundTintList().getDefaultColor()));
                }else {
                    rgb.setText("获取不到");
                }
                break;
            case R.id.vibrant_dark:
                showColor.setBackgroundColor(vibrantDark.getBackgroundTintList().getDefaultColor());
                if (vibrantDark.getText() != "获取不到") {
                    rgb.setText(Integer.toHexString(vibrantDark.getBackgroundTintList().getDefaultColor()));
                }else {
                    rgb.setText("获取不到");
                }
                break;
            case R.id.vibrant_light:
                showColor.setBackgroundColor(vibrantLight.getBackgroundTintList().getDefaultColor());
                if (vibrantLight.getText() != "获取不到"){
                    rgb.setText(Integer.toHexString(vibrantLight.getBackgroundTintList().getDefaultColor()));
                }else {
                    rgb.setText("获取不到");
                }
                break;
            case R.id.muted:
                showColor.setBackgroundColor(muted.getBackgroundTintList().getDefaultColor());
                if (muted.getText() != "获取不到") {
                    rgb.setText(Integer.toHexString(muted.getBackgroundTintList().getDefaultColor()));
                }else {
                    rgb.setText("获取不到");
                }
                break;
            case R.id.muted_dark:
                showColor.setBackgroundColor(mutedDark.getBackgroundTintList().getDefaultColor());
                if (mutedDark.getText() != "获取不到") {
                    rgb.setText(Integer.toHexString(mutedDark.getBackgroundTintList().getDefaultColor()));
                }else {
                    rgb.setText("获取不到");
                }
                break;
            case R.id.muted_light:
                showColor.setBackgroundColor(mutedLight.getBackgroundTintList().getDefaultColor());
                if (mutedLight.getText() != "获取不到") {
                    rgb.setText(Integer.toHexString(mutedLight.getBackgroundTintList().getDefaultColor()));
                }else {
                    rgb.setText("获取不到");
                }
                break;
                default:
                    break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode != RESULT_CANCELED){
            switch (requestCode){
                case CHOOSE_PHOTO :
                    Uri uri = handleImageOnKitKat(data);
                    if (getRealFilePath(this,uri).equals("download")){
                        getBitmap(uri);
                    }else {
                        getBitmap(getRealFilePath(this,uri));
                    }

                    break;
            }
        }
    }

    private void getBitmap(Uri uri){

        Glide.with(this).load(uri).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                bitmap = drawableToBitmap(resource);
                image.setImageBitmap(bitmap);
                getPaletteColor();
            }
        });
    }

    private void getBitmap(String path){

        Glide.with(this).load(path).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                bitmap = drawableToBitmap(resource);
                image.setImageBitmap(bitmap);
                getPaletteColor();
            }
        });
    }

    private void getPaletteColor(){
        Palette.Builder builder1 = Palette.from(bitmap);
            builder1.generate(new Palette.PaletteAsyncListener() {@Override public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                if (palette.getVibrantSwatch() != null){
                    vibrant.setBackgroundTintList(ColorStateList.valueOf(palette.getVibrantSwatch().getRgb()));
                    vibrant.setText("有活力的");
                }else {
                    vibrant.setText("获取不到");
                }
            }
        });
        Palette.Builder builder2 = Palette.from(bitmap);
            builder2.generate(new Palette.PaletteAsyncListener() {@Override public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                if (palette.getDarkVibrantSwatch() != null){
                    vibrantDark.setBackgroundTintList(ColorStateList.valueOf(palette.getDarkVibrantSwatch().getRgb()));
                    vibrantDark.setText("有活力的 暗色");
                }else {
                    vibrantDark.setText("获取不到");
                }
            }
        });
        Palette.Builder builder3 = Palette.from(bitmap);
            builder3.generate(new Palette.PaletteAsyncListener() {@Override public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                if (palette.getLightVibrantSwatch() != null){
                    vibrantLight.setBackgroundTintList(ColorStateList.valueOf(palette.getLightVibrantSwatch().getRgb()));
                    vibrantLight.setText("有活力的 亮色");
                }else {
                    vibrantLight.setText("获取不到");
                }
            }
        });
        Palette.Builder builder4 = Palette.from(bitmap);
            builder4.generate(new Palette.PaletteAsyncListener() {@Override public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                if (palette.getMutedSwatch() != null){
                    muted.setBackgroundTintList(ColorStateList.valueOf(palette.getMutedSwatch().getRgb()));
                    muted.setText("柔和的");
                }else {
                    muted.setText("获取不到");
                }
            }
        });
        Palette.Builder builder5 = Palette.from(bitmap);
            builder5.generate(new Palette.PaletteAsyncListener() {@Override public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                if (palette.getDarkMutedSwatch() != null){
                    mutedDark.setBackgroundTintList(ColorStateList.valueOf(palette.getDarkMutedSwatch().getRgb()));
                    mutedDark.setText("柔和的 暗色");
                }else {
                    mutedDark.setText("获取不到");
                }
            }
        });
        Palette.Builder builder6 = Palette.from(bitmap);
            builder6.generate(new Palette.PaletteAsyncListener() {@Override public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                if (palette.getLightMutedSwatch() != null){
                    mutedLight.setBackgroundTintList(ColorStateList.valueOf(palette.getLightMutedSwatch().getRgb()));
                    mutedLight.setText("柔和的 亮色");
                }else {
                    mutedLight.setText("获取不到");
                }
            }
        });

    }
}
