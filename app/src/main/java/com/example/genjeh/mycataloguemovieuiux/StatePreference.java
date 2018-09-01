package com.example.genjeh.mycataloguemovieuiux;

import android.content.Context;
import android.content.SharedPreferences;

public class StatePreference {
    private String STATE_PREFERENCE="state_preference";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String KEY_DAILY_ON = "daily_on";
    private String KEY_RELASED_NOW = "released_now";

    public StatePreference(Context context) {
        sharedPreferences = context.getSharedPreferences(STATE_PREFERENCE,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setDailyOn(Boolean state){
        editor.putBoolean(KEY_DAILY_ON,state);
        editor.apply();
    }

    public void setReleasedNow(Boolean state){
        editor.putBoolean(KEY_RELASED_NOW,state);
        editor.apply();
    }

    public boolean getDailyOn(){
        return sharedPreferences.getBoolean(KEY_DAILY_ON,false);
    }

    public boolean getReleasedNow(){
        return sharedPreferences.getBoolean(KEY_RELASED_NOW,false);
    }

}
