package com.prime.redef.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;

import com.prime.redef.R;
import com.prime.redef.RedefConfig;
import com.prime.redef.app.configs.FragmentConfig;
import com.prime.redef.app.configs.OptionsMenuConfig;
import com.prime.redef.app.configs.OptionsMenuItemConfig;
import com.prime.redef.app.controllers.IMenuController;
import com.prime.redef.app.controllers.ISearchViewController;
import com.prime.redef.app.listeners.ISearchViewListener;

import java.lang.reflect.Field;
import java.util.List;

public class AndroidFragment extends Fragment {

    private String instanceId;
    private int tabPosition;
    private RedefFragment redefFragment;
    private FragmentConfig fragmentConfig;

    private boolean unableToCreate;

    private IMenuController menuController;
    private ISearchViewController searchViewController;

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // or maybe new Bundle() ?

        /* Obtain instance id */
        Bundle bundle = getArguments();
        if (bundle != null) {
            instanceId = bundle.getString(App.KEY_INSTANCE_ID);
            tabPosition = bundle.getInt(App.KEY_TAB_POSITION);
        }

        if (instanceId == null) {
            unableToCreate = true;
            unregister();
            return;
        }

        /* Create redef fragment instance */
        Class<? extends RedefFragment> redefFragmentType = App.getRedefFragmentTypeById(instanceId);
        if (redefFragmentType == null) {
            unableToCreate = true;
            unregister();
            return;
        }
        try {
            redefFragment = redefFragmentType.newInstance();
        } catch (Exception e) {
            unregister();
            throw new RuntimeException(e);
        }

        redefFragment.androidFragment = this;

        /* Process inject parameters */
        for (Field field : redefFragment.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectParameter.class)) {
                field.setAccessible(true);
                try {
                    Object parameterObject = App.getActivityOrFragmentParametersById(instanceId);
                    field.set(redefFragment, parameterObject); // unchecked call here
                } catch (Exception e) {
                    unregister();
                    throw new RuntimeException(e);
                }
            }
        }

        /* Call onBind */
        if (!redefFragment.onBind()) {
            // False dönerse unable to create ima edilmiştir
            redefFragment.onDestroy();
            unableToCreate = true;
            unregister();
        }
    }

    private void unregister() {
        redefFragment = null;
        App.unregisterActivityOrFragmentInstance(instanceId);
        App.unregisterFragmentForEvents(this);
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) return null;

        // Bu metod için context mevcut olduğundan zaten null olamaz
        Context context = getContext();
        Activity activity = getActivity();
        if (context == null || activity == null) return null;

        /* Unable to create controlü */
        if (unableToCreate) {
            // TODO: Belki burada host activity'y haber verilebilir o da kendini finish'ler
            return new View(context);
        }

        /* Register for events */
        App.registerFragmentForEvents(this);

        /* Obtain configuration */
        this.fragmentConfig = new FragmentConfig();
        this.redefFragment.onConfig(this.fragmentConfig);

        /* Set has options menu */
        if (fragmentConfig.getOptionsMenuConfig() != null)
            setHasOptionsMenu(true);

        /* Setup frameController */
        redefFragment.frameController = ((AndroidActivity) activity).getRedefActivity().frameController;

        /* Create custom fragment */
        return redefFragment.onCreate(context, inflater);
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();

        if (unableToCreate)
            return;
        if (redefFragment != null)
            redefFragment.onDestroy();
        unregister();
    }

    @Override
    public final void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (unableToCreate)
            return;

        Context context = getContext();
        if (context == null)
            return;

        OptionsMenuConfig optionsMenuConfig = fragmentConfig.getOptionsMenuConfig();
        searchViewController = new SearchViewControllerImpl(redefFragment, context);
        menuController = new MenuControllerImpl(optionsMenuConfig, menu, searchViewController);

        redefFragment.searchViewController = searchViewController;
        redefFragment.menuController = menuController;
        redefFragment.onMenuCreated();
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        if (unableToCreate)
            return false;
        if (redefFragment != null)
            return redefFragment.onMenuItemClicked(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public final String getInstanceId() {
        return instanceId;
    }

    public final int getCurrentTabPosition() {
        return tabPosition;
    }

    public RedefFragment getRedefFragment() {
        return redefFragment;
    }

    /* Controller classes */

    private static class MenuControllerImpl implements IMenuController {
        private SparseArray<MenuItem> menuItemMap = new SparseArray<>();

        MenuControllerImpl(OptionsMenuConfig optionsMenu, Menu menu, ISearchViewController searchViewController) {
            if (optionsMenu == null)
                return;
            for (int i = 0; i < optionsMenu.size(); i++) {
                applyForMenuRec(menu, optionsMenu.get(i), searchViewController);
            }
        }

        @Override
        public void setTitle(int itemId, String title) {
            MenuItem item = menuItemMap.get(itemId);
            if (item != null) item.setTitle(title);
        }
        @Override
        public CharSequence getTitle(int itemId) {
            MenuItem item = menuItemMap.get(itemId);
            return item == null ? null : item.getTitle();
        }
        @Override
        public void setEnabled(int itemId, boolean enabled) {
            MenuItem item = menuItemMap.get(itemId);
            if (item != null) item.setEnabled(enabled);
        }
        @Override
        public boolean isEnabled(int itemId) {
            MenuItem item = menuItemMap.get(itemId);
            return item != null && item.isEnabled();
        }
        @Override
        public void setVisible(int itemId, boolean visible) {
            MenuItem item = menuItemMap.get(itemId);
            if (item != null) item.setVisible(visible);
        }
        @Override
        public boolean isVisible(int itemId) {
            MenuItem item = menuItemMap.get(itemId);
            return item != null && item.isVisible();
        }
        @Override
        public void setCheckable(int itemId, boolean checkable) {
            MenuItem item = menuItemMap.get(itemId);
            if (item != null) item.setCheckable(checkable);
        }
        @Override
        public boolean isCheckable(int itemId) {
            MenuItem item = menuItemMap.get(itemId);
            return item != null && item.isCheckable();
        }
        @Override
        public void setChecked(int itemId, boolean checked) {
            MenuItem item = menuItemMap.get(itemId);
            if (item != null) item.setChecked(checked);
        }
        @Override
        public boolean isChecked(int itemId) {
            MenuItem item = menuItemMap.get(itemId);
            return item != null && item.isChecked();
        }
        @Override
        public void setShowAsAction(int itemId, boolean showAsAction, boolean withText) {
            MenuItem item = menuItemMap.get(itemId);
            if (item != null) showAsActionHelper(item, showAsAction, withText);
        }

        private static void showAsActionHelper(MenuItem menuItem, boolean showAsAction, boolean withText) {
            if (showAsAction) {
                int f = MenuItem.SHOW_AS_ACTION_ALWAYS;
                if (withText) f |= MenuItem.SHOW_AS_ACTION_WITH_TEXT;
                menuItem.setShowAsAction(f);
            }
            else {
                int f = MenuItem.SHOW_AS_ACTION_IF_ROOM;
                if (withText) f |= MenuItem.SHOW_AS_ACTION_WITH_TEXT;
                menuItem.setShowAsAction(f);
            }
        }

        @SuppressLint("RestrictedApi")
        private void applyForMenuRec(Menu menu, OptionsMenuItemConfig item, final ISearchViewController searchViewController) {
            if (item == null) return;

            if (item.getItems() == null) {
                MenuItem m = menu.add(0, item.getItemId(), Menu.NONE, item.getTitle());
                menuItemMap.put(item.getItemId(), m);
                if (!item.isEnabled()) m.setEnabled(false);
                if (!item.isVisible()) m.setVisible(false);
                if (item.isCheckable()) m.setCheckable(true);
                if (item.isChecked()) m.setChecked(true);
                showAsActionHelper(m, item.getShowAsAction(), item.isWithText());
                if (item.getIconRes() != 0) {
                    m.setIcon(item.getIconRes());
                }

                if (item.isSearch()) {
                    SearchView searchView = new SearchView(searchViewController.getContext(), null, 0);
                    searchViewController.setSearchView(searchView);
                    m.setActionView(searchView);

                    int color = RedefConfig.getMenuTintColor();

                    ImageView closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
                    closeButton.setImageResource(R.drawable.ic_search_close_button);
                    closeButton.setColorFilter(color);

                    ImageView searchButton = searchView.findViewById(androidx.appcompat.R.id.search_button);
                    searchButton.setImageResource(R.drawable.ic_search_button);
                    searchButton.setColorFilter(color);

                    SearchView.SearchAutoComplete searchAutoCompleteView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
                    searchViewController.setSearchAutoCompleteView(searchAutoCompleteView);
                    searchAutoCompleteView.setTextColor(color);
                    searchAutoCompleteView.setHintTextColor(ColorUtils.setAlphaComponent(color, 180));
                    searchAutoCompleteView.setHint(item.getTitle());

                    try {
                        searchAutoCompleteView.setThreshold(1);
                    } catch (Exception ignored) { }

                    searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                        @Override
                        public boolean onClose() {
                            searchViewController.getListener().onSearchViewClosed();
                            return false;
                        }
                    });
                    searchView.setOnSearchClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            searchViewController.getListener().onSearchViewOpened();
                        }
                    });
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return searchViewController.getListener().onSearchViewSubmit(query);
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            return searchViewController.getListener().onSearchViewChange(newText);
                        }
                    });
                    searchAutoCompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            searchViewController.getListener().onSearchViewItemClicked((String) parent.getItemAtPosition(position));
                        }
                    });
                }
            } else {
                SubMenu s = menu.addSubMenu(0, item.getItemId(), Menu.NONE, item.getTitle());
                menuItemMap.put(item.getItemId(), s.getItem());
                if (item.isClearHeader()) s.clearHeader();
                if (!item.isEnabled()) s.getItem().setEnabled(false);
                if (!item.isVisible()) s.getItem().setVisible(false);
                if (item.isCheckable()) s.getItem().setCheckable(true);
                if (item.isChecked()) s.getItem().setChecked(true);
                showAsActionHelper(s.getItem(), item.getShowAsAction(), item.isWithText());
                if (item.getIconRes() != 0) {
                    s.getItem().setIcon(item.getIconRes());
                }
                for (int i = 0; i < item.getItems().size(); i++)
                    applyForMenuRec(s, item.getItems().get(i), searchViewController);
            }
        }
    }

    public static class SearchViewControllerImpl implements ISearchViewController {
        private RedefFragment fragment;
        private Context context;
        private SearchView searchView;
        private SearchView.SearchAutoComplete searchAutoCompleteView;
        private ArrayAdapter<String> searchViewAdapter;
        private ISearchViewListener listener;

        SearchViewControllerImpl(RedefFragment fragment, @NonNull Context context) {
            this.fragment = fragment;
            this.context = context;
            this.listener = new ISearchViewListener() {
                public void onSearchViewClosed() { }
                public void onSearchViewOpened() { }
                public boolean onSearchViewSubmit(String query) { return false; }
                public boolean onSearchViewChange(String newText) { return false;}
                public void onSearchViewItemClicked(String item) {}
            };
        }

        @Override
        public void setListener(ISearchViewListener listener) {
            this.listener = listener;
        }

        @Override
        public ISearchViewListener getListener() {
            return listener;
        }

        @Override
        public SearchView getSearchView() {
            return searchView;
        }

        @Override
        public void setSearchView(SearchView searchView) {
            this.searchView = searchView;
        }

        @Override
        public SearchView.SearchAutoComplete getSearchAutoCompleteView() {
            return searchAutoCompleteView;
        }

        @Override
        public void setSearchAutoCompleteView(SearchView.SearchAutoComplete searchAutoCompleteView) {
            this.searchAutoCompleteView = searchAutoCompleteView;
        }

        @Override
        public Context getContext() {
            return context;
        }

        @Override
        public RedefFragment getRedefFragment() {
            return fragment;
        }

        @Override
        public void setQuery(String query) {
            searchView.setQuery(query, false);
        }

        @Override
        public void setQuery(String query, boolean submit) {
            searchView.setQuery(query, submit);
        }

        @Override
        public CharSequence getQuery() {
            return searchView.getQuery();
        }

        @Override
        public void setData(String[] data) {
            searchViewAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, data);
            searchAutoCompleteView.setAdapter(searchViewAdapter);
        }

        @Override
        public void setData(List<String> data) {
            searchViewAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, data);
            searchAutoCompleteView.setAdapter(searchViewAdapter);
        }

        @Override
        public void notifyDataSetChanged() {
            searchViewAdapter.notifyDataSetChanged();
        }

        @Override
        public void open() {
            searchView.setIconified(false);
        }

        @Override
        public void close() {
            searchView.setQuery(null, false);
            searchView.setIconified(true);
        }

        @Override
        public boolean isOpen() {
            return !searchView.isIconified();
        }
    }
}
