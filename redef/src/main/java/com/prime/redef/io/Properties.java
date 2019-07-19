package com.prime.redef.io;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.prime.redef.utils.AppUtils;

import org.joda.time.DateTime;

public final class Properties {

    private static SharedPreferences sharedPreferences;

    public static void initialize(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences("com.prime.redef.io.Properties", Context.MODE_PRIVATE);
    }

    private static String makeKey(String entity, String property, boolean versioned) {
        if (versioned)
            return entity + '~' + property + '~' + AppUtils.getVersionCode();
        return entity + '~' + property;
    }


    /////////////////////////////////////////////////////////

    private final String entityKey;
    private final boolean versioned;

    public Properties(String entityKey, boolean versioned) {
        this.entityKey = entityKey;
        this.versioned = versioned;
    }

    public void putString(@NonNull String property, String value) {
        String key = makeKey(entityKey, property, versioned);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(@NonNull String property, String defaultValue) {
        String key = makeKey(entityKey, property, versioned);
        return sharedPreferences.getString(key, defaultValue);
    }

    public void putDateTime(@NonNull String property, DateTime value) {
        String key = makeKey(entityKey, property, versioned);
        sharedPreferences.edit().putString(key, value == null ? null : value.toString()).apply();
    }

    public DateTime getDateTime(@NonNull String property, DateTime defaultValue) {
        String key = makeKey(entityKey, property, versioned);
        String string = sharedPreferences.getString(key, null);
        return string == null ? defaultValue : DateTime.parse(string);
    }
}
