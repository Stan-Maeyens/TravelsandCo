package com.maeyens.stan.travelsandco.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Stan on 19-Jun-16.
 */
public class SaveSharedPreference {
    private static final String PREF_USER_NAME = "username";
    private static final String PREF_CURR_TRAVEL = "current_travel";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_USER_NAME);
        editor.commit();
    }

    public static void setCurrentTravel(Context ctx, String travel){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_CURR_TRAVEL, travel);
        editor.commit();
    }

    public static String getCurrentTravel(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_CURR_TRAVEL, "");
    }

    public static void clearCurrentTravel(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_CURR_TRAVEL);
        editor.commit();
    }
}
