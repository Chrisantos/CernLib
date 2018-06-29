package com.example.chriseze.cernlib;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.chriseze.cernlib.Models.StudentModel;

/**
 * Created by CHRIS EZE on 6/28/2018.
 */

public class UserInfo {
    private String name, regno, department, level;
    private static final String PREF_NAME = "CERN-LIB";
    private static final String NAME = "name";
    private static final String REGNO = "regno";
    private static final String DEPARTMENT = "department";
    private static final String LEVEL = "level";
    private static final String _ID = "_id";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public UserInfo(Context context){
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setUser(StudentModel model){
        editor.putString(NAME, model.getName());
        editor.putString(REGNO, model.getRegno());
        editor.putString(DEPARTMENT, model.getDepartment());
        editor.putString(LEVEL, model.getLevel());
        editor.putString(_ID, model.get_id());
        editor.apply();
    }
    public StudentModel getUser(){
        return new StudentModel(
                preferences.getString(NAME, ""),
                preferences.getString(REGNO, ""),
                preferences.getString(DEPARTMENT, ""),
                preferences.getString(LEVEL, ""),
                preferences.getString(_ID, ""));
    }


}
