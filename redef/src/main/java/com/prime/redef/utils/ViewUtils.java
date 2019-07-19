package com.prime.redef.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ViewUtils {

    private static Resources resources;

    public static void initialize(@NonNull Context context) {
        resources = context.getResources();
    }

    public static float dpToPx(float dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

    public static void makeClickable(ViewGroup viewGroup) {
        TypedValue outValue = new TypedValue();
        viewGroup.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        viewGroup.setBackgroundResource(outValue.resourceId);
        viewGroup.setClickable(true);
    }

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void toast(@Nullable String text) {
        if (text == null) return;
        Context context = WeakUtils.getAppContext();
        if (context == null) return;
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
