package com.hk.main.utils;

import android.util.Log;

/**
 * Created by Hovhannisyan.Karo on 03.11.2017.
 */

public class LogUtils {
    private static final boolean isDebug = true;
    private static final String TAG = "HK_LOG";

    public static void d(String msg){
        Log.d(TAG, msg);
    }

    public static void d(String tag, String msg){
        Log.d(tag, msg);
    }

    public static void e(String msg){
        Log.e(TAG, msg);
    }

    public static void e(String tag, String msg){
        Log.e(tag, msg);
    }

}
