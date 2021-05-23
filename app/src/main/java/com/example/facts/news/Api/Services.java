package com.example.facts.news.Api;

import com.example.facts.news.Models.NewsResponse.NewsResponse;
import com.example.facts.news.Models.SourcesResponse.SourcesResponse;

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
