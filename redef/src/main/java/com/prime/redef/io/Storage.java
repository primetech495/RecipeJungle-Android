package com.prime.redef.io;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.common.io.Files;

import java.io.File;
import java.io.FileInputStream;

public class Storage {

    private static String rootPath;

    public static void initialize(@NonNull Context context) {
        File rootDir = context.getFilesDir();
        rootPath = rootDir.getAbsolutePath();
        if (rootPath.endsWith("/"))
            rootPath = rootPath.substring(0, rootPath.length() - 1);
    }

    private static File getFileFromPath(String path) {
        if (!path.startsWith("/"))
            path = "/" + path;
        return new File(rootPath + path);
    }

    @Nullable
    public static byte[] readBytes(String path) {
        File file = getFileFromPath(path);
        if (!file.exists())
            return null;

        int size = (int)file.length();
        byte[] bytes = new byte[size];
        byte[] tmpBuff = new byte[size];
        try (FileInputStream fis = new FileInputStream(file)) {
            int read = fis.read(bytes, 0, size);
            if (read < size) {
                int remain = size - read;
                while (remain > 0) {
                    read = fis.read(tmpBuff, 0, remain);
                    System.arraycopy(tmpBuff, 0, bytes, size - remain, read);
                    remain -= read;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return bytes;
    }

    public static boolean deleteFile(String path) {
        File file = getFileFromPath(path);
        return file.exists() && file.delete();
    }

    public static boolean writeBytes(String path, @NonNull byte[] data) {
        try {
            File file = getFileFromPath(path);
            Files.createParentDirs(file);
            Files.touch(file);
            Files.write(data, file);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean exists(String path) {
        return getFileFromPath(path).exists();
    }

    public static long lastModified(String path) {
        return getFileFromPath(path).lastModified();
    }
}
