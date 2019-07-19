package com.prime.redef.flatui;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.prime.redef.R;

public abstract class FlatButton extends AppCompatButton {
    private Context context;

    public FlatButton(Context context) {
        super(context, null, R.attr.borderlessButtonStyle);
        this.context = context;
        init();
    }

    public FlatButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.borderlessButtonStyle);
        this.context = context;
        init();
    }

    public FlatButton(Context context, AttributeSet attributeSet, int style) {
        super(context, attributeSet, style);
        this.context = context;
        init();
    }

    StateListDrawable makeSelector(int color, int strokeColor, int cornerDp, int strokeDp) {
        int color1 = FlatUtils.simpleDark(color, 5);
        int color2 = FlatUtils.simpleDark(color, 10);

        StateListDrawable res = new StateListDrawable();
        //res.setAlpha(255);
        res.addState(new int[] { android.R.attr.state_pressed},
                FlatUtils.radialBackground(color2, strokeColor, cornerDp, strokeDp));
        res.addState(new int[] { android.R.attr.state_selected },
                FlatUtils.radialBackground(color2, strokeColor, cornerDp, strokeDp));
        res.addState(new int[] { android.R.attr.state_focused },
                FlatUtils.radialBackground(color1, strokeColor, cornerDp, strokeDp));
        res.addState(new int[] { },
                FlatUtils.radialBackground(color, strokeColor, cornerDp, strokeDp));
        return res;
    }

    void init() {
        setAllCaps(false);
    }
}
