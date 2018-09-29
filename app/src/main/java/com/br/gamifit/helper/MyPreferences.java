package com.br.gamifit.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.android.gms.flags.impl.SharedPreferencesFactory;

import java.util.Map;
import java.util.Set;
import java.util.prefs.PreferencesFactory;

public class MyPreferences {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String USER_NAME_TAG = "USERNAME";
    public static final String USER_EMAIL_TAG = "USEREMAILTAG";
    public static final String USER_CODE_TAG = "USERCODETAG";
    public static final String USER_PASSWORD_TAG = "USERPASSWORDTAG";

    public static final String PREFERENCES_FILE = "GAMIFIT.PREFERENCES";
    public static final int PREFERENCES_PRIVATE_MODE = 0;

    private static MyPreferences myPreferences;

    private MyPreferences(Context context){
        try {
            sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE,PREFERENCES_PRIVATE_MODE);
            editor = sharedPreferences.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MyPreferences getMyPreferences(Context context){
        if(myPreferences==null){
            myPreferences = new MyPreferences(context);
        }
        return myPreferences;
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

    public boolean saveUserData(String name,String email,String password,String code){
        editor.putString(USER_NAME_TAG,name);
        editor.putString(USER_EMAIL_TAG,email);
        editor.putString(USER_CODE_TAG,code);
        editor.putString(USER_PASSWORD_TAG,password);
        return editor.commit();
    }
}
