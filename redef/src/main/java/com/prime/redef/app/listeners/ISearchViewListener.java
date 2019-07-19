package com.prime.redef.app.listeners;

public interface ISearchViewListener {
    void onSearchViewClosed();
    void onSearchViewOpened();
    boolean onSearchViewSubmit(String query);
    boolean onSearchViewChange(String newText);
    void onSearchViewItemClicked(String item);
}
