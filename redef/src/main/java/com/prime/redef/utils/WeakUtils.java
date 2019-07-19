package com.prime.redef.utils;

import android.app.Application;
import android.content.Context;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

public final class WeakUtils {
    private static WeakReference<Context> applicationContext;
    private static WeakReference<Application> application;

    public static void initialize(Application application) {
        WeakUtils.application = new WeakReference<>(application);
        WeakUtils.applicationContext = new WeakReference<>(application.getApplicationContext());
    }

    @Nullable
    public static Context getAppContext() {
        return applicationContext == null ? null : applicationContext.get();
    }

    @Nullable
    public static Application getApp() {
        return application == null ? null : application.get();
    }
}
