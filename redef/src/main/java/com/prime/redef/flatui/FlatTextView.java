package com.prime.redef.flatui;

import android.content.Context;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.util.AttributeSet;

public class FlatTextView extends AppCompatTextView
{
    Context context;

    public FlatTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FlatTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    private void init()
    {
        FontManager.SetGoogleRegular(this);
    }
}