package com.prime.recipejungle.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.prime.recipejungle.R;
import com.prime.recipejungle.entities.Recipe;
import com.prime.recipejungle.holders.RecipeHolder;
import com.prime.redef.app.RedefFragment;
import com.prime.redef.app.configs.FragmentConfig;
import com.prime.redef.app.configs.OptionsMenuConfig;
import com.prime.redef.app.configs.OptionsMenuItemConfig;
import com.prime.redef.app.listeners.ISearchViewListener;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;

public class MyRecipesFragment extends RedefFragment {

    private MyAdapter adapter;

    @Override
    public void onConfig(FragmentConfig config) {
        config.setOptionsMenuConfig(new OptionsMenuConfig().addItems(
                new OptionsMenuItemConfig(1)
                        .setShowAsAction(true)
                        .setTitle("Search...")
                        .setSearch(true)
        ));
    }

    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.my_recipes_fragment, null);

        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //PaddedDividerItemDecoration decoration = new PaddedDividerItemDecoration(context, layoutManager.getOrientation());
        //decoration.setLeftMarginDp(76);
        //decoration.setRightMarginDp(16);
        //recyclerView.addItemDecoration(decoration);

        adapter = new MyAdapter(new ArrayList<Recipe>());
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onMenuCreated() {
        searchViewController.setListener(new ISearchViewListener() {
            @Override
            public void onSearchViewClosed() {
                //adapter.updateSearchText("");
            }

            @Override
            public void onSearchViewOpened() {

            }

            @Override
            public boolean onSearchViewSubmit(String query) {
                return false;
            }

            @Override
            public boolean onSearchViewChange(String newText) {
                //adapter.updateSearchText(newText);
                return false;
            }

            @Override
            public void onSearchViewItemClicked(String item) {

            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter<RecipeHolder> {
        private ArrayList<Recipe> dataSet;

        MyAdapter(ArrayList<Recipe> dataSet) {
            this.dataSet = dataSet;
        }

        @NonNull
        @Override
        public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recipe, parent, false);

            return new RecipeHolder(root);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
            /*holder.getText1().setText(dataSet.get(position).getCode());
            holder.getText2().setText(dataSet.get(position).getName());
            holder.getRoot().setOnClickListener(new SingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    onClassClicked(dataSet.get(holder.getAdapterPosition()));
                }
            });

            int color = ColorScheme.SCHEME2.getColor(holder.getText1().getText().subSequence(0, 3));

            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(4)
                    .bold()
                    .endConfig()
                    .buildRound(holder.getText1().getText().charAt(0) + "", color);

            holder.getImageView().setImageDrawable(drawable);

            FontManager.SetGoogleMedium(holder.getText1());
            FontManager.SetGoogleRegular(holder.getText2());*/
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }
}
