package com.prime.redef.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.prime.redef.R;

import java.io.InputStream;

public class AppUtils {
    private static int versionCode;
    private static String versionName;
    private static String packageName;

    private static int accentColor;

    private static Resources resources;

    public static void initialize(@NonNull Application application) {
        Context context = application.getApplicationContext();
        resources = context.getResources();

        try {
            packageName = context.getPackageName();
            PackageInfo p = context.getPackageManager().getPackageInfo(packageName, 0);
            versionCode = p.versionCode;
            versionName = p.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException("AppUtils is unable to initialize");
        }

        accentColor = ContextCompat.getColor(context, R.color.colorAccent);
    }

    public static int getVersionCode() {
        return versionCode;
    }

    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getPackageName() {
        return packageName;
    }

    public static String getVersionName() {
        return versionName;
    }

    public static void openMarket() {
        Context appContext = WeakUtils.getAppContext();
        if (appContext == null) return;

        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK /*| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS*/);

        try {
            appContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK /*| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS*/);

            appContext.startActivity(intent);
        }
    }

    public static void openLink(String uri) {
        Context appContext = WeakUtils.getAppContext();
        if (appContext == null) return;

        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(uri));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK /*| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS*/);

        try {
            appContext.startActivity(intent);
        } catch (ActivityNotFoundException ignored) {

        }
    }

    public static int getAccentColor() {
        return accentColor;
    }

    public static String readRawText(int resourceId) {
        try {
            InputStream in_s = resources.openRawResource(resourceId);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            return new String(b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("HardwareIds")
    @NonNull
    public static String makeMetaData() {
        return "SERIAL: " + Build.SERIAL + '\n' +
                "MODEL: " + Build.MODEL + '\n' +
                "ID: " + Build.ID + '\n' +
                "MANUFACTURER: " + Build.MANUFACTURER + '\n' +
                "BRAND: " + Build.BRAND + '\n' +
                "TYPE: " + Build.TYPE + '\n' +
                "USER: " + Build.USER + '\n' +
                "BASE: " + Build.VERSION_CODES.BASE + '\n' +
                "INCREMENTAL " + Build.VERSION.INCREMENTAL + '\n' +
                "SDK  " + Build.VERSION.SDK_INT + '\n' +
                "BOARD: " + Build.BOARD + '\n' +
                "BRAND " + Build.BRAND + '\n' +
                "HOST " + Build.HOST + '\n' +
                "FINGERPRINT: " + Build.FINGERPRINT + '\n' +
                "RELEASE: " + Build.VERSION.RELEASE + '\n';
    }
}
