package com.prime.redef.app;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.prime.redef.R;
import com.prime.redef.RedefConfig;
import com.prime.redef.app.configs.ActivityConfig;
import com.prime.redef.app.controllers.IFragmentController;
import com.prime.redef.app.controllers.IFrameController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AndroidActivity extends AppCompatActivity {
    private String instanceId;
    private RedefActivity redefActivity;
    private FragmentManager fragmentManager;
    private ActivityConfig activityConfig;

    private Toolbar toolbar;

    private IFrameController frameController;
    private IFragmentController fragmentController;

    private ViewPager vp;
    private TabLayout tabLayout;
    private FragmentStatePagerAdapter adapter;

    private boolean unableToCreate;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {

        /* Create redef activity instance */
        instanceId = getIntent().getStringExtra(App.KEY_INSTANCE_ID);
        if (instanceId == null) {
            // InstanceId null ise sistem memory pressure üzerine process'i
            // öldürmüş olabilir ve/veya intent extralarını yok etmiş olabilir
            // bu durumda basitçe açılmayı reddedicez
            unableToCreate = true;
            finish();
            unregister();
            super.onCreate(new Bundle());
            return;
        }

        Class<? extends RedefActivity> redefActivityType = App.getRedefActivityTypeById(instanceId);
        if (redefActivityType == null) {
            // Üsttekine benzer durum
            unableToCreate = true;
            finish();
            unregister();
            super.onCreate(new Bundle());
            return;
        }
        try {
            redefActivity = redefActivityType.newInstance();
        } catch (Exception e) {
            unregister();
            throw new RuntimeException(e);
        }

        redefActivity.setInstanceId(instanceId);
        redefActivity.androidActivity = this;

        /* Process inject parameters */
        for (Field field : redefActivity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectParameter.class)) {
                field.setAccessible(true);
                try {
                    Object parameterObject = App.getActivityOrFragmentParametersById(redefActivity.getInstanceId());
                    field.set(redefActivity, parameterObject); // unchecked call here
                } catch (Exception e) {
                    unregister();
                    throw new RuntimeException(e);
                }
            }
        }

        /* Apply window settings */
        if (transparentStatusBar()) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
        if (!RedefConfig.DARK) {
            if (android.os.Build.VERSION.SDK_INT >= 23)
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        /* Call super */
        super.onCreate(new Bundle());

        /* Init some managers */
        fragmentManager = getSupportFragmentManager();

        /* Call onBind */
        if (!redefActivity.onBind()) {
            unableToCreate = true;
            finish();
            unregister();
            return;
        }

        /* Config custom activity */
        activityConfig = new ActivityConfig();
        redefActivity.onConfig(activityConfig);

        /* Set content view and load some UI */
        setContentView(getContentViewResource());

        frameController = new FrameController();
        fragmentController = new FragmentController();

        redefActivity.frameController = frameController;
        redefActivity.fragmentController = fragmentController;

        loadActionbar();
        loadFragmentRelated();

        /* Register activity for events */
        App.registerActivityForEvents(this);

        /* Call internal onCreate */
        beforeCustomOnCreate();

        /* Load custom activity */
        redefActivity.onCreate();

        /* Call internal onCreate */
        afterCustomOnCreate();
    }

    @Override
    public final void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(new Bundle());
    }

    @Override
    protected final void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(new Bundle());
    }

    @Override
    protected final void onDestroy() {
        super.onDestroy();

        if (unableToCreate)
            return;
        unregister();
    }

    private void unregister() {
        redefActivity = null;
        App.unregisterActivityOrFragmentInstance(instanceId);
        App.unregisterActivityForEvents(this);
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        if (unableToCreate) return false;

        if (item.getItemId() == android.R.id.home) {
            return redefActivity.onHomeButtonClicked();
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    @CallSuper
    public final void onBackPressed() {
        if (unableToCreate) return;
        if (!redefActivity.onBackPressed()) {
            super.onBackPressed();
        }
    }

    private void loadActionbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String actionBarTitle = activityConfig.getActionBarTitle();
        String actionBarSubtitle = activityConfig.getActionBarSubtitle();

        if (actionBarTitle != null)
            frameController.setTitle(actionBarTitle);
        if (actionBarSubtitle != null)
            frameController.setSubtitle(actionBarSubtitle);

        Drawable overflow = toolbar.getOverflowIcon();
        if (overflow != null) {
            DrawableCompat.setTint(overflow, RedefConfig.getMenuTintColor());
            toolbar.setOverflowIcon(overflow);
        }

        toolbar.setTitleTextColor(RedefConfig.getActionbarTitleColor());
        toolbar.setSubtitleTextColor(RedefConfig.getActionbarSubtitleColor());

        if (activityConfig.isActionbarEnabled())
            toolbar.setVisibility(View.VISIBLE);
        frameController.setHomeButton(activityConfig.getHomeButton());
    }

    private void loadFragmentRelated() {
        vp = findViewById(R.id.vp_pages);

        tabLayout = findViewById(R.id.tabs);
        if (activityConfig.isTabsEnabled()) {
            tabLayout.setupWithViewPager(vp);
            tabLayout.setVisibility(View.GONE);
            tabLayout.setTabTextColors(RedefConfig.getTabNormalTextColor(), RedefConfig.getTabSelectedTextColor());
        } else {
            ((ViewGroup) tabLayout.getParent()).removeView(tabLayout);
            tabLayout = null;
        }

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //if (lastNavigationSelection > 0)
                //    lastTabIndexes.put(lastNavigationSelection, position);
                //ContentInfo info = currentNavigationContentInfo[position];
                //if (info.isOverrideToolbarTitle()) {
                //    frameController.setTitle(info.getToolbarTitle());
                //    frameController.setSubtitle(info.getToolbarSubtitle());
                //}
                ////else {
                ////    frameController.resetTitle();
                ////}

                // Gecikmeyi önlemek için
                supportInvalidateOptionsMenu();

                redefActivity.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Gecikmeyi önlemek için
                supportInvalidateOptionsMenu();
            }
        });
    }

    /* Some getters */

    public final String getInstanceId() {
        return instanceId;
    }

    public final RedefActivity getRedefActivity() {
        return redefActivity;
    }

    /* Internal methods to override */

    void beforeCustomOnCreate() {

    }

    void afterCustomOnCreate() {

    }

    boolean transparentStatusBar() {
        return false;
    }

    @LayoutRes
    int getContentViewResource() {
        return R.layout.activity_redef_base;
    }

    // Controller classes

    private static class FragmentCreateInfo {
        Class<? extends RedefFragment> type;
        String title;
        Object parameter;
    }

    private static class FragmentAdapter extends FragmentStatePagerAdapter {

        private FragmentCreateInfo[] infos;

        FragmentAdapter(FragmentManager fm, FragmentCreateInfo[] infos) {
            super(fm);
            this.infos = infos;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
            // do nothing
        }

        @Override
        public Fragment getItem(int position) {
            FragmentCreateInfo info = infos[position];
            return App.createFragment(info.type, position, info.parameter);
        }

        @Override
        public int getCount() {
            return infos.length;
        }

        @Nullable
        @Override
        public final CharSequence getPageTitle(int position) {
            return infos[position].title;
        }
    }

    private class FrameController implements IFrameController {
        boolean titleDirty;
        boolean upButton;

        @Override
        public CharSequence getTitle() {
            ActionBar ab = getSupportActionBar();
            return ab == null ? null : ab.getTitle();
        }

        @Override
        public void setTitle(String title) {
            titleDirty = true;
            ActionBar ab = getSupportActionBar();
            if (ab != null) ab.setTitle(title);
        }

        @Override
        public CharSequence getSubtitle() {
            ActionBar ab = getSupportActionBar();
            return ab == null ? null : ab.getSubtitle();
        }

        @Override
        public void setSubtitle(String subtitle) {
            titleDirty = true;
            ActionBar ab = getSupportActionBar();
            if (ab != null) ab.setSubtitle(subtitle);
        }

        @Override
        public void resetTitle() {
            if (!titleDirty) return;
            String actionBarTitle = activityConfig.getActionBarTitle();
            String actionBarSubtitle = activityConfig.getActionBarSubtitle();
            setTitle(actionBarTitle);
            setSubtitle(actionBarSubtitle);
            titleDirty = false;
        }

        @Override
        public void setHomeButton(boolean upButton) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar == null) return;

            this.upButton = upButton;
            if (!upButton) {
                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
                return;
            }

            Drawable icon = getResources().getDrawable(R.drawable.ic_pure_menu_back);
            DrawableCompat.setTint(icon, RedefConfig.getMenuTintColor());

            actionBar.setHomeAsUpIndicator(icon);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private class FragmentController implements IFragmentController {

        @Override
        public ITransaction startTransaction() {
            return new Transaction();
        }

        @Override
        public void commitTransaction(ITransactionBuilder[] transactionBuilders) {
            FragmentCreateInfo[] infos = new FragmentCreateInfo[transactionBuilders.length];
            for (int i = 0; i < infos.length; i++) {
                Transaction.TransactionBuilder tb = (Transaction.TransactionBuilder) transactionBuilders[i];

                infos[i] = new FragmentCreateInfo();
                infos[i].type = tb.type;
                infos[i].parameter = tb.parameter;
                infos[i].title = tb.title;
            }

            frameController.resetTitle();

            adapter = new FragmentAdapter(fragmentManager, infos);

            vp.removeAllViews();

            List<Fragment> fragments = fragmentManager.getFragments();
            FragmentTransaction t = fragmentManager.beginTransaction();
            for (Fragment f : fragments)
                t.remove(f);
            //t.commit();
            t.commitAllowingStateLoss();

            vp.setAdapter(adapter);

            if (tabLayout != null)
                tabLayout.setVisibility(adapter.getCount() > 1 ? View.VISIBLE : View.GONE);
        }

        @Override
        public int getCurrentTab() {
            PagerAdapter adapter = vp.getAdapter();
            if (adapter != null)
                return vp.getCurrentItem();
            return -1;
        }

        @Override
        public void setCurrentTab(int position) {
            PagerAdapter adapter = vp.getAdapter();
            if (adapter != null && adapter.getCount() > position)
                vp.setCurrentItem(position);
        }

        private class Transaction implements ITransaction {

            private List<ITransactionBuilder> list;

            Transaction() {
                this.list = new ArrayList<>();
            }

            @Override
            public ITransactionBuilder useFragment(Class<? extends RedefFragment> type) {
                ITransactionBuilder transaction = new TransactionBuilder(type);
                list.add(transaction);
                return transaction;
            }

            @Override
            public void commit() {
                commitTransaction(list.toArray(new ITransactionBuilder[0]));
            }

            private class TransactionBuilder implements ITransactionBuilder {

                Class<? extends RedefFragment> type;
                Object parameter;
                String title;

                TransactionBuilder(Class<? extends RedefFragment> type) {
                    this.type = type;
                }

                @Override
                public ITransactionBuilder withParameter(@Nullable Object parameter) {
                    this.parameter = parameter;
                    return this;
                }

                @Override
                public ITransactionBuilder withTitle(String title) {
                    this.title = title;
                    return this;
                }

                @Override
                public ITransactionBuilder useFragment(Class<? extends RedefFragment> type) {
                    return Transaction.this.useFragment(type);
                }

                @Override
                public void commit() {
                    Transaction.this.commit();
                }
            }
        }
    }
}
