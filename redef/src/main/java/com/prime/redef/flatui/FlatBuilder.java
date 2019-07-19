package com.prime.redef.flatui;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prime.redef.R;
import com.prime.redef.utils.ViewUtils;

public class FlatBuilder {
    private final Context context;
    private final LinearLayout root;
    private int dpToPx4, dpToPx8, dpToPx12;
    private boolean htmlEnabled;

    public FlatBuilder(@NonNull Context context, @NonNull LinearLayout root) {
        this.context = context;
        this.root = root;
        if (root.getOrientation() != LinearLayout.VERTICAL)
            throw new IllegalStateException();

        dpToPx4 = (int) ViewUtils.dpToPx(4);
        dpToPx8 = (int) ViewUtils.dpToPx(8);
        dpToPx12 = (int) ViewUtils.dpToPx(12);
    }

    public void setHtmlEnabled(boolean htmlEnabled) {
        this.htmlEnabled = htmlEnabled;
    }

    private void addView(View view) {
        root.addView(view);
    }

    private ViewGroup.LayoutParams createLayoutParams(int marginTop, int marginBottom, int marginRight, int marginLeft) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = marginTop;
        params.bottomMargin = marginBottom;
        params.leftMargin = marginLeft;
        params.rightMargin = marginRight;
        return params;
    }

    private TextView addTextView(String text, int spSize, boolean bold) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(createLayoutParams(dpToPx4, dpToPx4, 0, 0));
        textView.setGravity(Gravity.CENTER);
        textView.setText(htmlEnabled ? Html.fromHtml(text) : text);
        if (bold)
            textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
        addView(textView);
        return textView;
    }

    public TextView addHeader1(String text) {
        return addTextView(text, 20, true);
    }

    public TextView addHeader2(String text) {
        return addTextView(text, 16, false);
    }

    public TextView addText(String text) {
        return addTextView(text, 14, false);
    }

    public FlatEditText addEditText() {
        return addEditText(false, false, null);
    }

    public FlatEditText addEditText(boolean monospace, boolean password, @Nullable String hint) {
        FlatEditText editText = new FlatEditText(context);
        editText.setLayoutParams(createLayoutParams(dpToPx4, dpToPx4, dpToPx4, dpToPx4));
        editText.setGravity(Gravity.CENTER);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        if (monospace)
            editText.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        if (password)
            editText.setTransformationMethod(new PasswordTransformationMethod());
        if (hint != null)
            editText.setHint(htmlEnabled ? Html.fromHtml(hint) : hint);
        addView(editText);
        return editText;
    }

    public FlatButton addButton(String text) {
        FlatButton button = new FlatButtonPrimary(context, null, R.attr.borderlessButtonStyle);
        button.setLayoutParams(createLayoutParams(dpToPx4, dpToPx4, dpToPx4, dpToPx4));
        button.setGravity(Gravity.CENTER);
        button.setText(htmlEnabled ? Html.fromHtml(text) : text);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        addView(button);
        return button;
    }

    public FlatButton addButtonSecondary(String text) {
        FlatButton button = new FlatButtonSecondary(context, null, R.attr.borderlessButtonStyle);
        button.setLayoutParams(createLayoutParams(dpToPx4, dpToPx4, dpToPx4, dpToPx4));
        button.setGravity(Gravity.CENTER);
        button.setText(htmlEnabled ? Html.fromHtml(text) : text);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        addView(button);
        return button;
    }

    public FlatButton addButtonDanger(String text) {
        FlatButton button = new FlatButtonDanger(context, null, R.attr.borderlessButtonStyle);
        button.setLayoutParams(createLayoutParams(dpToPx4, dpToPx4, dpToPx4, dpToPx4));
        button.setGravity(Gravity.CENTER);
        button.setText(htmlEnabled ? Html.fromHtml(text) : text);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        addView(button);
        return button;
    }

    public View addDummy() {
        View view = new View(context);
        view.setLayoutParams(createLayoutParams(dpToPx8, dpToPx8, 0, 0));
        addView(view);
        return view;
    }

    public View addEmpty() {
        View view = new View(context);
        addView(view);
        return view;
    }
}
