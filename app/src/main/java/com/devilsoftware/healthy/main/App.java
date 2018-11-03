package com.devilsoftware.healthy.main;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.devilsoftware.healthy.main.API.APIService;

public class App extends Application {

    public static String URL = "192.168.43.148:8080";

    static APIService mAPIService = new APIService();
    static Context mContext;
    static PreferencesManager sPreferencesManager;


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mAPIService.initRetrofit(URL);
        mContext = getApplicationContext();
        sPreferencesManager = new PreferencesManager();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static PreferencesManager getsPreferencesManager() {
        return sPreferencesManager;
    }

    public static APIService getAPIService() {
        return mAPIService;
    }
}
