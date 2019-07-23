package com.prime.recipejungle.activities;

import com.prime.recipejungle.fragments.DetailsFragment;
import com.prime.redef.app.InjectParameter;
import com.prime.redef.app.RedefActivity;
import com.prime.redef.app.configs.ActivityConfig;

public class DetailsActivity extends RedefActivity{
    @InjectParameter
    private int recipeID;

    @Override
    public boolean onBind() {
        return true;
    }

    @Override
    public void onConfig(ActivityConfig config) {
        config.setTabsEnabled(true);
        config.setActionbarEnabled(true);
        config.setHomeButton(true);
        config.setActionbarTitle("DETAILS");
    }

    @Override
    public void onCreate() {
        fragmentController.startTransaction()
                .useFragment(DetailsFragment.class)
                    .withParameter(recipeID)
                .commit();
    }

    @Override
    public boolean onHomeButtonClicked() {
        finish();
        return true;
    }
}
