package com.example.skripsixml

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
    interface ApiService {
        @GET("volumes")
        fun getBooks(
            @Query("q") query: String,
            @Query("key") apiKey: String,
            @Query("maxResults") maxResults: Int
        ): Call<BookResponse>
    }
}