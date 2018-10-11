package org.andcreator.andview.uilt;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class ImageUtil {

    public static final int CHOOSE_PHOTO = 2;
    private Activity mActivity;
    private static Context mContext;

    public ImageUtil(Context context, Activity activity){
        mActivity = activity;
        mContext = context;
    }

    public void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        mActivity.startActivityForResult(intent,CHOOSE_PHOTO);
    }

    //系统版本在4.4以下的处理方式
    public static String handleImageBeforeKitKat(Intent data) { //直接将Uri传入到getImagePath()即可
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        return imagePath;
    }

    //系统版本在4.4及以上处理图片方式
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String handleImageOnKitKat(Intent data) { //将Uri封装进行解析
        String imagePath = null;
        Uri uri = data.getData();
        //判断Uri类型
        if (DocumentsContract.isDocumentUri(mContext,uri)) {
            //如果是documents 类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID+"="+id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是File类型的Uri，则直接获取图片路径
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    private static String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取图片真实路径
        Cursor cursor = mContext.getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
