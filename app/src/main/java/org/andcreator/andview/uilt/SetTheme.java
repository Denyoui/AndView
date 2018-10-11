package org.andcreator.andview.uilt;

import android.content.Context;
import android.content.SharedPreferences;

import org.andcreator.andview.R;

import static android.content.Context.MODE_PRIVATE;

public class SetTheme {
    //设置主题
    public static void setTheme(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("item", MODE_PRIVATE);
        int themeId = preferences.getInt("theme", 0);

        switch (themeId) {
            case R.style.AppTheme_Purple:
            case R.style.AppTheme_Red:
            case R.style.AppTheme_Pink:
            case R.style.AppTheme_Yellow:
            case R.style.AppTheme_Green:
            case R.style.AppTheme_Paint:
            case R.style.AppTheme_Blue:
                context.setTheme(themeId);
                break;
            default:
                context.setTheme(R.style.AppTheme);
                break;
        }
    }
    //设置主题
    public static void setDialogTheme(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("item", MODE_PRIVATE);
        int themeId = preferences.getInt("dialogtheme", 0);

        switch (themeId) {
            case R.style.DialogActivity_Purple:
            case R.style.DialogActivity_Red:
            case R.style.DialogActivity_Pink:
            case R.style.DialogActivity_Yellow:
            case R.style.DialogActivity_Green:
            case R.style.DialogActivity_Paint:
            case R.style.DialogActivity_Blue:
                context.setTheme(themeId);
                break;
            default:
                context.setTheme(R.style.DialogActivity);
                break;
        }
    }
    //设置主题
    public static void setTranslucentTheme(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("item", MODE_PRIVATE);
        int themeId = preferences.getInt("translucent", 0);

        switch (themeId) {
            case R.style.AppTheme_Purple_Translucent:
            case R.style.AppTheme_Red_Translucent:
            case R.style.AppTheme_Pink_Translucent:
            case R.style.AppTheme_Yellow_Translucent:
            case R.style.AppTheme_Green_Translucent:
            case R.style.AppTheme_Paint_Translucent:
            case R.style.AppTheme_Blue_Translucent:
                context.setTheme(themeId);
                break;
            default:
                context.setTheme(R.style.Translucent);
                break;
        }
    }
    //设置主题
    public static void setStartTheme(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("item", MODE_PRIVATE);
        int themeId = preferences.getInt("start", 0);

        switch (themeId) {
            case R.style.AppTheme_Purple_Start:
            case R.style.AppTheme_Red_Start:
            case R.style.AppTheme_Pink_Start:
            case R.style.AppTheme_Yellow_Start:
            case R.style.AppTheme_Green_Start:
            case R.style.AppTheme_Paint_Start:
            case R.style.AppTheme_Blue_Start:
                context.setTheme(themeId);
                break;
            default:
                context.setTheme(R.style.AppTheme_Start);
                break;
        }
    }
}
