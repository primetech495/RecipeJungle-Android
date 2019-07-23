package com.prime.recipejungle.activities;

import com.prime.recipejungle.entities.Recipe;
import com.prime.recipejungle.fragments.UpdateFragment;

import com.prime.redef.app.InjectParameter;
import com.prime.redef.app.RedefActivity;
import com.prime.redef.app.configs.ActivityConfig;

public class UpdateActivity extends RedefActivity {

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
        config.setActionbarTitle("Update Recipe Page");
    }

    @Override
    public void onCreate() {
        fragmentController.startTransaction()
                .useFragment(UpdateFragment.class)
                .withParameter(recipe)
                .commit();
    }

    @Override
    public boolean onHomeButtonClicked() {
        finish();
        return true;
    }

}
