package com.prime.redef.flatui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.prime.redef.R;

public class FlatButtonDanger extends FlatButton {

    public FlatButtonDanger(Context context) {
        super(context, null, R.attr.borderlessButtonStyle);
    }

    public FlatButtonDanger(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.borderlessButtonStyle);
    }

    public FlatButtonDanger(Context context, AttributeSet attributeSet, int style) {
        super(context, attributeSet, style);
    }

    @Override
    void init() {
        super.init();
        setBackground(makeSelector(0xffC62F3E, 0, 5, 0));
        setTextColor(Color.WHITE);
    }
}
