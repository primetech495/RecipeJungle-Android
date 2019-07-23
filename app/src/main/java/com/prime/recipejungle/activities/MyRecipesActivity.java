package com.prime.recipejungle.activities;

import com.prime.recipejungle.fragments.CreateFragment;
import com.prime.recipejungle.fragments.MyRecipesFragment;
import com.prime.redef.app.RedefActivity;
import com.prime.redef.app.configs.ActivityConfig;

public class MyRecipesActivity extends RedefActivity {

    @Override
    public boolean onBind() {
        return true;
    }

    @Override
    public void onConfig(ActivityConfig config) {
        config.setTabsEnabled(true);
        config.setActionbarEnabled(true);
        config.setHomeButton(true);
        config.setActionbarTitle("My Recipes");
    }

    @Override
    public void onCreate() {
        fragmentController.startTransaction()
                .useFragment(MyRecipesFragment.class)
                .commit();
    }

    @Override
    public boolean onHomeButtonClicked() {
        finish();
        return true;
    }
}
