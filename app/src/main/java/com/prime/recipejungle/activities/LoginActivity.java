package com.prime.recipejungle.activities;

import com.prime.recipejungle.fragments.LoginFragment;
import com.prime.redef.app.RedefActivity;
import com.prime.redef.app.configs.ActivityConfig;

public class LoginActivity extends RedefActivity {

    //@InjectParameter
    //private TodoElement todoElement;

    @Override
    public boolean onBind() {
        return true;
    }

    @Override
    public void onConfig(ActivityConfig config) {
        config.setTabsEnabled(true);
        config.setActionbarEnabled(true);
        config.setHomeButton(true);
        config.setActionbarTitle("LOGIN");
    }

    @Override
    public void onCreate() {
        fragmentController.startTransaction()
                .useFragment(LoginFragment.class)
                .commit();
    }

    @Override
    public boolean onHomeButtonClicked() {
        finish();
        return true;
    }
}