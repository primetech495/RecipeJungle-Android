package com.prime.redef.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public final class ObjectUtils {

    private static Random random = new Random();

    private ObjectUtils() { }

    public static String toStr(Object o) {
        return o == null ? null : o.toString();
    }

    public static byte[] utf8Bytes(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    public static String utf8String(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static <E> String join(String seperator, ArrayList<E> data) {
        if (data == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            sb.append(data.get(i));
            if (i != data.size() - 1)
                sb.append(seperator);
        }
        return sb.toString();
    }
}
