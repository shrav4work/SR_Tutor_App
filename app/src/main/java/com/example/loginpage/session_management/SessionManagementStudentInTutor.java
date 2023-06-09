package com.example.loginpage.session_management;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagementStudentInTutor {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor1;
    String SHARED_PREF_NAME = "session";
    String email;
    String SESSION_KEY = "session_user_mail";
    String SESSION_USER_TYPE = "session_user_type";
    public SessionManagementStudentInTutor(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


    }
    public void SaveSession(String id){
        editor.putString(SESSION_KEY,id).commit();

    }
//
//    public String getId() {
//        return sharedPreferences.getString("id",null);
//    }
//
//    public void setEmail(String email) {
//        editor.putString("email",email).commit();
//    }

    public String getSESSION_KEY(){
        return sharedPreferences.getString(SESSION_KEY,null);
    }

    public void removeSession(){
        editor.putString(SESSION_KEY,null).commit();
    }
}
