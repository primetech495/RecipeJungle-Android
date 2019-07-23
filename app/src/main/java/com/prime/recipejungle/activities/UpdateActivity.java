package com.prime.recipejungle.activities;

import com.prime.recipejungle.fragments.UpdateFragment;

import com.prime.redef.app.RedefActivity;
import com.prime.redef.app.configs.ActivityConfig;

public class UpdateActivity extends RedefActivity {
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
                .commit();
    }

    @Override
    public boolean onHomeButtonClicked() {
        finish();
        return true;
    }

}
