package com.ptit.store.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ptit.store.models.response.AccessToken;
import com.ptit.store.models.response.AccessTokenPair;


public class UserAuth {

    public static void saveLoginState(Context context, String userID) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PRE_USER_ID, userID);
        editor.apply();
    }
    public static boolean isUserLoggedIn(Context context) {
        return Constants.LOGIN_TRUE.equals(Utils.getSharePreferenceValues(context,Constants.STATUS_LOGIN));
    }
    public static String getBearerToken(Context context) {
        return Constants.BEARER_TOKEN_PREFIX +
                Utils.getSharePreferenceValues(context,Constants.PRE_ACCESS_TOKEN_LOGIN);
    }

    public static String getUserID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.PRE_USER_ID, null);
    }



    public static void saveAccessToken(Context context, AccessToken accessToken) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(accessToken == null) {
            editor.putString(Constants.PRE_USER_ID, null);
            editor.putString(Constants.PRE_ACCESS_TOKEN_LOGIN, null);
            editor.putString(Constants.PRE_REFRESH_TOKEN_LOGIN, null);
            editor.putString(Constants.STATUS_LOGIN, Constants.LOGIN_FAIL);
            editor.putBoolean(Constants.PRE_UPDATE_SHARE_PREF, false);
        } else {
            editor.putString(Constants.PRE_USER_ID, accessToken.getId());
            editor.putString(Constants.PRE_ACCESS_TOKEN_LOGIN, accessToken.getAccessToken());
            editor.putString(Constants.PRE_REFRESH_TOKEN_LOGIN, accessToken.getRefreshToken());
            editor.putString(Constants.STATUS_LOGIN, Constants.LOGIN_TRUE);
            editor.putBoolean(Constants.PRE_UPDATE_SHARE_PREF, true);
        }
        editor.apply();
    }

    public static void saveAccessTokenPair(Context context, AccessTokenPair accessTokenGroup) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(accessTokenGroup == null) {
            editor.putString(Constants.PRE_ACCESS_TOKEN_LOGIN, null);
            editor.putString(Constants.PRE_REFRESH_TOKEN_LOGIN, null);
            editor.putString(Constants.STATUS_LOGIN, Constants.LOGIN_FAIL);
        } else {
            editor.putString(Constants.PRE_ACCESS_TOKEN_LOGIN, accessTokenGroup.getAccessToken());
            editor.putString(Constants.PRE_REFRESH_TOKEN_LOGIN, accessTokenGroup.getRefreshToken());
            editor.putString(Constants.STATUS_LOGIN, Constants.LOGIN_TRUE);
        }
        editor.apply();
    }

}
