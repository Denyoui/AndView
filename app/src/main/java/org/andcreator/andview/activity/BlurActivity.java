package org.andcreator.andview.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import org.andcreator.andview.R;
import org.andcreator.andview.service.UPThreadPoolManager;
import org.andcreator.andview.uilt.SetTheme;
import org.reactivestreams.Subscriber;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static org.andcreator.andview.uilt.ImageUtil.drawableToBitmap;

public class BlurActivity extends AppCompatActivity {

    private ImageView blurImage;
    private SeekBar adjust;
    private TextView number;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.setTheme(this);
        setContentView(R.layout.activity_blur);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        blurImage = findViewById(R.id.blur_image);
        adjust = findViewById(R.id.adjust);
        number = findViewById(R.id.number);

        Glide.with(this).load(R.drawable.blur).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                bitmap = drawableToBitmap(resource);
                blurImage.setImageBitmap(bitmap);
            }
        });

        adjust.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, final int i, boolean b) {
                Bitmap bitmaps = blurBitmap(BlurActivity.this, bitmap, 25f*i/100, 1f);
                if (bitmaps != null){
                    blurImage.setImageBitmap(bitmaps);
                    number.setText(25f*i/100+"");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 处理bitmap为高斯模糊图片
     * @param context 上下文
     * @param image   图片源
     * @param radius  模糊程度 0到25之间
     * @param scale   图片缩放比例, 该值越小越节省内存,模糊程度越敏感,0到1之间
     * @return 模糊的图片
     */
    public static Bitmap blurBitmap(Context context, Bitmap image, float radius, float scale) {
        Log.e("66666666666",radius+"");
        if (0 < radius && radius<= 25){
            // 计算图片缩小后的长宽
            int width = Math.round(image.getWidth() * scale);
            int height = Math.round(image.getHeight() * scale);
            // 将缩小后的图片做为预渲染的图片。
            Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
            // 创建一张渲染后的输出图片。
            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
            // 创建RenderScript内核对象
            RenderScript rs = RenderScript.create(context);
            // 创建一个模糊效果的RenderScript的工具对象
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
            // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
            Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
            // 设置渲染的模糊程度, 25f是最大模糊度
            blurScript.setRadius(radius);
            // 设置blurScript对象的输入内存
            blurScript.setInput(tmpIn);
            // 将输出数据保存到输出内存中
            blurScript.forEach(tmpOut);
            // 将数据填充到Allocation中
            tmpOut.copyTo(outputBitmap);
            return outputBitmap;
        }
        return null;
    }
}
