package com.prime.recipejungle.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prime.recipejungle.R;
import com.prime.recipejungle.activities.CreateActivity;
import com.prime.recipejungle.activities.DetailsActivity;
import com.prime.recipejungle.activities.MyRecipesActivity;
import com.prime.recipejungle.activities.UpdateActivity;
import com.prime.recipejungle.entities.Recipe;
import com.prime.recipejungle.entities.RecipeTag;
import com.prime.recipejungle.holders.RecipeHolder;
import com.prime.recipejungle.utils.Global;
import com.prime.recipejungle.utils.PaddedDividerItemDecoration;
import com.prime.redef.app.App;
import com.prime.redef.app.RedefFragment;
import com.prime.redef.app.configs.FragmentConfig;
import com.prime.redef.app.configs.OptionsMenuConfig;
import com.prime.redef.app.configs.OptionsMenuItemConfig;
import com.prime.redef.app.listeners.ISearchViewListener;
import com.prime.redef.json.JArray;
import com.prime.redef.json.JObject;
import com.prime.redef.json.Json;
import com.prime.redef.network.ApiClient;
import com.prime.redef.network.ApiRequest;
import com.prime.redef.network.ApiRestHandler;
import com.prime.redef.network.GetRequest;
import com.prime.redef.network.Header;
import com.prime.redef.utils.ObjectUtils;

import java.util.ArrayList;

public class HomeFragment extends RedefFragment {

    private MyAdapter adapter;
    private ApiClient apiClient;

    @Override
    public void onConfig(FragmentConfig config) {
        config.setOptionsMenuConfig(new OptionsMenuConfig().addItems(
                new OptionsMenuItemConfig(1)
                        .setShowAsAction(true)
                        .setTitle("Profile"),
                new OptionsMenuItemConfig(2)
                        .setShowAsAction(false)
                        .setTitle("My Recipes")
        ));
    }

    @Override
    public View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.my_recipes_fragment, null);

        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        PaddedDividerItemDecoration decoration = new PaddedDividerItemDecoration(context, layoutManager.getOrientation());
        decoration.setLeftMarginDp(4);
        decoration.setRightMarginDp(4);
        recyclerView.addItemDecoration(decoration);

        apiClient = new ApiClient(Global.HOST);

        GetRequest request = new GetRequest("/api/recipe/list");
        request.putHeader("Authorization", Global.PROPERTIES.getString("Authentication:",null));
        apiClient.execute(request, new ApiRestHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseString = ObjectUtils.utf8String(responseBody);
                JArray array = JArray.parse(responseString);
                ArrayList<Recipe> recipes = new ArrayList<>();
                for (int i = 0; i < array.size(); i++){
                    JObject obj = array.getObject(i);
                    Recipe recipe = Json.fromJson(obj.toString(), Recipe.class);
                    recipes.add(recipe);
                }
                adapter.updateDataset(recipes);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String responseString = ObjectUtils.utf8String(responseBody);
                Toast.makeText(getContext(), responseString, Toast.LENGTH_SHORT).show();
            }
        });

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
            final Recipe recipe = dataSet.get(position);
            holder.getTitleView().setText(recipe.getTitle());

            StringBuilder sb = new StringBuilder();
            sb.append("Tags: ");
            if (recipe.RecipeTags != null) {
                for (RecipeTag tag : recipe.getRecipeTags()) {
                    sb.append(tag.getTag().getText());
                    sb.append(", ");
                }
            }
            String tags = sb.toString();
            tags = tags.substring(0, tags.length() - 2);
            holder.getTagsView().setText(tags);

            final Context context = getAndroidActivity();
            if (context == null) return;

            holder.getDetailsButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.startActivity(context, DetailsActivity.class, recipe.Id);
                }
            });




        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        public void updateDataset(ArrayList<Recipe> recipes) {
            this.dataSet = recipes;
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean onMenuItemClicked(int id) {
        if (id == 1) {
            App.startActivity(getAndroidActivity(), MyRecipesActivity.class, null);
            return true;
        }
        if (id == 2) {
            App.startActivity(getAndroidActivity(), MyRecipesActivity.class, null);
            return true;
        }
        return false;
    }
}
