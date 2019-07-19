package com.prime.redef.flatui;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import com.prime.redef.utils.ViewUtils;

class FlatUtils {

    public static GradientDrawable radialBackground(int color, int strokeColor, float corderDp, float strokeDp) {
        float cornerPixel = ViewUtils.dpToPx(corderDp);
        float strokePixel = ViewUtils.dpToPx(strokeDp);
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] { cornerPixel, cornerPixel, cornerPixel, cornerPixel, cornerPixel, cornerPixel, cornerPixel, cornerPixel });
        shape.setColor(color);
        shape.setStroke((int)strokePixel, strokeColor);
        return shape;
    }

    public static int simpleDark(int color, int delta) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        r -= delta;
        g -= delta;
        b -= delta;
        if (r < 0) r = 0;
        if (g < 0) g = 0;
        if (b < 0) b = 0;
        return Color.argb(a, r, g, b);
    }
}
