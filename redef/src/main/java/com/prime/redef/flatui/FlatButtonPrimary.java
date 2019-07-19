package com.prime.redef.flatui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.prime.redef.R;
import com.prime.redef.utils.AppUtils;

public class FlatButtonPrimary extends FlatButton {

    public FlatButtonPrimary(Context context) {
        super(context, null, R.attr.borderlessButtonStyle);
    }

    public FlatButtonPrimary(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.borderlessButtonStyle);
    }

    public FlatButtonPrimary(Context context, AttributeSet attributeSet, int style) {
        super(context, attributeSet, style);
    }

    @Override
    void init() {
        super.init();
        setBackground(makeSelector(AppUtils.getAccentColor(), 0, 5, 0));
        setTextColor(Color.WHITE);
    }
}
