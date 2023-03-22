package com.mj.myrooms.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import com.mj.myrooms.App;

public class PreferenceUtils {
    private static String TAG = "PreferenceUtils";
    private static PreferenceUtils instance;
    private Context mContext;

    private String PREFERENCE_LOCALE = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).packageName;

    // for app
    private String preference_fcmToken = "fcmToken";
    private String preference_authToken = "authToken";
    private String preference_last_call = "lastCall";
    private String preference_username = "username";
    private String preference_password = "password";
    private String preference_isLoggedIn = "isLoggedIn";
    private String preference_isFirstTime = "isFirstTime";

    private String preference_userModel = "userModel";

    // shared preferences
    private SharedPreferences sp_locale;

    private PreferenceUtils() throws PackageManager.NameNotFoundException {
        sp_locale = App.getAppContext().getSharedPreferences(PREFERENCE_LOCALE, Context.MODE_PRIVATE);
    }

    public static PreferenceUtils getInstance() throws PackageManager.NameNotFoundException {
        if (instance == null) {
            instance = new PreferenceUtils();
        }
        return instance;
    }

    /**
     * clear local storage
     */
    public void clearData() {
        sp_locale.edit().clear().commit();
    }

    /**
     * store data into local
     */

    // token
    public String getFcmToken() {
        return sp_locale.getString(preference_fcmToken, "");
    }

    public void setFcmToken(String token) {
        sp_locale.edit().putString(preference_fcmToken, token).commit();
    }

    public String getAuthToken() {
        return sp_locale.getString(preference_authToken, "");
    }

    public void setAuthToken(String token) {
        sp_locale.edit().putString(preference_authToken, token).commit();
    }

    // store - is user logged in or not
    public boolean isLoggedIn() {
        return sp_locale.getBoolean(preference_isLoggedIn, false);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        sp_locale.edit().putBoolean(preference_isLoggedIn, isLoggedIn).commit();
    }
    // store - is user first time or not
    public boolean getFirstTime() {
        return sp_locale.getBoolean(preference_isFirstTime, false);
    }

    public void setFirstTime(boolean isFirstTime) {
        sp_locale.edit().putBoolean(preference_isFirstTime, isFirstTime).commit();
    }

    // store - user credential
    public String getUsername() {
        return sp_locale.getString(preference_username, "");
    }

    public String getPassword() {
        return sp_locale.getString(preference_password, "");
    }

    public void setUserCredential(String username, String password) {
        sp_locale.edit().putString(preference_username, username)
                .putString(preference_password, password).commit();
    }

    public void setCompanyLegalName(String preference_company_legal_name, String value) {
        sp_locale.edit().putString(preference_company_legal_name, value);
        sp_locale.edit().commit();
    }
    public String getCompanyLegalName(String preference_company_legal_name, String defaultValue) {
        return sp_locale.getString(preference_company_legal_name, defaultValue);
    }

    public void setCompanyName(String key, String value) {
        sp_locale.edit().putString(key, value);
        sp_locale.edit().commit();
    }
    public String getCompanyName(String key, String defaultValue) {
        return sp_locale.getString(key, defaultValue);
    }

    public void setValue(String key, String value) {
        sp_locale.edit().putString(key, value);
        sp_locale.edit().commit();
    }
    public String getValue(String key, String defaultValue) {
        return sp_locale.getString(key, defaultValue);
    }


    // store - logged in user's data
/*    public UserModel getUser() {
        return (UserModel) Utility.jsonToPojo(sp_locale.getString(preference_userModel, null), UserModel.class);
    }

    public void setUser(UserModel object) {
        sp_locale.edit().putString(preference_userModel, Utility.pojoToJson(object)).commit();
    }*/

    public String getLastTimeAPICalled() {
        return sp_locale.getString(preference_last_call, "");
    }

    public void setLastTimeAPICalled(String token) {
        sp_locale.edit().putString(preference_last_call, token).commit();
    }
}