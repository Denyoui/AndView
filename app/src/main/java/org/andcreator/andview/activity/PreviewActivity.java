package org.andcreator.andview.activity;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView img = new ImageView(this);
        String path = getIntent().getStringExtra("path");
        if(path != null){

//            FileInputStream fis;
//            try {
//                fis = new FileInputStream(path);
//                Bitmap bitmap  = BitmapFactory.decodeStream(fis);
//
//                Bitmap image = rotaingImageView(90,bitmap);
//                try {
//                    File file = new File(path);
//                    FileOutputStream out = new FileOutputStream(file);
//                    image.compress(Bitmap.CompressFormat.JPEG, 100, out);
//                    out.flush();
//                    out.close();
//
//                    //保存图片后发送广播通知更新数据库
//                    Uri uri = Uri.fromFile(file);
//                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                img.setImageBitmap(image);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            img.setImageURI(Uri.fromFile(new File(path)));
        }
        setContentView(img);
    }

    public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

}
