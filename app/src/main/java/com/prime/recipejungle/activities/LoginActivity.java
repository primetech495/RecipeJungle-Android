package com.prime.recipejungle.activities;

import com.prime.redef.app.RedefActivity;
import com.prime.redef.app.configs.ActivityConfig;

public class LoginActivity extends RedefActivity {

    @Override
    public void onConfig(ActivityConfig config) {
        config.setTabsEnabled(true);
        config.setActionbarEnabled(true);
        config.setActionbarTitle("Login");
        config.setHomeButton(false);
    }

    @Override
    public void onCreate() {

    }
}
