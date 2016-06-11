package com.dclover.gpsutilities.taxi.Utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.dclover.gpsutilities.taxi.model.User;

import java.lang.reflect.Array;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Kinghero on 6/7/2016.
 */
public class Env {
    public static final String COMP_PLACE_DESC = "COMP_PLACE_DESC";
    public static final String COMP_PLACE_ID = "COMP_PLACE_ID";
    public static final String EMPTY_TRIP_ID = "";
    public static final String HOME_PLACE_DESC = "HOME_PLACE_DESC";
    public static final String HOME_PLACE_ID = "HOME_PLACE_ID";
    private static final String LOCALE = "LOCALE";
    private static final String LOCALE_EN = "en";
    private static final String LOCALE_VI = "vi";
    public static final int NUM_RECENT_PLACE = 5;
    public static final String RECENT_PLACE_DESC = "RECENT_PLACE_DESC";
    public static final String RECENT_PLACE_ID = "RECENT_PLACE_ID";
    public static final int TRIP_STATUS_CANCELED = 0;
    public static final int TRIP_STATUS_DONE = 4;
    public static final int TRIP_STATUS_DRIVER_COME = 3;
    public static final int TRIP_STATUS_DRIVER_REGISTERED = 2;
    public static final int TRIP_STATUS_NEW = 1;
    public static final int TRIP_STATUS_NONE = -1;
    public static final int TRIP_STATUS_RATED = 6;
    public static String currentTripId;
    public static int currentTripStatus;
    private static Application objApp;
    private static User objUser;

    public static void setUser(User user) {
        objUser = user;
    }

    public static User getUser() {
        if (objUser == null) {
            objUser = readUser(getApplication());
        }
        return objUser;
    }

    public static User readUser(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, TRIP_STATUS_CANCELED);
        if (sharedPref == null) {
            return null;
        }
        String userName = sharedPref.getString(Constants.USER_NAME, null);
        String userPhoneNo = sharedPref.getString(Constants.USER_PHONE_NO, null);
        String userEmail = sharedPref.getString(Constants.USER_EMAIL, null);
        String userPassword = sharedPref.getString(Constants.USER_PASSWORD, null);
        String userDeviceUUID = sharedPref.getString(Constants.USER_DEVICE_UUID, null);
        if (userPhoneNo == null) {
            return null;
        }
        User user = new User();
        user.setName(userName);
        user.setPhoneNo(userPhoneNo);
        user.setEmail(userEmail);
        user.setPassword(userPassword);
        user.setDeviceUUID(userDeviceUUID);
        return user;
    }

    public static void writeUser(Context context, User user) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, TRIP_STATUS_CANCELED);
        if (sharedPref != null) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Constants.USER_NAME, user.getName());
            editor.putString(Constants.USER_PHONE_NO, user.getPhoneNo());
            editor.putString(Constants.USER_EMAIL, user.getEmail());
            editor.putString(Constants.USER_PASSWORD, user.getPassword());
            editor.putString(Constants.USER_REG_ID, user.getRegId());
            if (user.getDeviceUUID() == null) {
                user.setDeviceUUID(UUID.randomUUID().toString());
            }
            editor.putString(Constants.USER_DEVICE_UUID, user.getDeviceUUID());
            editor.commit();
            setUser(user);
        }
    }

    public static void removeUser(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, TRIP_STATUS_CANCELED);
        if (sharedPref != null) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(Constants.USER_NAME);
            editor.remove(Constants.USER_PHONE_NO);
            editor.remove(Constants.USER_EMAIL);
            editor.remove(Constants.USER_PASSWORD);
            editor.remove(Constants.USER_REG_ID);
            editor.commit();
            setUser(null);
        }
    }

    public static void setApplication(Application app) {
        objApp = app;
        setLocale(isDefaultLocale());
    }

    public static Application getApplication() {
        return objApp;
    }

    public static void setLocale(boolean isDefault) {
        if (objApp != null) {
            Resources res = objApp.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            String strLocale = LOCALE_VI;
            if (!isDefault) {
                strLocale = LOCALE_EN;
            }
            conf.locale = new Locale(strLocale);
            res.updateConfiguration(conf, dm);
            SharedPreferences sharedPref = objApp.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, TRIP_STATUS_CANCELED);
            if (sharedPref != null) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(LOCALE, strLocale);
                editor.commit();
            }
        }
    }

    public static boolean isDefaultLocale() {
        if (objApp == null) {
            return true;
        }
        if (LOCALE_EN.equals(objApp.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, TRIP_STATUS_CANCELED).getString(LOCALE, LOCALE_VI))) {
            return false;
        }
        return true;
    }

    static {
        currentTripId = EMPTY_TRIP_ID;
        currentTripStatus = TRIP_STATUS_NONE;
    }

    public static void writeTripState(String tripId, int tripStatus) {
        currentTripId = tripId;
        currentTripStatus = tripStatus;
        SharedPreferences sharedPref = objApp.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, TRIP_STATUS_CANCELED);
        if (sharedPref != null) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("currentTripId", tripId);
            editor.putInt("status_trip_id_" + tripId, tripStatus);
            editor.commit();
        }
    }

    public static void readTripState(String tripId, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, TRIP_STATUS_CANCELED);
        if (sharedPref == null || EMPTY_TRIP_ID.equals(tripId)) {
            currentTripStatus = TRIP_STATUS_NONE;
        } else {
            currentTripStatus = sharedPref.getInt("status_trip_id_" + tripId, TRIP_STATUS_NONE);
        }
    }

    public static String getCurrentTripIdFromPersistence(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, TRIP_STATUS_CANCELED);
        if (sharedPref == null) {
            return EMPTY_TRIP_ID;
        }
        currentTripId = sharedPref.getString("currentTripId", EMPTY_TRIP_ID);
        return currentTripId;
    }

    public static void writeHomeCompany(Context context, String type, String placeId, String placeDesc) {
        SharedPreferences sharedPref = objApp.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, TRIP_STATUS_CANCELED);
        if (sharedPref != null && placeId != null && placeId.trim().length() > 0) {
            SharedPreferences.Editor editor = sharedPref.edit();
            if (HOME_PLACE_ID.equals(type)) {
                editor.putString(HOME_PLACE_ID, placeId);
                editor.putString(HOME_PLACE_DESC, placeDesc);
            } else if (COMP_PLACE_ID.equals(type)) {
                editor.putString(COMP_PLACE_ID, placeId);
                editor.putString(COMP_PLACE_DESC, placeDesc);
            }
            editor.commit();
        }
    }

    public static String[] readHomeCompany(Context context, String type) {
        String[] place = new String[TRIP_STATUS_DRIVER_REGISTERED];
        place[TRIP_STATUS_CANCELED] = EMPTY_TRIP_ID;
        place[TRIP_STATUS_NEW] = EMPTY_TRIP_ID;
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, TRIP_STATUS_CANCELED);
        if (sharedPref != null) {
            if (HOME_PLACE_ID.equals(type)) {
                place[TRIP_STATUS_CANCELED] = sharedPref.getString(HOME_PLACE_ID, EMPTY_TRIP_ID);
                place[TRIP_STATUS_NEW] = sharedPref.getString(HOME_PLACE_DESC, EMPTY_TRIP_ID);
            } else if (COMP_PLACE_ID.equals(type)) {
                place[TRIP_STATUS_CANCELED] = sharedPref.getString(COMP_PLACE_ID, EMPTY_TRIP_ID);
                place[TRIP_STATUS_NEW] = sharedPref.getString(COMP_PLACE_DESC, EMPTY_TRIP_ID);
            }
        }
        return place;
    }

    public static void writeRecentPlaces(Context context, String placeId, String placeDesc) {
        SharedPreferences sharedPref = objApp.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, TRIP_STATUS_CANCELED);
        if (sharedPref != null && placeId != null && placeId.trim().length() > 0) {
            int i;
            String[][] prePlaces = readRecentPlaces(context);
            int idx = TRIP_STATUS_NONE;
            for (i = TRIP_STATUS_CANCELED; i < NUM_RECENT_PLACE; i += TRIP_STATUS_NEW) {
                if (placeId.equals(prePlaces[i][TRIP_STATUS_CANCELED])) {
                    idx = i;
                    break;
                }
            }
            SharedPreferences.Editor editor = sharedPref.edit();
            if (idx >= 0) {
                editor.putString("RECENT_PLACE_ID0", placeId);
                editor.putString("RECENT_PLACE_DESC0", placeDesc);
                for (i = TRIP_STATUS_NEW; i <= idx; i += TRIP_STATUS_NEW) {
                    editor.putString(RECENT_PLACE_ID + i, prePlaces[i + TRIP_STATUS_NONE][TRIP_STATUS_CANCELED]);
                    editor.putString(RECENT_PLACE_DESC + i, prePlaces[i + TRIP_STATUS_NONE][TRIP_STATUS_NEW]);
                }
            } else {
                editor.putString("RECENT_PLACE_ID0", placeId);
                editor.putString("RECENT_PLACE_DESC0", placeDesc);
                for (i = TRIP_STATUS_NEW; i < NUM_RECENT_PLACE; i += TRIP_STATUS_NEW) {
                    editor.putString(RECENT_PLACE_ID + i, prePlaces[i + TRIP_STATUS_NONE][TRIP_STATUS_CANCELED]);
                    editor.putString(RECENT_PLACE_DESC + i, prePlaces[i + TRIP_STATUS_NONE][TRIP_STATUS_NEW]);
                }
            }
            editor.commit();
        }
    }

    public static String[][] readRecentPlaces(Context context) {
        String[][] places = (String[][]) Array.newInstance(String.class, new int[]{NUM_RECENT_PLACE, TRIP_STATUS_DRIVER_REGISTERED});
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, TRIP_STATUS_CANCELED);
        if (sharedPref != null) {
            for (int i = TRIP_STATUS_CANCELED; i < NUM_RECENT_PLACE; i += TRIP_STATUS_NEW) {
                places[i][TRIP_STATUS_CANCELED] = sharedPref.getString(RECENT_PLACE_ID + i, EMPTY_TRIP_ID);
                places[i][TRIP_STATUS_NEW] = sharedPref.getString(RECENT_PLACE_DESC + i, EMPTY_TRIP_ID);
            }
        }
        return places;
    }

}
