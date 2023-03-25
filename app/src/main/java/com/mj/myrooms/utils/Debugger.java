package com.mj.myrooms.utils;

import android.util.Log;


public class Debugger {
    public static final String TAG = "LOG";

    private final static boolean LOG_DISPLAY = true;

    public static void logE(String message) {
        if (LOG_DISPLAY)
            Log.e(TAG, message);
    }

    public static void logE(String tag, String message) {
        if (LOG_DISPLAY)
            Log.e(TAG, tag + " --> " + message);
    }

    public static void logD(String message) {
        if (LOG_DISPLAY)
            Log.d(TAG, message);
    }

    public static void logD(String tag, String message) {
        if (LOG_DISPLAY)
            Log.d(TAG, tag + " --> " + message);
    }

    public static void logI(String message) {
        if (LOG_DISPLAY)
            Log.i(TAG, message);
    }

    public static void logI(String tag, String message) {
        if (LOG_DISPLAY)
            Log.i(TAG, tag + " --> " + message);
    }
}
