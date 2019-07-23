package com.prime.recipejungle.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.prime.recipejungle.R;
import com.prime.recipejungle.activities.MyRecipesActivity;
import com.prime.recipejungle.utils.Global;
import com.prime.redef.app.App;
import com.prime.redef.app.RedefActivity;
import com.prime.redef.app.RedefFragment;
import com.prime.redef.app.configs.ActivityConfig;
import com.prime.redef.json.JObject;
import com.prime.redef.json.Json;
import com.prime.redef.network.ApiClient;
import com.prime.redef.network.ApiRequest;
import com.prime.redef.network.ApiRestHandler;
import com.prime.redef.network.GetRequest;
import com.prime.redef.network.Header;
import com.prime.redef.network.PostRequest;
import com.prime.redef.utils.ObjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CreateFragment extends RedefFragment {
    private ApiClient apiClient;
    private Button button;
    private EditText etTitle;
    private EditText etDescription;
    private EditText etPortion;
    private EditText etPrepareTime;
    private EditText etIngredients;
    private EditText etTags;
    private EditText etSteps;

    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        View content = inflater.inflate(R.layout.create_fragment, null);

        this.apiClient = new ApiClient(Global.HOST);
        etTitle = content.findViewById(R.id.recipeTitle);
        etDescription = content.findViewById(R.id.recipeDescription);
        etPortion = content.findViewById(R.id.recipePortion);
        etPrepareTime = content.findViewById(R.id.recipePrepareTime);
        etIngredients= content.findViewById(R.id.recipeIngredients);
        etTags = content.findViewById(R.id.recipeTags);
        etSteps = content.findViewById(R.id.recipeSteps);
        this.button = content.findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked();
            }
        });

        return content;
    }

    private void buttonClicked(){
        PostRequest request = new PostRequest("/api/recipe/create");
        final String title = etTitle.getText().toString();
        final String description = etDescription.getText().toString();
        final int portion = Integer.parseInt(etPortion.getText().toString());
        final int prepareTime = Integer.parseInt(etPrepareTime.getText().toString());
        final String ingredients = etIngredients.getText().toString();
        final String tags = etTags.getText().toString();
        final String steps= etSteps.getText().toString();

        HashMap<String,Object> body = new HashMap<>();
        try {
            body.put("Title", title);
            body.put("Text", description);
            body.put("Portion", portion);
            body.put("PrepareTime", prepareTime);
            body.put("Ingredients", ingredients.split("\n"));
            body.put("Tags", tags.split("\n"));
            body.put("Steps", steps.split("\n"));
            request.putHeader("Authorization", Global.PROPERTIES.getString("Authentication:",null));
            ((PostRequest) request).setJsonBody(Json.toJson(body));
        } catch (Exception e) {
            e.printStackTrace();
        }

        apiClient.execute(request, new ApiRestHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) throws Exception {
                String responseString = ObjectUtils.utf8String(responseBody);
                String message = Json.fromJson(responseString, String.class);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                App.startActivity(getContext(), MyRecipesActivity.class, null);
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

