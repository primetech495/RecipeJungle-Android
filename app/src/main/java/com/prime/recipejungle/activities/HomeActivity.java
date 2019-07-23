package com.prime.recipejungle.activities;

import com.prime.recipejungle.fragments.HomeFragment;
import com.prime.recipejungle.fragments.LoginFragment;
import com.prime.redef.app.RedefActivity;
import com.prime.redef.app.configs.ActivityConfig;

public class HomeActivity extends RedefActivity {

    @Override
    public boolean onBind() {
        return true;
    }

    @Override
    public void onConfig(ActivityConfig config) {
        config.setTabsEnabled(true);
        config.setActionbarEnabled(true);
        config.setHomeButton(true);
        config.setActionbarTitle("HOME");
    }

    @Override
    public void onCreate() {
        fragmentController.startTransaction()
                .useFragment(HomeFragment.class)
                .commit();
    }

    @Override
    public boolean onHomeButtonClicked() {
        finish();
        return true;
    }
}
