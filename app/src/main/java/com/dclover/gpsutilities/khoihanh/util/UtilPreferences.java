package com.dclover.gpsutilities.khoihanh.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class UtilPreferences extends PreferenceConstants {
    private static Editor mEDITORINSTANCE;
    private static SharedPreferences mINSTANCE;

    private static SharedPreferences getInstance(Context ctx) {
        if (mINSTANCE == null) {
            mINSTANCE = ctx.getSharedPreferences(PreferenceConstants.SHARED_PREFERENCES_NAME, 0);
        }
        return mINSTANCE;
    }

    private static Editor getEditorInstance(Context ctx) {
        if (mEDITORINSTANCE == null) {
            mEDITORINSTANCE = getInstance(ctx).edit();
        }
        return mEDITORINSTANCE;
    }

    public static boolean getFirstLaunch(Context ctx) {
        return getInstance(ctx).getBoolean(PreferenceConstants.PREF_FIRSTLAUNCH, true);
    }

    public static void saveFirstLaunch(Context ctx, boolean aFirstLaunch) {
        getEditorInstance(ctx).putBoolean(PreferenceConstants.PREF_FIRSTLAUNCH, aFirstLaunch).commit();
    }

    public static String getSelectedLanguage(Context ctx) {
        return getInstance(ctx).getString(PreferenceConstants.PREF_SELECTED_LANGUAGE, PREF_DEFAULT_NULL);
    }

    public static void saveSelectedLanguage(Context ctx, String aLanguage) {
        getEditorInstance(ctx).putString(PreferenceConstants.PREF_SELECTED_LANGUAGE, aLanguage).commit();
    }

    public static String getDeviceLanguageCode(Context ctx) {
        return getInstance(ctx).getString(PreferenceConstants.PREF_DEVICE_LANGUAGE_CODE, PreferenceConstants.PREF_DEFAULT_DEVICE_LANGUAGE_CODE);
    }

    public static void saveDeviceLanguageCode(Context ctx, String aLanguageCode) {
        getEditorInstance(ctx).putString(PreferenceConstants.PREF_DEVICE_LANGUAGE_CODE, aLanguageCode).commit();
    }

    public static String getDeviceLanguageName(Context ctx) {
        return getInstance(ctx).getString(PreferenceConstants.PREF_DEVICE_LANGUAGE_NAME, PreferenceConstants.PREF_DEFAULT_DEVICE_LANGUAGE_NAME);
    }

    public static void saveDeviceLanguageName(Context ctx, String aLanguageName) {
        getEditorInstance(ctx).putString(PreferenceConstants.PREF_DEVICE_LANGUAGE_NAME, aLanguageName).commit();
    }
}
