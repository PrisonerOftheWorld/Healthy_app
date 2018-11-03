package com.devilsoftware.healthy.main.API;

import com.devilsoftware.healthy.Models.Organisations.Organisations;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexAPI {

    @GET("/v1")
    Call<Organisations> getOrganisations(@Query("apikey") String apikey,
                                         @Query("text") String text,
                                         @Query("ll") String cityGeo,
                                         @Query("spn") String area,
                                         @Query("lang") String lang);

}
