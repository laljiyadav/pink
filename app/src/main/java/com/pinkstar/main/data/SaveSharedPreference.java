package com.pinkstar.main.data;

/**
 * Created by Win 7 on 10/1/2015.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_USER_NAME = "username";
    static final String PREF_LAST_NAME = "lastname";
    static final String PREF_USER_MOBILE = "mobile";
    static final String PREF_USER_EMAIL = "email";
    static final String PREF_USER_IMAGE = "image";
    static final String PREF_USER_AUTH = "place";
    static final String PREF_USER_ID = "id";
    static final String bal_star = "bal_star";
    static final String PREF_SSS = "ssn";
    static final String PREF_Facebook = "fb";
    static final String PREF_Twitter = "tw";
    static final String PREF_Check = "ch";
    static final String Birth = "birth";
    static final String Annversary = "Annversary";
    static final String Gender = "gender";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void setLastName(Context ctx, String userName) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LAST_NAME, userName);
        editor.commit();
    }

    public static String getLastName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_LAST_NAME, "");
    }

    public static void setMobile(Context ctx, String userName) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_MOBILE, userName);
        editor.commit();
    }

    public static String getMobile(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_MOBILE, "");
    }

    public static void setUserID(Context ctx, String userid) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_ID, userid);
        editor.commit();
    }

    public static String getUserID(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_ID, "");
    }


    public static void setUserEMAIL(Context ctx, String useremail) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_EMAIL, useremail);
        editor.commit();
    }

    public static String getUserEMAIL(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
    }

    public static void setUserIMAGE(Context ctx, String userimage) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_IMAGE, userimage);
        editor.commit();
    }

    public static String getUserIMAGE(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_IMAGE, "");
    }


    public static void setBalStar(Context ctx, String mobile) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(bal_star, mobile);
        editor.commit();
    }

    public static String getBalStar(Context ctx) {
        return getSharedPreferences(ctx).getString(bal_star, "");
    }

    public static void setUSERAuth(Context ctx, String place) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_AUTH, place);
        editor.commit();
    }

    public static String getUSERAuth(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_AUTH, "");
    }

    public static void setUSERSSN(Context ctx, String place) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_SSS, place);
        editor.commit();
    }

    public static String getUSERSSN(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_SSS, "");
    }
    public static void setTotal(Context ctx, String place) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_Facebook, place);
        editor.commit();
    }

    public static String getTotal(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_Facebook, "");
    }

    public static void setAnnversary(Context ctx, String place) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_Twitter, place);
        editor.commit();
    }

    public static String getAnnversary(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_Twitter, "");
    }

    public static void setGender(Context ctx, String place) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_Check, place);
        editor.commit();
    }

    public static String getGender(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_Check, "");
    }

    public static void setBirth(Context ctx, String place) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Birth, place);
        editor.commit();
    }

    public static String getBirth(Context ctx) {
        return getSharedPreferences(ctx).getString(Birth, "");
    }

}