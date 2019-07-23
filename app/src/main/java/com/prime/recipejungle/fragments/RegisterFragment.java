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
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.prime.recipejungle.R;
import com.prime.recipejungle.activities.LoginActivity;
import com.prime.recipejungle.activities.RegisterActivity;
import com.prime.recipejungle.utils.Global;
import com.prime.redef.app.App;
import com.prime.redef.app.RedefFragment;
import com.prime.redef.json.Json;
import com.prime.redef.network.ApiClient;
import com.prime.redef.network.ApiRestHandler;
import com.prime.redef.network.Header;
import com.prime.redef.network.PostRequest;
import com.prime.redef.utils.ObjectUtils;

import java.util.HashMap;

public class RegisterFragment extends RedefFragment {

    private Button button;
    private EditText usertext;
    private EditText passtext;
    private EditText emailtext;
    private ApiClient apiClient;
    private String username;
    private String password;
    private String email;
    private View login;
    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {

        View content = inflater.inflate(R.layout.register_fragment, null);
        usertext = content.findViewById(R.id.editText1);
        passtext = content.findViewById(R.id.editText2);
        emailtext = content.findViewById(R.id.editText3);
        button = content.findViewById(R.id.button1);
        login = content.findViewById(R.id.view1);

        passtext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        apiClient = new ApiClient(Global.HOST);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usertext.getText()== null || passtext.getText() ==null || emailtext.getText()==null)
                    return;

                password = passtext.getText().toString();
                email = emailtext.getText().toString();
                username =usertext.getText().toString();

                buttonClicked();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.startActivity(getAndroidActivity(), LoginActivity.class,null);

            }
        });

        return content;
    }

    private void buttonClicked(){
        PostRequest request = new PostRequest("/api/account/register");
        HashMap<String, Object> body = new HashMap<>();
        body.put("Username", username);
        body.put("Password", password);
        body.put("Email",email);

        request.setJsonBody(Json.toJson(body));

        apiClient.execute(request, new ApiRestHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseString = ObjectUtils.utf8String(responseBody);
                String message = Json.fromJson(responseString, String.class);
                App.startActivity(getAndroidActivity(), LoginActivity.class,null);
                getAndroidActivity().finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String responseString = ObjectUtils.utf8String(responseBody);
                String message = Json.fromJson(responseString, String.class);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}