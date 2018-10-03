package org.andcreator.andview.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.andcreator.andview.R;
import org.andcreator.andview.uilt.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ScreenshotActivity extends Activity {
    private ImageView mImageCupView;
    private MediaProjectionManager mMediaProjectionManager;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mScreenDensity;
    private ImageReader mImageReader;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    public static final int REQUEST_MEDIA_PROJECTION = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_Translucent);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_screenshot);
        //获取当前屏幕的像素点
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        //5 表示接受的权限的最多次数(拒绝5次就会崩溃)
        mImageReader = ImageReader.newInstance(mScreenWidth, mScreenHeight, PixelFormat.RGBA_8888, 5);
        //检查其版本号
        requestCapturePermission();
        mImageCupView = (ImageView) findViewById(R.id.cupView);
        startScreenShot();
    }

    private void startScreenShot() {
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            public void run() {
                //start virtual
                virtualDisplay();
            }
        }, 5);

        handler1.postDelayed(new Runnable() {
            public void run() {
                //capture the screen
                startCapture();
            }
        }, 30);
    }

    private void startCapture() {

        Image image = mImageReader.acquireLatestImage();
        if (image == null) {
            //开始截屏
            startScreenShot();
        } else {
            //保存截屏
            Bitmap bitmap = getSc(image);
            if (bitmap != null) {
                Toast.makeText(ScreenshotActivity.this,"截图已经成功保存到相册",Toast.LENGTH_SHORT).show();
                //直接设置动画效果
                setAnimation(bitmap);

            }

        }
    }

    public Bitmap getSc(Image params){

        Image image = params;

        int width = image.getWidth();
        int height = image.getHeight();
        final Image.Plane[] planes = image.getPlanes();
        final ByteBuffer buffer = planes[0].getBuffer();
        //每个像素的间距
        int pixelStride = planes[0].getPixelStride();
        //总的间距
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * width;
        Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        image.close();
        File fileImage = null;
        if (bitmap != null) {
            try {
                fileImage = new File(FileUtil.getScreenShotsName(getApplicationContext()));
                if (!fileImage.exists()) {
                    fileImage.createNewFile();
                }
                FileOutputStream out = new FileOutputStream(fileImage);
                if (out != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

                    out.flush();
                    out.close();
                    //发送广播给相册--更新相册图片
                    Intent media = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(fileImage);
                    media.setData(contentUri);
                    sendBroadcast(media);

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                fileImage = null;
            } catch (IOException e) {
                e.printStackTrace();
                fileImage = null;
            }
        }

        if (fileImage != null) {
            return bitmap;
        }
        return null;
    }

    private void setAnimation(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        ViewGroup.LayoutParams layoutParams = mImageCupView.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;
        //mImagerCupView是布局文件中创建的
        mImageCupView.setLayoutParams(layoutParams);
        mImageCupView.setImageBitmap(bitmap);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatory = ObjectAnimator.ofFloat(mImageCupView, "scaleY", 1.0f, 0.0f);
        ObjectAnimator animatorx = ObjectAnimator.ofFloat(mImageCupView, "scaleX", 1.0f, 0.0f);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //后面可以继续接着写其他活动，如页面的跳转等等
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.setDuration(500);
        animatorSet.play(animatory).with(animatorx);//两个动画同时开始
        animatorSet.start();
    }

    private void virtualDisplay() {
        if(mMediaProjection != null) {
            mVirtualDisplay = mMediaProjection.createVirtualDisplay("screen-mirror",
                    mScreenWidth, mScreenHeight, mScreenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    mImageReader.getSurface(), null, null);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void requestCapturePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Toast.makeText(this, "不能截屏", Toast.LENGTH_LONG).show();
            return;
        }
        //获取截屏的管理器
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION:
                if (resultCode == RESULT_OK && data != null) {
                    mMediaProjection = mMediaProjectionManager.getMediaProjection(Activity.RESULT_OK, data);
                }
                break;
        }
    }

    private void tearDownMediaProjection() {
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
    }

    private void stopVirtual() {
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
        mVirtualDisplay = null;
    }

    // 结束后销毁
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopVirtual();
        tearDownMediaProjection();
    }

}
