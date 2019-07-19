package com.prime.recipejungle.activities;

import com.prime.recipejungle.fragments.LoginFragment;
import com.prime.recipejungle.fragments.RegisterFragment;
import com.prime.redef.app.RedefActivity;
import com.prime.redef.app.configs.ActivityConfig;

public class RegisterActivity extends RedefActivity {

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
        config.setActionbarTitle("REGISTER");
    }

    @Override
    public void onCreate() {
        fragmentController.startTransaction()
                .useFragment(RegisterFragment.class)
                .commit();
    }

    @Override
    public boolean onHomeButtonClicked() {
        finish();
        return true;
    }
}