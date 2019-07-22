package com.prime.recipejungle.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.prime.recipejungle.R;
import com.prime.redef.app.RedefFragment;

public class DetailsFragment extends RedefFragment {

    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        View content = inflater.inflate(R.layout.details_fragment, null);
        return content;

    }
}
