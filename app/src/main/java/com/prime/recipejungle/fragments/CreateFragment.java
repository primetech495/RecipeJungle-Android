package com.prime.recipejungle.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.prime.recipejungle.R;
import com.prime.recipejungle.utils.Constants;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CreateFragment extends RedefFragment {
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

        final ApiClient client = new ApiClient(Constants.HOST);
        final ApiRequest request = new PostRequest("/api/recipe/create");

        etTitle = content.findViewById(R.id.recipeTitle);
        etDescription = content.findViewById(R.id.recipeDescription);
        etPortion = content.findViewById(R.id.recipePortion);
        etPrepareTime = content.findViewById(R.id.recipePrepareTime);
        etIngredients= content.findViewById(R.id.recipeIngredients);
        etTags = content.findViewById(R.id.recipeTags);
        etSteps = content.findViewById(R.id.recipeSteps);
        this.button = content.findViewById(R.id.button1);


        button.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v)  {
                final String title = etTitle.getText().toString();
                final String description = etTitle.getText().toString();
                final int portion = Integer.parseInt(etPortion.getText().toString());
                final int prepareTime = Integer.parseInt(etPrepareTime.getText().toString());
                final String ingredients = etIngredients.getText().toString();
                final String tags = etTags.getText().toString();
                final String steps= etSteps.getText().toString();

                HashMap<String,Object> obj = new HashMap<>();
                try {
                    obj.put("Title", title);
                    obj.put("Text", description);
                    obj.put("Portion", portion);
                    obj.put("PrepareTime", prepareTime);
                    obj.put("Ingredients", ingredients);
                    obj.put("Tags", tags);
                    obj.put("Steps", steps);
                    ((PostRequest) request).setJsonBody(Json.toJson(obj));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                client.execute(request, new ApiRestHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) throws Exception {
                        super.onSuccess(statusCode, headers, responseBody);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        super.onFailure(statusCode, headers, responseBody, error);
                    }
                });
            }
        });

        return content;
    }
}

