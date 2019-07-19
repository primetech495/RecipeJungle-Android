package com.prime.recipejungle.fragments;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.prime.recipejungle.R;
import com.prime.redef.app.RedefFragment;
import com.prime.redef.network.PostRequest;
import com.prime.redef.utils.ViewUtils;

public class HelloFragment extends RedefFragment {

    private Button button;

    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        View content = inflater.inflate(R.layout.hello_fragment, null);
        final EditText editText = content.findViewById(R.id.editText1);
        this.button = content.findViewById(R.id.button1);

        String text = editText.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return content;
    }
}