package com.effizent.esplapp.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.effizent.esplapp.Activity.LoginActivity;

import java.util.HashMap;

public class SessionManager
{
    // Shared Preferences
    private SharedPreferences pref;
    // Editor for Shared preferences
    private Editor editor;
    // Shared pref file name
    private static final String PREF_NAME = "salesformPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_WORKING_HOURS_ADDED = "is_working_hours_added";
    private static final String KEY_ID = "ID";
    private static final String KEY_DEPARTMENT = "department";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_MOBILE = "MOBILE";
    private static final String KEY_TEAMLEADER = "TEAMLEADER";
    private static final String KEY_PROFILEPICTURE = "PROFILEPIC";



    public SessionManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setLoginDetails(String id,String department,String name,String email, String mobile
            ,String teamLeader,String profilePicture) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_ID, id);
        editor.putString(KEY_DEPARTMENT,department );
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_TEAMLEADER, teamLeader);
        editor.putString(KEY_PROFILEPICTURE,profilePicture );

        editor.commit();
    }

    public HashMap<String, String> getLoginDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_ID, pref.getString(KEY_ID, ""));
        user.put(KEY_DEPARTMENT, pref.getString(KEY_DEPARTMENT, ""));
        user.put(KEY_NAME, pref.getString(KEY_NAME, ""));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, ""));
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, ""));
        user.put(KEY_TEAMLEADER, pref.getString(KEY_TEAMLEADER, ""));
        user.put(KEY_PROFILEPICTURE, pref.getString(KEY_PROFILEPICTURE, ""));

        return user;
    }

}