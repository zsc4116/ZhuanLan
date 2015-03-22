package io.bxbxbai.zhuanlan.utils;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import io.bxbxbai.zhuanlan.App;
import io.bxbxbai.zhuanlan.R;

import java.io.File;

/**
 * @author bxbxbai
 */
public final class SharedPreferencesUtils {

    private static final String FILE_NAME = "config";

    public static final String KEY_HAS_SHORT_CUT = "has_short_cut";

    private static SharedPreferences SP;


    public static void setValue(@NonNull Context context, @NonNull String key, @NonNull Object value) {
        String type = value.getClass().getSimpleName();

        if (SP == null) {
            SP = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = SP.edit();

        if (String.class.getSimpleName().equals(type)) {
            editor.putString(key, (String) value);
        } else if (Integer.class.getSimpleName().equals(type)) {
            editor.putInt(key, (Integer) value);
        } else if (Boolean.class.getSimpleName().equals(type)) {
            editor.putBoolean(key, (Boolean) value);
        } else if (Float.class.getSimpleName().equals(type)) {
            editor.putFloat(key, (Float) value);
        } else if (Long.class.getSimpleName().equals(type)) {
            editor.putLong(key, (Long) value);
        }
        editor.apply();
    }


    public static Object getValue(Context context, String key, Object defaultValue) {
        String type = defaultValue.getClass().getSimpleName();

        if (SP == null) {
            SP = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }

        if (String.class.getSimpleName().equals(type)) {
            return SP.getString(key, (String) defaultValue);
        } else if (Integer.class.getSimpleName().equals(type)) {
            return SP.getInt(key, (Integer) defaultValue);
        } else if (Boolean.class.getSimpleName().equals(type)) {
            return SP.getBoolean(key, (Boolean) defaultValue);
        } else if (Float.class.getSimpleName().equals(type)) {
            return SP.getFloat(key, (Float) defaultValue);
        } else if (Long.class.getSimpleName().equals(type)) {
            return SP.getLong(key, (Long) defaultValue);
        }
        return defaultValue;
    }


    public static void setHasShortCut(Context context, Boolean has) {
        setValue(context, FILE_NAME, has);
    }

    public static boolean hasShortCut(Context context) {
        return (Boolean)getValue(context, FILE_NAME, false);
    }

    public static void createShortCut(@NonNull Context context) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
        intent.putExtra("duplicate", false);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
        Intent i = new Intent();
        i.setAction(App.PACKAGE_NAME);
        i.addCategory("android.intent.category.DEFAULT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i);
        context.sendBroadcast(intent);
        SharedPreferencesUtils.setHasShortCut(context, true);
    }
}
