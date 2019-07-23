package com.prime.recipejungle.activities;

import android.content.Context;
import android.util.Log;

import com.prime.recipejungle.fragments.HomeFragment;
import com.prime.recipejungle.fragments.LoginFragment;
import com.prime.recipejungle.utils.Global;
import com.prime.recipejungle.utils.NotificationHelper;
import com.prime.redef.app.RedefActivity;
import com.prime.redef.app.configs.ActivityConfig;
import com.prime.redef.network.ApiClient;
import com.prime.redef.network.ApiRestHandler;
import com.prime.redef.network.GetRequest;
import com.prime.redef.network.Header;
import com.prime.redef.utils.ObjectUtils;
import com.prime.redef.utils.WeakUtils;

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

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ApiClient client = new ApiClient(Global.HOST, false);
                    final GetRequest request = new GetRequest("/api/account/notifs");
                    request.putHeader("Authorization", Global.PROPERTIES.getString("Authentication:",null));
                    client.execute(request, new ApiRestHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) throws Exception {
                            String response = ObjectUtils.utf8String(responseBody);
                            Log.d("DEBUG NOTIFICATION", response);
                            if (response.equals("true")) return;
                            Context context = WeakUtils.getAppContext();
                            if (context == null) return;
                            NotificationHelper.post(context, "Recipe Jungle", response + " liked your recipe");
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.e("DEBUG NOTIFICATION", "failure");
                        }
                    });
                }
            }
        });
        thread.start();
    }

    @Override
    public boolean onHomeButtonClicked() {
        finish();
        return true;
    }
}
