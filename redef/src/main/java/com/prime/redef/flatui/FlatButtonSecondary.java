package com.prime.redef.flatui;

import android.content.Context;
import android.util.AttributeSet;

import com.prime.redef.R;
import com.prime.redef.utils.AppUtils;

public class FlatButtonSecondary extends FlatButton {

    public FlatButtonSecondary(Context context) {
        super(context, null, R.attr.borderlessButtonStyle);
    }

    public FlatButtonSecondary(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.borderlessButtonStyle);
    }

    public FlatButtonSecondary(Context context, AttributeSet attributeSet, int style) {
        super(context, attributeSet, style);
    }

    @Override
    void init() {
        super.init();
        int accentColor = AppUtils.getAccentColor();
        setBackground(makeSelector(0xFFFAFAFA, accentColor, 5, 2));
        setTextColor(accentColor);
    }
}
