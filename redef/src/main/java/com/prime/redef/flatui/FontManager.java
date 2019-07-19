package com.prime.redef.flatui;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public final class FontManager
{
    private static AssetManager assets;

    public static void initialize(AssetManager assets)
    {
        FontManager.assets = assets;
    }

    public static Typeface GetTypeFaceGoogleRegular() {
        return Typeface.createFromAsset(assets, "fonts/GoogleSans-Regular.ttf");
    }

    public static void SetGoogleRegular(TextView tv) {
        Typeface tf = Typeface.createFromAsset(assets, "fonts/GoogleSans-Regular.ttf");
        tv.setTypeface(tf);
    }

    public static void SetGoogleMedium(TextView tv) {
        Typeface tf = Typeface.createFromAsset(assets, "fonts/GoogleSans-Medium.ttf");
        tv.setTypeface(tf);
    }

    public static void SetRobotoRegular(TextView tv) {
        Typeface tf = Typeface.createFromAsset(assets, "fonts/Roboto-Regular.ttf");
        tv.setTypeface(tf);
    }

    public static void SetGoogleRegular(Button b) {
        Typeface tf = Typeface.createFromAsset(assets, "fonts/GoogleSans-Regular.ttf");
        b.setTypeface(tf);
    }

    public static void SetGoogleMedium(Button b) {
        Typeface tf = Typeface.createFromAsset(assets, "fonts/GoogleSans-Medium.ttf");
        b.setTypeface(tf);
    }

    public static void SetRobotoRegular(Button b) {
        Typeface tf = Typeface.createFromAsset(assets, "fonts/Roboto-Regular.ttf");
        b.setTypeface(tf);
    }
}