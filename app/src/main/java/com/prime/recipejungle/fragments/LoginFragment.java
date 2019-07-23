package com.prime.recipejungle.fragments;

import android.content.Context;
import android.provider.SyncStateContract;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.prime.recipejungle.R;
import com.prime.recipejungle.activities.CreateActivity;
import com.prime.recipejungle.activities.LoginActivity;
import com.prime.recipejungle.activities.MyRecipesActivity;
import com.prime.recipejungle.activities.RegisterActivity;
import com.prime.recipejungle.utils.Global;
import com.prime.redef.app.App;
import com.prime.recipejungle.utils.Global;
import com.prime.redef.app.RedefFragment;
import com.prime.redef.json.JObject;
import com.prime.redef.json.Json;
import com.prime.redef.network.ApiClient;
import com.prime.redef.network.ApiRequest;
import com.prime.redef.network.ApiRestHandler;
import com.prime.redef.network.Header;
import com.prime.redef.network.PostRequest;
import com.prime.redef.utils.ObjectUtils;

import java.util.HashMap;

public class LoginFragment extends RedefFragment {

    private Button button;
    private View content;
    private ApiClient apiClient;
    private EditText usertext;
    private EditText passtext;
    private String username;
    private String password;
    private View register;
    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        this.content = inflater.inflate(R.layout.login_fragment, null);
        this.register = content.findViewById(R.id.view1);
        this.usertext = content.findViewById(R.id.editText1);
        this.passtext = content.findViewById(R.id.editText2);

        passtext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        apiClient = new ApiClient(Global.HOST);

        this.button = content.findViewById(R.id.button1);

        passtext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usertext.getText()== null || passtext.getText() ==null )
                    return;

                username = usertext.getText().toString();
                password = passtext.getText().toString();

                buttonClicked();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.startActivity(getAndroidActivity(), RegisterActivity.class,null);
            }
        });


        return content;
    }

    private void buttonClicked() {
        PostRequest request = new PostRequest("/api/account/authenticate");
        HashMap<String, Object> body = new HashMap<>();
        body.put("Username", username);
        body.put("Password", password);

        request.setJsonBody(Json.toJson(body));

        apiClient.execute(request, new ApiRestHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseString = ObjectUtils.utf8String(responseBody);
                String message = Json.fromJson(responseString, String.class);
                Global.PROPERTIES.putString("Authentication:", message);
                App.startActivity(getAndroidActivity(), MyRecipesActivity.class,null);
                getAndroidActivity().finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String responseString = ObjectUtils.utf8String(responseBody);
                Toast.makeText(getContext(), responseString, Toast.LENGTH_SHORT).show();
            }
        });
    }
}