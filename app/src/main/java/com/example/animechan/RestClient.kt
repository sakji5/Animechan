package com.example.animechan

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {

    private const val BASE_URL = "https://animechan.vercel.app/api/"

    fun getInstance(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpclient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpclient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
