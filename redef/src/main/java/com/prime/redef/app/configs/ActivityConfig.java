package com.prime.redef.app.configs;

public class ActivityConfig {
    private boolean actionbarEnabled;
    private String actionbarTitle;
    private String actionbarSubtitle;
    private boolean tabsEnabled;
    private boolean homeButton;

    public boolean isActionbarEnabled() {
        return actionbarEnabled;
    }

    public ActivityConfig setActionbarEnabled(boolean actionbarEnabled) {
        this.actionbarEnabled = actionbarEnabled;
        return this;
    }

    public String getActionBarTitle() {
        return actionbarTitle;
    }

    public ActivityConfig setActionbarTitle(String actionbarTitle) {
        this.actionbarTitle = actionbarTitle;
        return this;
    }

    public String getActionBarSubtitle() {
        return actionbarSubtitle;
    }

    public ActivityConfig setActionbarSubtitle(String actionbarSubtitle) {
        this.actionbarSubtitle = actionbarSubtitle;
        return this;
    }

    public boolean isTabsEnabled() {
        return tabsEnabled;
    }

    public ActivityConfig setTabsEnabled(boolean tabsEnabled) {
        this.tabsEnabled = tabsEnabled;
        return this;
    }

    public boolean getHomeButton() {
        return homeButton;
    }

    public ActivityConfig setHomeButton(boolean homeButton) {
        this.homeButton = homeButton;
        return this;
    }

}
