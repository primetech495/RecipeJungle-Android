package com.prime.redef.json;

import com.google.gson.Gson;

public final class Json {

    private Json() { }

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> klass) {
        return new Gson().fromJson(json, klass);
    }
}
