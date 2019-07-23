package com.prime.recipejungle.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.prime.recipejungle.R;
import com.prime.recipejungle.entities.Recipe;
import com.prime.recipejungle.entities.RecipeTag;
import com.prime.recipejungle.utils.Global;
import com.prime.redef.app.InjectParameter;
import com.prime.redef.app.RedefFragment;
import com.prime.redef.json.JObject;
import com.prime.redef.json.Json;
import com.prime.redef.network.ApiClient;
import com.prime.redef.network.ApiRestHandler;
import com.prime.redef.network.GetRequest;
import com.prime.redef.network.Header;
import com.prime.redef.utils.ObjectUtils;

public class DetailsFragment extends RedefFragment {
    private Recipe recipe;
    GetRequest request;
    ApiClient client;
    private TextView etTitle;
    private TextView etDescription;
    private TextView etPortion;
    private TextView etPrepareTime;
    private TextView etIngredients;
    private TextView etTags;
    private TextView etSteps;

    @InjectParameter
    private int recipeId;

    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        View content = inflater.inflate(R.layout.details_fragment, null);
        request = new GetRequest("/api/recipe/recipe?id="+recipeId);
        request.putHeader("Authorization", Global.PROPERTIES.getString("Authentication:",null));
        this.client = new ApiClient(Global.HOST);

        etTitle = content.findViewById(R.id.recipeTitle);
        etDescription = content.findViewById(R.id.recipeDescription);
        etPortion = content.findViewById(R.id.recipePortion);
        etPrepareTime = content.findViewById(R.id.recipePrepareTime);
        etIngredients= content.findViewById(R.id.recipeIngredients);
        etTags = content.findViewById(R.id.recipeTags);
        etSteps = content.findViewById(R.id.recipeSteps);

        client.execute(request, new ApiRestHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) throws Exception {
                String responseString = ObjectUtils.utf8String(responseBody);
                Recipe recipe = Json.fromJson(responseString,Recipe.class);
                etTitle.setText(recipe.Title);
                //etDescription.setText(recipe.Text);
                //etPortion.setText(recipe.Portion);
                //etPrepareTime.setText(recipe.PrepareTime);
                //etIngredients.setText(recipe.Ingredients);
                //etSteps.setText(recipe.Steps);

                StringBuilder builder = new StringBuilder();
                for (RecipeTag tag: recipe.RecipeTags) {
                    builder.append(tag.getTag().getText());
                    builder.append("\n");
                }
                etTags.setText(builder.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
            }
        });
        return content;


    }
}
