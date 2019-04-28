package com.sample.searchnewsandroidapplication.network

import com.sample.searchnewsandroidapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestControllerFactory {

    private const val timeoutInterval = 60
    private var searchNewsFactory: SearchNewsFactory?
    private val client: OkHttpClient

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(timeoutInterval.toLong(), TimeUnit.SECONDS)
        httpClient.readTimeout(timeoutInterval.toLong(), TimeUnit.SECONDS)
        client = httpClient.build()

        val serviceSearch = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        searchNewsFactory = serviceSearch.create(SearchNewsFactory::class.java)
    }

    fun getSearchNewsFactory(): SearchNewsFactory? {
        return searchNewsFactory
    }
}