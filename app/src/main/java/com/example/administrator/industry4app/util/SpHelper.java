package com.example.administrator.industry4app.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator
 * on 2016/6/23.
 */
public class SpHelper {

    private final String spUser = "User";
    private final String spPassword = "password";

    private final String spFile = "Industry";


    private SharedPreferences InShared;
    private SharedPreferences.Editor InEditor;

    private static SpHelper instance;

    private SpHelper(Context context){
        InShared = context.getSharedPreferences(
                spFile, Activity.MODE_PRIVATE);
        InEditor = InShared.edit();
    }


    public static SpHelper getInstance(Context context){
        if(instance == null){
            instance = new SpHelper(context);
        }
        return instance;
    }


    //用户名
    public String getSpUserText() {
        return InShared.getString(spUser, null);
    }

    public void saveSpUserText(String user) {
        InEditor.putString(spUser, user);
        InEditor.commit();
    }


    //密码
    public String getSpPasswordText() {
        return InShared.getString(spPassword, null);
    }

    public void saveSpPasswordText(String password) {
        InEditor.putString(spPassword, password);
        InEditor.commit();
    }


}
