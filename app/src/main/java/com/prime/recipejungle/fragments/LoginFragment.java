package com.prime.recipejungle.fragments;

import android.content.Context;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.prime.recipejungle.R;
import com.prime.redef.app.RedefFragment;

public class LoginFragment extends RedefFragment {

    private Button button;

    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        final View content = inflater.inflate(R.layout.login_fragment, null);
        final EditText usertext = content.findViewById(R.id.editText1);
        final EditText passtext = content.findViewById(R.id.editText2);
        this.button = content.findViewById(R.id.button1);

        passtext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usertext.getText().toString();
                String password = passtext.getText().toString();
            }
        });

        return content;
    }
}