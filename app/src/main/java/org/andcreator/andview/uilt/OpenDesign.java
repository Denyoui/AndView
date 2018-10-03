package org.andcreator.andview.uilt;


import android.app.Application;

public class OpenDesign extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.init(getApplicationContext());
    }
}
