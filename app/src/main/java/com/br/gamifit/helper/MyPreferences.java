package com.br.gamifit.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.br.gamifit.model.User;
import com.google.android.gms.flags.impl.SharedPreferencesFactory;

import java.util.Map;
import java.util.Set;
import java.util.prefs.PreferencesFactory;

public class MyPreferences {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String USER_NAME_TAG = "USERNAME";
    private static final String USER_EMAIL_TAG = "USEREMAILTAG";
    private static final String USER_CODE_TAG = "USERCODETAG";
    private static final String USER_PASSWORD_TAG = "USERPASSWORDTAG";
    private static final String USER_TOKEN = "USERTOKEN";

    public static final String PREFERENCES_FILE = "GAMIFIT.PREFERENCES";
    public static final int PREFERENCES_PRIVATE_MODE = 0;


    public MyPreferences(Context context){
        try {
            sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE,PREFERENCES_PRIVATE_MODE);
            editor = sharedPreferences.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearData(){
        editor.remove(USER_CODE_TAG);
        editor.remove(USER_EMAIL_TAG);
        editor.remove(USER_NAME_TAG);
        editor.remove(USER_PASSWORD_TAG);
        editor.apply();
    }
    public String getUserName(){
        return sharedPreferences.getString(USER_NAME_TAG,null);
    }

    public String getUserEmail(){
        return sharedPreferences.getString(USER_EMAIL_TAG,null);
    }

    public String getUserCode(){
        return sharedPreferences.getString(USER_CODE_TAG,null);
    }

    public String getUserPassword(){
        return sharedPreferences.getString(USER_PASSWORD_TAG,null);
    }

    public String getUserToken() {
        return sharedPreferences.getString(USER_TOKEN,null);
    }

    public void saveUserToken(String token){
        editor.putString(USER_TOKEN,token);
        editor.commit();
    }

    public boolean saveUserData(String name, String email, String password, String code){
        editor.putString(USER_NAME_TAG,name);
        editor.putString(USER_EMAIL_TAG,email);
        editor.putString(USER_CODE_TAG,code);
        editor.putString(USER_PASSWORD_TAG,password);
        return editor.commit();
    }

    public User getUser(){
        User user = new User();
        user.setName(getUserName());
        user.setCode(getUserCode());
        user.setEmail(getUserEmail());
        user.setToken(getUserToken());
        return user;
    }
}
