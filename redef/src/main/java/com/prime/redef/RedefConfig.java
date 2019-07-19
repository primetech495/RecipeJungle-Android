package com.prime.redef;

public class RedefConfig  {

    public static boolean DARK = false;

    public static int getMenuTintColor() {
        return DARK ? 0xFFFFFFFF : 0xFF424242;
    }

    public static int getActionbarTitleColor() {
        return DARK ? 0xFFFFFFFF : 0xFF424242;
    }

    public static int getActionbarSubtitleColor() {
        return DARK ? 0xFFFFFFFF : 0xFF424242;
    }

    public static int getTabSelectedTextColor() {
        return DARK ? 0xFFFFFFFF : 0xFF424242;
    }

    public static int getTabNormalTextColor() {
        return DARK ? 0xFFeeeeee : 0xFF616161;
    }

    public static int getPrimaryTextColor() {
        return DARK ? 0xFFFFFFFF : 0xDE000000;
    }

    public static int getSecondaryTextColor() {
        return DARK ? 0xB2FFFFFF : 0x8A000000;
    }

    public static int getDisabledTextColor() {
        return DARK ? 0x80FFFFFF : 0x61000000;
    }

    public static int getHintTextColor() {
        return DARK ? 0x80ffffff : 0x80000000;
    }
}
