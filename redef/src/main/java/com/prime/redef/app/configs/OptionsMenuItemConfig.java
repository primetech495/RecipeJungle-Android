package com.prime.redef.app.configs;

import java.util.List;

public class OptionsMenuItemConfig {
    private int itemId;
    private String title;
    private int iconRes;
    private boolean enabled = true;
    private boolean visible = true;
    private boolean showAsAction = false;
    private boolean withText;
    private boolean checkable;
    private boolean checked;
    private boolean clearHeader;
    private boolean isSearch;
    private List<OptionsMenuItemConfig> items;

    public OptionsMenuItemConfig(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public OptionsMenuItemConfig setItemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public OptionsMenuItemConfig setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getIconRes() {
        return iconRes;
    }

    public OptionsMenuItemConfig setIconRes(int iconRes) {
        this.iconRes = iconRes;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public OptionsMenuItemConfig setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public boolean isVisible() {
        return visible;
    }

    public OptionsMenuItemConfig setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public boolean getShowAsAction() {
        return showAsAction;
    }

    public OptionsMenuItemConfig setShowAsAction(boolean showAsAction) {
        this.showAsAction = showAsAction;
        return this;
    }

    public boolean isWithText() {
        return withText;
    }

    public OptionsMenuItemConfig setWithText(boolean withText) {
        this.withText = withText;
        return this;
    }

    public boolean isCheckable() {
        return checkable;
    }

    public OptionsMenuItemConfig setCheckable(boolean checkable) {
        this.checkable = checkable;
        return this;
    }

    public boolean isChecked() {
        return checked;
    }

    public OptionsMenuItemConfig setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    public boolean isClearHeader() {
        return clearHeader;
    }

    public OptionsMenuItemConfig setClearHeader(boolean clearHeader) {
        this.clearHeader = clearHeader;
        return this;
    }

    public boolean isSearch() {
        return isSearch;
    }

    public OptionsMenuItemConfig setSearch(boolean search) {
        isSearch = search;
        return this;
    }

    public List<OptionsMenuItemConfig> getItems() {
        return items;
    }

    public OptionsMenuItemConfig setItems(List<OptionsMenuItemConfig> items) {
        this.items = items;
        return this;
    }
}
