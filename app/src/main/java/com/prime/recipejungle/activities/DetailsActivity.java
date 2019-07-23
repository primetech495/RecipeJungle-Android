package com.prime.recipejungle.activities;

import com.prime.recipejungle.entities.Recipe;
import com.prime.recipejungle.fragments.DetailsFragment;
import com.prime.redef.app.InjectParameter;
import com.prime.redef.app.RedefActivity;
import com.prime.redef.app.configs.ActivityConfig;

public class DetailsActivity extends RedefActivity{
    @InjectParameter
    private Recipe recipe;

    @Override
    public boolean onBind() {
        return true;
    }

    @Override
    public void onConfig(ActivityConfig config) {
        config.setTabsEnabled(true);
        config.setActionbarEnabled(true);
        config.setHomeButton(true);
        config.setActionbarTitle("Details");
    }

    @Override
    public void onCreate() {
        fragmentController.startTransaction()
                .useFragment(DetailsFragment.class)
                    .withParameter(recipe)
                .commit();
    }

    @Override
    public boolean onHomeButtonClicked() {
        finish();
        return true;
    }
}
