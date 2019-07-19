package com.prime.redef.utils;

import java.nio.charset.StandardCharsets;
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
}
