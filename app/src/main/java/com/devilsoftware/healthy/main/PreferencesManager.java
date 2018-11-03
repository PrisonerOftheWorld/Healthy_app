package com.devilsoftware.healthy.main;

import android.content.Context;
import android.content.SharedPreferences;

import com.devilsoftware.healthy.main.App;
import com.google.gson.Gson;


public class PreferencesManager {

        private SharedPreferences mSharedPreferences;
        private Gson mGson;

        public SharedPreferences getSharedPreferences() {
            return mSharedPreferences;
        }

        PreferencesManager() {
            mSharedPreferences = App.getAppContext().getSharedPreferences("default", Context.MODE_PRIVATE);
        }

        public void setFieldInt(String name, int num){
            mSharedPreferences.edit().putInt(name, num).apply();
        }

        public int getFieldInt(String name){
            return mSharedPreferences.getInt(name,0);
        }

        public void setField(String name, float num){
            mSharedPreferences.edit().putFloat(name, num).apply();
        }

        public float getField(String name){
            return mSharedPreferences.getFloat(name, 0);
        }

        public void setIMT(float f){
            mSharedPreferences.edit().putFloat("imt",f).apply();
        }

}
