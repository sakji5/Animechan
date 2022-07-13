package com.example.animechan.home.data.network.service

import retrofit2.Response
import retrofit2.http.GET

interface AnimeApiService {

    @GET("available/anime")
    suspend fun getAnimeList() : Response<List<String>>
}
