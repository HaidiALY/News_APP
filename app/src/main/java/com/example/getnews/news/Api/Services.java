package com.example.getnews.news.Api;

import com.example.getnews.news.Models.NewsResponse.NewsResponse;
import com.example.getnews.news.Models.SourcesResponse.SourcesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {

    @GET("sources")
    Call<SourcesResponse> getNewsSources(@Query("apiKey") String apiKey,@Query("language") String Language);

    @GET("everything")
    Call<NewsResponse>getNewsBySourceId(@Query("apiKey") String apiKey
                                        ,@Query("language") String Language
                                        ,@Query("sources") String sources);
}
