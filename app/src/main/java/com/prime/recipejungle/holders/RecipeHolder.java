package com.prime.recipejungle.holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prime.recipejungle.R;

public class RecipeHolder extends RecyclerView.ViewHolder {
    private View root;
    private ImageView imageView;
    private TextView titleView;
    private TextView tagsView;
    private Button detailsButton;
    private Button editButton;
    private Button deleteButton;

    public RecipeHolder(View root) {
        super(root);
        this.root = root;

        this.imageView = root.findViewById(R.id.image);
        this.titleView = root.findViewById(R.id.text);
        this.detailsButton = root.findViewById(R.id.button1);
        this.tagsView = root.findViewById(R.id.text2);
    }

    public View getRoot() {
        return root;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public Button getDetailsButton() {
        return detailsButton;
    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public TextView getTagsView() {
        return tagsView;
    }
}

