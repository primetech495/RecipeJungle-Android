package com.prime.redef.app.controllers;

import android.content.Context;
import androidx.appcompat.widget.SearchView;

import com.prime.redef.app.RedefFragment;
import com.prime.redef.app.listeners.ISearchViewListener;

import java.util.List;

public interface ISearchViewController {
    SearchView getSearchView();
    void setSearchView(SearchView searchView);
    SearchView.SearchAutoComplete getSearchAutoCompleteView();
    void setSearchAutoCompleteView(SearchView.SearchAutoComplete searchAutoCompleteView);

    Context getContext();
    RedefFragment getRedefFragment();
    void setListener(ISearchViewListener listener);
    ISearchViewListener getListener();

    void setQuery(String query);
    void setQuery(String query, boolean submit);
    CharSequence getQuery();
    void setData(String[] data);
    void setData(List<String> data);
    void notifyDataSetChanged();
    void open();
    void close();
    boolean isOpen();
}
