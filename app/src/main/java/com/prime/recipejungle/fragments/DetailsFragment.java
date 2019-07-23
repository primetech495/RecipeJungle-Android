package com.prime.recipejungle.fragments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;

import com.prime.recipejungle.R;
import com.prime.recipejungle.activities.CreateActivity;
import com.prime.recipejungle.entities.Recipe;
import com.prime.recipejungle.entities.RecipeTag;
import com.prime.recipejungle.utils.Global;
import com.prime.redef.app.App;
import com.prime.redef.app.InjectParameter;
import com.prime.redef.app.RedefFragment;
import com.prime.redef.app.configs.FragmentConfig;
import com.prime.redef.app.configs.OptionsMenuConfig;
import com.prime.redef.app.configs.OptionsMenuItemConfig;
import com.prime.redef.json.JArray;
import com.prime.redef.network.ApiClient;
import com.prime.redef.utils.ObjectUtils;

import java.util.ArrayList;

public class DetailsFragment extends RedefFragment {
    private ApiClient client;
    private TextView etTitle;
    private TextView etDescription;
    private TextView etPortion;
    private TextView etPrepareTime;
    private TextView etIngredients;
    private TextView etTags;
    private TextView etSteps;

    @Override
    public void onConfig(FragmentConfig config) {
        config.setOptionsMenuConfig(new OptionsMenuConfig().addItems(
                new OptionsMenuItemConfig(1)
                        .setShowAsAction(false)
                        .setTitle("Share")
        ));
    }

    @InjectParameter
    private Recipe recipe;

    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        View content = inflater.inflate(R.layout.details_fragment, null);
        this.client = new ApiClient(Global.HOST);

        etTitle = content.findViewById(R.id.recipeTitle);
        etDescription = content.findViewById(R.id.recipeDescription);
        etPortion = content.findViewById(R.id.recipePortion);
        etPrepareTime = content.findViewById(R.id.recipePrepareTime);
        etIngredients = content.findViewById(R.id.recipeIngredients);
        etTags = content.findViewById(R.id.recipeTags);
        etSteps = content.findViewById(R.id.recipeSteps);

        etTitle.setText(recipe.Title);
        etDescription.setText(recipe.Text);
        etPortion.setText(String.valueOf(recipe.Portion));
        etPrepareTime.setText(String.valueOf(recipe.PrepareTime));


        ArrayList<String> tags = new ArrayList<>();
        if (recipe.getRecipeTags() != null) {
            for (RecipeTag tag : recipe.getRecipeTags())
                tags.add(tag.getTag().getText());
        }

        ArrayList<String> steps = new ArrayList<>();
        JArray stepsArray = JArray.parse(recipe.getSteps());
        if (stepsArray != null) {
            for (int i = 0; i < stepsArray.size(); i++) {
                steps.add(stepsArray.getString(i));
            }
        }

        ArrayList<String> ingredients = new ArrayList<>();
        JArray ingredientsArray = JArray.parse(recipe.getIngredients());
        if (ingredientsArray != null) {
            for (int i = 0; i < ingredientsArray.size(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }
        }

        etSteps.setText(ObjectUtils.join("\n", steps));
        etTags.setText(ObjectUtils.join("\n", tags));
        etIngredients.setText(ObjectUtils.join("\n", ingredients));

        return content;
    }

    public boolean onMenuItemClicked(int id) {
        if (id == 1) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
            i.putExtra(Intent.EXTRA_TEXT, "http://www.url.com");
            getAndroidActivity().startActivity(Intent.createChooser(i, "Share URL"));
            return true;
        }
        return false;
    }
}
