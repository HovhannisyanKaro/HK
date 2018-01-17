package com.hk.main.app;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;

/**
 * Created by Hovhannisyan.Karo on 12.11.2017.
 */

public class MyApplication extends Application {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
}
