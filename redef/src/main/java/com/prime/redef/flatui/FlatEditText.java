package com.prime.redef.flatui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatEditText;

import com.prime.redef.RedefConfig;
import com.prime.redef.utils.ViewUtils;

public class FlatEditText extends AppCompatEditText {
    private Context context;
    private int backgroundColor = 0xffF5F5F5;
    private int strokeColor = 0xffBDBDBD;

    public FlatEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FlatEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    private GradientDrawable makeBackground() {
        return FlatUtils.radialBackground(backgroundColor, strokeColor, 5, 1f);
    }

    private void init() {
        setBackground(makeBackground());
        setAllCaps(false);

        int paddingX = (int) ViewUtils.dpToPx(12);
        int paddingY = (int) ViewUtils.dpToPx(12);

        setPadding(paddingX, paddingY, paddingX, paddingY);

        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        setTextColor(RedefConfig.getPrimaryTextColor());
        setHintTextColor(RedefConfig.getHintTextColor());
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        setBackground(makeBackground());
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        setBackground(makeBackground());
    }
}