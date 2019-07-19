package com.prime.recipejungle.activities;

import com.prime.recipejungle.fragments.HelloFragment;
import com.prime.redef.app.App;
import com.prime.redef.app.RedefActivity;
import com.prime.redef.app.configs.ActivityConfig;

public class HelloActivity extends RedefActivity {

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
        config.setActionbarTitle("Hello Ekranı");
    }

    @Override
    public void onCreate() {
        fragmentController.startTransaction()
                .useFragment(HelloFragment.class)
                .commit();
    }

    @Override
    public boolean onHomeButtonClicked() {
        finish();
        return true;
    }
}