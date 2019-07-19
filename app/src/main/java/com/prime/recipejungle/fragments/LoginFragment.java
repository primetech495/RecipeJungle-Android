package com.prime.recipejungle.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.prime.recipejungle.R;
import com.prime.redef.app.RedefFragment;

public class LoginFragment extends RedefFragment {

    private Button button;

    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        View content = inflater.inflate(R.layout.login_fragment, null);
        final EditText usertext = content.findViewById(R.id.editText1);
        final EditText passtext = content.findViewById(R.id.editText2);

        this.button = content.findViewById(R.id.button1);

        String username = usertext.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return content;
    }
}