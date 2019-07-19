package com.prime.redef.app.controllers;

public interface IMenuController {
    void setTitle(int itemId, String title);
    CharSequence getTitle(int itemId);
    void setEnabled(int itemId, boolean enabled);
    boolean isEnabled(int itemId);
    void setVisible(int itemId, boolean visible);
    boolean isVisible(int itemId);
    void setCheckable(int itemId, boolean checkable);
    boolean isCheckable(int itemId);
    void setChecked(int itemId, boolean checked);
    boolean isChecked(int itemId);
    void setShowAsAction(int itemId, boolean showAsAction, boolean withText);
}
