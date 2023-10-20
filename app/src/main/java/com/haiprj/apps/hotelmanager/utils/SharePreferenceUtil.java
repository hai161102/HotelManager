package com.haiprj.apps.hotelmanager.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {

    @SuppressLint("StaticFieldLeak")
    private static SharePreferenceUtil instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Activity context;

    public static SharePreferenceUtil getInstance() {
        if (SharePreferenceUtil.instance == null) SharePreferenceUtil.instance = new SharePreferenceUtil();
        return SharePreferenceUtil.instance;
    }

    private SharePreferenceUtil() {
    }

    public void init(Activity context) {
        this.context = context;
        this.sharedPreferences = context.getPreferences(Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }
}
