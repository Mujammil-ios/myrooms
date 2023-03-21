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

    private String preference_userModel = "userModel";



    private String preference_company_legal_name = "companyLegalName";
    private String preference_company_name = "companyName";
    private String preference_sac_code = "sacCode";
    private String preference_company_compostition = "companyComposition";
    private String preference_msme_number = "msmeNumber";
    private String preference_address_1 = "address1";
    private String preference_address_2 = "address2";
    private String preference_select_country = "country";
    private String preference_select_city = "city";
    private String preference_select_state = "state";
    private String preference_pincode = "pincode";
    private String preference_billing_address_1 = "billingAddress1";
    private String preference_billing_address_2 = "billingAddress2";
    private String preference_select_billing_country = "billingCountry";
    private String preference_select_billing_city = "city";
    private String preference_select_billing_state = "state";
    private String preference_billing_pincode = "pincode";
    private String preference_gst_status = "gstStatus";
    private String preference_gst_number = "gstNumber";
    private String preference_tan_number = "tanNumber";
    private String preference_pan_number = "panNumber";
    private String preference_invoice_prefix = "invoicePrefix";
//    private String preference_operational_desk_number = "invoicePrefix";

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