package com.sample.searchnewsandroidapplication.network

import com.sample.searchnewsandroidapplication.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchNewsFactory {

    @GET("top-headlines")
    fun searchTopHeadlinesNews(
        @Query("q") q: String?,
        @Query("category") category: String?,
        @Query("country") country: String?,
        @Query("pageSize") pageSize: Int?,
        @Query("apiKey") apiKey: String
    ): Call<SearchResponse>

    @GET("everything")
    fun searchAllNews(
        @Query("q") q: String?,
        @Query("language") language: String?,
        @Query("sortBy") sortBy: String?,
        @Query("pageSize") pageSize: Int?,
        @Query("apiKey") apiKey: String
    ): Call<SearchResponse>

    @GET("sources")
    fun searchSourceNews(
        @Query("category") category: String?,
        @Query("language") language: String?,
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String
    ): Call<SearchResponse>
}