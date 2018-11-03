package com.devilsoftware.healthy.main.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {

    private Retrofit mRetrofit;

    private HealthyAPI mHealthyAPI;
    YandexAPI mYandexAPI;

    public void initRetrofit(String url){
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://" + url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mHealthyAPI = mRetrofit.create(HealthyAPI.class);
    }

    public void initYandex(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://search-maps.yandex.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mYandexAPI = mRetrofit.create(YandexAPI.class);
    }

    public YandexAPI getYandexAPI() {
        return mYandexAPI;
    }

    public HealthyAPI getHealthyAPI(){
        return mHealthyAPI;
    }

}
