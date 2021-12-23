package com.ivy.workmanager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Admin on 22-02-2018.
 */

public class Sharedata {

    // Sharedpref file name
    private static final String PREF_NAME = "WorkManagerSharedDatas";
    // Shared Preferences
    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private Context context;

    private final String USER_ID = "UserId";
    private final String TEMPUSER_ID = "TempUserId";
    private final String DEVICE_ID = "DeviceId";
    private final String ISDEVICE_ID = "IsDeviceId";
    private final String WORKDATA = "Workdata";
    private final String ISDATEUPDATE = "Isdateupdate";
    private final String ISSKIP = "IsSkip";
    private final String ISAPPUPDATE = "IsAppUpdate";
    private final String ISDATAAVAILABLE = "IsDataAvailable";


    public Sharedata(Context context) {
        this.context = context;
        sPref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sPref.edit();
    }


    public void setUserId(int UserId) {
        editor.putInt(USER_ID, UserId);
        editor.commit();
    }

    public int getUserId() {
        return sPref.getInt(USER_ID, 0);
    }

    public void setTempUserId(int TempUserId) {
        editor.putInt(TEMPUSER_ID, TempUserId);
        editor.commit();
    }

    public int getTempUserId() {
        return sPref.getInt(TEMPUSER_ID, 0);
    }

    public void setDeviceId(String UserId) {
        editor.putString(DEVICE_ID, UserId);
        editor.commit();
    }

    public String getDeviceId() {
        return sPref.getString(DEVICE_ID, null);
    }

    public void setIsDeviceId(boolean UserId) {
        editor.putBoolean(ISDEVICE_ID, UserId);
        editor.commit();
    }

    public String getWorkData() {
        return sPref.getString(WORKDATA, null);
    }

    public void setWorkData(String date) {
        editor.putString(WORKDATA, date);
        editor.commit();
    }

    public String getDateupdate() {
        return sPref.getString(ISDATEUPDATE, null);
    }

    public void setDateupdate(String dateupdate) {
        editor.putString(ISDATEUPDATE, dateupdate);
        editor.commit();
    }

    public boolean getIsDeviceId() {
        return sPref.getBoolean(ISDEVICE_ID, false);
    }

    public void setIsSkip(boolean Skip) {
        editor.putBoolean(ISSKIP, Skip);
        editor.commit();
    }

    public boolean getIsSkip() {
        return sPref.getBoolean(ISSKIP, false);
    }

    public void setIsAppUpdate(boolean isAppUpdate) {
        editor.putBoolean(ISAPPUPDATE, isAppUpdate);
        editor.commit();
    }

    public boolean getIsAppUpdate() {
        return sPref.getBoolean(ISAPPUPDATE, false);
    }

    public void clearSharedData() {
        int tempId = getUserId();
        editor.clear();
        editor.commit();
        setUserId(0);
        setTempUserId(tempId);
        setWorkData(null);
    }
}
