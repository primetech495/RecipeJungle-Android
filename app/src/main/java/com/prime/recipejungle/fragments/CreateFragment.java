package com.prime.recipejungle.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.prime.recipejungle.R;
import com.prime.redef.app.RedefActivity;
import com.prime.redef.app.RedefFragment;
import com.prime.redef.app.configs.ActivityConfig;

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
                String title = etTitle.getText().toString();
                String description = etTitle.getText().toString();
                int portion = Integer.parseInt(etPortion.getText().toString());
                int prepareTime = Integer.parseInt(etPrepareTime.getText().toString());
                String ingredients = etIngredients.getText().toString();
                String tags = etTags.getText().toString();
                String steps= etSteps.getText().toString();
            }
        });

        return content;
    }
}

