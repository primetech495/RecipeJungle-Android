package com.prime.recipejungle.fragments;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.prime.redef.app.RedefFragment;
import com.prime.redef.utils.ViewUtils;

public class HelloFragment extends RedefFragment {

    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        String html = "SLm";

        ScrollView root = new ScrollView(context);

        TextView textView = new TextView(context);
        textView.setText(Html.fromHtml(html));

        int padding = (int) ViewUtils.dpToPx(8);
        textView.setPadding(padding, padding, padding, padding);

        root.addView(textView);
        return root;
    }
}