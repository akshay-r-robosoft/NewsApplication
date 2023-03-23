package com.newsapplication.data.remote

import com.newsapplication.data.model.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getNews(
    @Query("country") country: String,
    @Query("category") category: String,
    @Query("apiKey") apiKey: String,
    @Query("pageSize") pageSize: Int,
    @Query("page") page: Int,
    ): Response<NewsModel>
}