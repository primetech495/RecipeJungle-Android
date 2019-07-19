package com.prime.redef.utils;

import android.os.Looper;

import com.prime.redef.BuildConfig;

public final class DebugUtils {

    public static boolean isUiThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void assertUiThread() {
        if (BuildConfig.DEBUG) {
            if (!isUiThread()) {
                throw new RuntimeException("UI Thread assertion failed");
            }
        }
    }
}
