package com.prime.redef.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prime.redef.app.configs.FragmentConfig;
import com.prime.redef.app.controllers.IFrameController;
import com.prime.redef.app.controllers.IMenuController;
import com.prime.redef.app.controllers.ISearchViewController;

import java.util.Map;

public abstract class RedefFragment {

    protected AndroidFragment androidFragment;

    protected IFrameController frameController;
    protected IMenuController menuController;
    protected ISearchViewController searchViewController;

    public boolean onBind() { return true; }

    public abstract View onCreate(@NonNull Context context, @NonNull LayoutInflater inflater);

    public void onConfig(FragmentConfig config) { }

    public boolean onMenuItemClicked(int id) { return false; }

    public void onMenuCreated() { }

    @CallSuper
    public void onDestroy() { }

    @Nullable
    public final Context getContext() {
        return androidFragment.getContext();
    }

    @Nullable
    public final View getView() {
        return androidFragment.getView();
    }

    @Nullable
    public final AndroidActivity getAndroidActivity() {
        return (AndroidActivity) androidFragment.getActivity();
    }
}
