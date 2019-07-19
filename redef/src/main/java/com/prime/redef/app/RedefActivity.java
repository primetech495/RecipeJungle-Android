package com.prime.redef.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prime.redef.app.configs.ActivityConfig;
import com.prime.redef.app.controllers.IFragmentController;
import com.prime.redef.app.controllers.IFrameController;

import java.util.Map;

// Holds strong reference to android activity, be aware of memory leaks, do not use in static contexts
public abstract class RedefActivity {

    private String instanceId;
    public final String getInstanceId() { return instanceId; }
    final void setInstanceId(String instanceId) { this.instanceId = instanceId; }

    protected IFrameController frameController;
    protected IFragmentController fragmentController;
    protected AndroidActivity androidActivity;

    public boolean onBind() {
        return true;
    }

    public abstract void onConfig(ActivityConfig config);

    public abstract void onCreate();

    public boolean onHomeButtonClicked() {
        return false;
    }

    protected final void finish() {
        androidActivity.finish();
    }

    public void onPageSelected(int position) { }

    public boolean onBackPressed() { return false; }
}
