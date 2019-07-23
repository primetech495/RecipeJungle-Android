package com.prime.redef.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prime.redef.utils.DebugUtils;

import java.lang.ref.WeakReference;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.UUID;

public final class App {

    static final String KEY_INSTANCE_ID = "___Instance_Id";
    static final String KEY_TAB_POSITION = "___Instance_Tab_Pos";

    private static HashMap<String, Class<? extends RedefActivity>> redefActivityTypes;
    private static HashMap<String, Class<? extends RedefFragment>> redefFragmentTypes;
    private static HashMap<String, Object> activityAndFragmentParameters;
    private static HashMap<String, WeakReference<AndroidActivity>> activeActivities;
    private static HashMap<String, WeakReference<AndroidFragment>> activeFragments;
    private static SecureRandom secureRandom;

    private App() {
    }

    public static void initialize() {
        DebugUtils.assertUiThread();
        secureRandom = new SecureRandom();
        redefActivityTypes = new HashMap<>();
        redefFragmentTypes = new HashMap<>();
        activityAndFragmentParameters = new HashMap<>();
        activeActivities = new HashMap<>();
        activeFragments = new HashMap<>();
    }

    private static String newInstanceId() {
        return String.valueOf(UUID.randomUUID()) +
                secureRandom.nextInt() +
                secureRandom.nextInt() +
                secureRandom.nextInt() +
                secureRandom.nextInt();
    }

    @Nullable
    static Object getActivityOrFragmentParametersById(String id) {
        DebugUtils.assertUiThread();

        return activityAndFragmentParameters.get(id);
    }

    public static void startActivity(@NonNull Context activityContext, Class<? extends RedefActivity> type, @Nullable Object args) {
        // HACK
        for (WeakReference<AndroidActivity> y : getActiveActivities().values()) {
            if (y == null)return;
            if (y.get() == null) return;
            if (y.get().getRedefActivity().getClass() == type) {
                y.get().finish();
            }
        }

        DebugUtils.assertUiThread();
        if (args != null && !isPod(args.getClass())) {
            throw new IllegalArgumentException("parameter should be plain data");
        }

        String activityId = newInstanceId();

        redefActivityTypes.put(activityId, type);
        activityAndFragmentParameters.put(activityId, args);

        Class<? extends AndroidActivity> androidActivityType;
        androidActivityType = AndroidActivity.class;


        Intent intent = new Intent(activityContext, androidActivityType);
        intent.putExtra(KEY_INSTANCE_ID, activityId);
        activityContext.startActivity(intent);
    }

    static Class<? extends RedefActivity> getRedefActivityTypeById(String instanceId) {
        DebugUtils.assertUiThread();
        return redefActivityTypes.get(instanceId);
    }

    static Class<? extends RedefFragment> getRedefFragmentTypeById(String instanceId) {
        DebugUtils.assertUiThread();
        return redefFragmentTypes.get(instanceId);
    }

    static AndroidFragment createFragment(Class<? extends RedefFragment> type, int tabPosition, @Nullable Object parameter) {
        DebugUtils.assertUiThread();

        String instanceId = newInstanceId();

        redefFragmentTypes.put(instanceId, type);
        activityAndFragmentParameters.put(instanceId, parameter);

        Bundle args = new Bundle();
        args.putString(KEY_INSTANCE_ID, instanceId);
        args.putInt(KEY_TAB_POSITION, tabPosition);

        AndroidFragment androidFragment = new AndroidFragment();
        androidFragment.setArguments(args);

        return androidFragment;
    }

    static void registerActivityForEvents(AndroidActivity androidActivity) {
        DebugUtils.assertUiThread();
        String instanceId = androidActivity.getInstanceId();
        if (instanceId == null)
            return;

        activeActivities.put(instanceId, new WeakReference<>(androidActivity));
    }

    static void registerFragmentForEvents(AndroidFragment androidFragment) {
        DebugUtils.assertUiThread();
        String instanceId = androidFragment.getInstanceId();
        if (instanceId == null)
            return;

        activeFragments.put(instanceId, new WeakReference<>(androidFragment));
    }

    static void unregisterActivityForEvents(AndroidActivity androidActivity) {
        DebugUtils.assertUiThread();
        String instanceId = androidActivity.getInstanceId();
        if (instanceId == null)
            return;

        activeActivities.remove(instanceId);
    }

    static void unregisterFragmentForEvents(AndroidFragment androidFragment) {
        DebugUtils.assertUiThread();
        String instanceId = androidFragment.getInstanceId();
        if (instanceId == null)
            return;

        activeFragments.remove(instanceId);
    }

    static void unregisterActivityOrFragmentInstance(String instanceId) {
        DebugUtils.assertUiThread();

        redefActivityTypes.remove(instanceId);
        redefFragmentTypes.remove(instanceId);
        activityAndFragmentParameters.remove(instanceId);
    }

    private static boolean isPod(Class clazz) {
        // todo
        return true;
    }

    public static HashMap<String, WeakReference<AndroidFragment>> getActiveFragments() {
        return activeFragments;
    }

    public static HashMap<String, WeakReference<AndroidActivity>> getActiveActivities() {
        return activeActivities;
    }
}
