package com.prime.redef.app.configs;

import java.util.ArrayList;

public class OptionsMenuConfig {

    private ArrayList<OptionsMenuItemConfig> items;

    public OptionsMenuConfig() {
        this.items = new ArrayList<>();
    }

    public OptionsMenuConfig addItem(OptionsMenuItemConfig item) {
        items.add(item);
        return this;
    }

    public OptionsMenuConfig addItems(OptionsMenuItemConfig... items) {
        for (OptionsMenuItemConfig item : items)
            addItem(item);
        return this;
    }

    public int size() {
        return items.size();
    }

    public OptionsMenuItemConfig get(int index) {
        return items.get(index);
    }
}
