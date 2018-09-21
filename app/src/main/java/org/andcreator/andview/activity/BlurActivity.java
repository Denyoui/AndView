package org.andcreator.andview.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import org.andcreator.andview.R;
import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class BlurActivity extends AppCompatActivity {

    private ImageView blurImage;
    private SeekBar adjust;
    private TextView number;
    private Bitmap showBitmap;
    private Bitmap bitmap;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        blurImage = findViewById(R.id.blur_image);
        adjust = findViewById(R.id.adjust);
        number = findViewById(R.id.number);
        Glide.with(this).load(R.drawable.blur).asBitmap()//强制Glide返回一个Bitmap对象
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {

                        showBitmap = resource;
                        bitmap = resource;
                        blurImage.setImageBitmap(bitmap);
                    }

                });


        adjust.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int i, boolean b) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("55555555555",i+"");
                            Bitmap bitmap1 = blurBitmap(BlurActivity.this, bitmap, 25f*i/100, 1f);
                            showBitmap = bitmap1;
                            num = (int) (25f*i/100);
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    blurImage.setImageBitmap(showBitmap);
                    number.setText(num+"");
                    break;
                default:
                    break;
            }
        }
    };

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
}
