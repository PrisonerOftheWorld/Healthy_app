package com.devilsoftware.healthy.main.API;

import com.devilsoftware.healthy.Models.Fields;
import com.devilsoftware.healthy.Models.HashRegions;
import com.devilsoftware.healthy.Models.IllnessAddRequest;
import com.devilsoftware.healthy.Models.ArticleModel;
import com.devilsoftware.healthy.Models.IllnessRequest;
import com.devilsoftware.healthy.Models.IllnessesResult;
import com.devilsoftware.healthy.Models.Status;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HealthyAPI {

    @GET("/region")
    Call<HashRegions> getRegions(@Query("level") int level);

    @GET("/field")
    Call<Fields> getFields(@Query("region") int region);

    @POST("/detect")
    Call<IllnessesResult> detectIllness(@Body IllnessRequest illnessRequest);

    @POST("/add")
    Call<Status> addIllness(@Body IllnessAddRequest illnessAddRequest);

    @GET("/illnesses")
    Call<List<ArticleModel>> getIllnesses(@Query("find") String findRequest);

    @GET("/articles")
    Call<List<ArticleModel>> getArticles();

}
