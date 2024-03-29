package com.effizent.esplapp.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.effizent.esplapp.Activity.LoginActivity;
import com.effizent.esplapp.RetroApiResponses.LoadDashBoardResult;
import com.google.gson.Gson;

import java.util.ArrayList;
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
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public static final String IS_WORKING_HOURS_ADDED = "is_working_hours_added";
    public static final String KEY_USERID = "KEY_USERID";
    public static final String KEY_DEPARTMENT = "department";
    public static final String KEY_NAME = "NAME";
    public static final String KEY_EMAIL = "EMAIL";
    public static final String KEY_MOBILE = "MOBILE";
    public static final String KEY_TEAMLEADER = "TEAMLEADER";
    public static final String KEY_PROFILEPICTURE = "PROFILEPIC";
    public static final String KEY_LIST = "list";
    public static final String KEY_POSITION = "position";
    public static final String KEY_DASHBOARD_RESPONSE="dashboardresp";
    public static final String KEY_DEVICE_TOKEN = "devicetoken";
    public static final String KEY_NOTI_DEPARTMENT = "KEY_NOTI_DEPARTMENT";

    public HashMap<String, String> getList() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_LIST, pref.getString(KEY_LIST, ""));
        user.put(KEY_POSITION, pref.getString(KEY_POSITION, ""));

        return user;
    }


    public void setList(ArrayList<String> imageItemDTOArrayList,int position) {
        editor.putString(KEY_LIST, String.valueOf(imageItemDTOArrayList));
        editor.putString(KEY_POSITION, String.valueOf(position));

        editor.commit();
    }


    public SessionManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setLoginDetails(String id,String department,String name,String email, String mobile
            ,String teamLeader,String profilePicture,String notidepartment) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_USERID, id);
        editor.putString(KEY_DEPARTMENT,department );
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_TEAMLEADER, teamLeader);
        editor.putString(KEY_PROFILEPICTURE,profilePicture );
        editor.putString(KEY_NOTI_DEPARTMENT,notidepartment );



        editor.commit();
    }

    public HashMap<String, String> getLoginDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_USERID, pref.getString(KEY_USERID, ""));
        user.put(KEY_DEPARTMENT, pref.getString(KEY_DEPARTMENT, ""));
        user.put(KEY_NAME, pref.getString(KEY_NAME, ""));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, ""));
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, ""));
        user.put(KEY_TEAMLEADER, pref.getString(KEY_TEAMLEADER, ""));
        user.put(KEY_PROFILEPICTURE, pref.getString(KEY_PROFILEPICTURE, ""));
        user.put(KEY_NOTI_DEPARTMENT, pref.getString(KEY_NOTI_DEPARTMENT, ""));

        return user;
    }



    public void setDeviceToken(String id) {
        editor.putString(KEY_DEVICE_TOKEN, id);

        editor.commit();
    }

    public HashMap<String, String> getDeviceToken() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_DEVICE_TOKEN, pref.getString(KEY_DEVICE_TOKEN, ""));

        return user;
    }

    public LoadDashBoardResult getDashBoardRespone() {
        Gson gson = new Gson();
        String json = pref.getString(KEY_DASHBOARD_RESPONSE, "");
        LoadDashBoardResult obj = gson.fromJson(json, LoadDashBoardResult.class);
        return obj;
    }


    public void setDashBoardRespone(LoadDashBoardResult loadDashBoardResult) {
        Gson gson = new Gson();
        String json = gson.toJson(loadDashBoardResult);
        editor.putString(KEY_DASHBOARD_RESPONSE, json);
        editor.commit();
    }


}