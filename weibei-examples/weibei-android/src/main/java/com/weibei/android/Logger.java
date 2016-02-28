package com.weibei.android;

import android.util.Log;

public class Logger {
    private static final String LOG_TAG = "WeibeiApplication";

    public static void LOG(String s, Object... args) {
        Log.d(LOG_TAG, String.format(s, args));
    }
}
