package com.prime.recipejungle.fragments;

import android.content.Context;
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
import com.prime.recipejungle.utils.Constants;
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
    private ApiClient apiClient;
    private EditText usertext;
    private EditText passtext;


    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        View content = inflater.inflate(R.layout.login_fragment, null);
        this.usertext = content.findViewById(R.id.editText1);
        this.passtext = content.findViewById(R.id.editText2);
        //final TextView reg = content.findViewById(R.id.view1);

        //passtext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //((TextView) content.findViewById(R.id.view1)).setMovementMethod(LinkMovementMethod.getInstance());
        // ((TextView) content.findViewById(R.id.view1)).setText(Html.fromHtml(content.getResources().getString(R.string.string_with_links)));

        passtext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        apiClient = new ApiClient(Constants.HOST);

        this.button = content.findViewById(R.id.button1);

        String username = usertext.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked();
            }
        });

        return content;
    }

    private void buttonClicked() {
        PostRequest request = new PostRequest("/api/account/authenticate");
        HashMap<String, Object> body = new HashMap<>();
        body.put("Username", "asdsadsd");
        body.put("Password", "asdsadsd");
        body.put("Email", "asdsadsd");
        request.putHeader("Authorization", "sadsad");
        request.setJsonBody(Json.toJson(body));

        apiClient.execute(request, new ApiRestHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseString = ObjectUtils.utf8String(responseBody);
                String message = Json.fromJson(responseString, String.class);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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