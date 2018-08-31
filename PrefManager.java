package com.consite.e_reader;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yogesh on 10/3/18.
 */

public class PrefManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String prefName = " Welcome ";

    private static final String isFirstTimeLaunch = "Is First Time Launch";

    public PrefManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(prefName,0);
        editor = preferences.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(isFirstTimeLaunch,isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimelaunch(){
        return preferences.getBoolean(isFirstTimeLaunch,true);
    }
}
