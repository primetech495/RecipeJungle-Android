package com.prime.redef.app;

import android.app.Application;

import androidx.annotation.CallSuper;

import com.prime.redef.flatui.FontManager;
import com.prime.redef.io.Properties;
import com.prime.redef.io.Storage;
import com.prime.redef.utils.AppUtils;
import com.prime.redef.utils.ViewUtils;
import com.prime.redef.utils.WeakUtils;

import net.danlew.android.joda.JodaTimeAndroid;

public abstract class RedefApplication extends Application {

    @Override
    @CallSuper
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);

        WeakUtils.initialize(this);
        ViewUtils.initialize(this);
        Storage.initialize(this);
        Properties.initialize(this);
        App.initialize();

        FontManager.initialize(getAssets());
        AppUtils.initialize(this);
    }
}
