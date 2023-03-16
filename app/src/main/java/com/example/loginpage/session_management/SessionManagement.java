package com.example.loginpage.session_management;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user_mail";
    String SESSION_USER_TYPE = "session_user_type";
    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


    }
    public void SaveSession(String email){
        editor.putString(SESSION_KEY,email).commit();

    }
    public String getSESSION_KEY(){
        return sharedPreferences.getString(SESSION_KEY,null);
    }
}
