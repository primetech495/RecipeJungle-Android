package com.prime.recipejungle.fragments;

import android.content.Context;
import android.text.Html;
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

public class RegisterFragment extends RedefFragment {

    private Button button;

    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        View content = inflater.inflate(R.layout.register_fragment, null);
        final EditText usertext = content.findViewById(R.id.editText1);
        final EditText passtext = content.findViewById(R.id.editText2);
        final EditText emailtext = content.findViewById(R.id.editText3);
        //final TextView reg = content.findViewById(R.id.view1);

        passtext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //((TextView) content.findViewById(R.id.view1)).setMovementMethod(LinkMovementMethod.getInstance());
       // ((TextView) content.findViewById(R.id.view1)).setText(Html.fromHtml(content.getResources().getString(R.string.string_with_links)));

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