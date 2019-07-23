package com.prime.recipejungle.fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.prime.recipejungle.R;
import com.prime.recipejungle.utils.Global;
import com.prime.redef.app.InjectParameter;
import com.prime.redef.app.RedefFragment;
import com.prime.redef.json.JObject;
import com.prime.redef.json.Json;
import com.prime.redef.network.ApiClient;
import com.prime.redef.network.ApiRestHandler;
import com.prime.redef.network.Header;
import com.prime.redef.network.GetRequest;
import com.prime.redef.network.PostRequest;
import com.prime.redef.utils.ObjectUtils;

import java.util.HashMap;

public class UpdateFragment extends RedefFragment {
    private ApiClient apiClient;
    private Button button;
    private EditText etTitle;
    private EditText etDescription;
    private EditText etPortion;
    private EditText etPrepareTime;
    private EditText etIngredients;
    private EditText etTags;
    private EditText etSteps;

    @InjectParameter
    //private int recipeID;

    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        View content = inflater.inflate(R.layout.update_fragment, null);
        GetRequest request = new GetRequest("/api/recipe/recipe?id=1"); //todo +recipeID
        this.apiClient = new ApiClient(Global.HOST);
        etTitle = content.findViewById(R.id.recipeTitle);
        etDescription = content.findViewById(R.id.recipeDescription);
        etPortion = content.findViewById(R.id.recipePortion);
        etPrepareTime = content.findViewById(R.id.recipePrepareTime);
        etIngredients= content.findViewById(R.id.recipeIngredients);
        etTags = content.findViewById(R.id.recipeTags);
        etSteps = content.findViewById(R.id.recipeSteps);
        this.button = content.findViewById(R.id.button1);

        apiClient.execute(request, new ApiRestHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) throws Exception {
                String responseString = ObjectUtils.utf8String(responseBody);
                JObject object = JObject.parse(responseString);
                String  title = object.getString("Title");
                Log.e("tny", title);
                String  description = object.getString("Text");
                int  portion = object.getInt("Portion");
                int  prep_time = object.getInt("PrepareTime");
                String  ingredients = object.getString("Ingredients");
                String  steps = object.getString("Steps");
                Object  tags = object.getObject("Tags");
                String tag =((JObject) tags).getString("Tag");


                etTitle.setText(title);
                etDescription.setText(description);
                etPortion.setText(String.valueOf(portion));
                etPrepareTime.setText(String.valueOf(prep_time));
                etSteps.setText(steps);
                etIngredients.setText(ingredients);
                etTags.setText(tags.toString());

                String message = Json.fromJson(responseString, String.class);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("tny", "failure2");
                String responseString = ObjectUtils.utf8String(responseBody);
                String message = Json.fromJson(responseString, String.class);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked();
            }
        });

        return content;
    }

    private void buttonClicked(){
       /* PostRequest request = new PostRequest("/api/recipe/update");

        final String new_title = etTitle.getText().toString();
        final String new_description = etTitle.getText().toString();
        final int new_portion = Integer.parseInt(etPortion.getText().toString());
        final int new_prepareTime = Integer.parseInt(etPrepareTime.getText().toString());
        final String new_ingredients = etIngredients.getText().toString();
        final String new_tags = etTags.getText().toString();
        final String new_steps= etSteps.getText().toString();

        HashMap<String,Object> body = new HashMap<>();
        try {
            body.put("Title", new_title);
            body.put("Text", new_description);
            body.put("Portion", new_portion);
            body.put("PrepareTime", new_prepareTime);
            body.put("Ingredients", new_ingredients);
            body.put("Tags", new_tags);
            body.put("Steps", new_steps);
            request.putHeader("Authorization", Global.PROPERTIES.getString("Authentication",null));
            ((PostRequest) request).setJsonBody(Json.toJson(body));
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

    }
}
