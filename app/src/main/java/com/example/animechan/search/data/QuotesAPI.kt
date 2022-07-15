package com.example.animechan.search.data

import com.example.animechan.random.model.Quote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuotesAPI {

    @GET("quotes/character")
    suspend fun getQuotesByCharacter(
        @Query("name") name: String,
        @Query("page") page: Int
    ): Response<List<Quote>>

    @GET("quotes/anime")
    suspend fun getQuotesByAnime(
        @Query("title") title: String,
        @Query("page") page: Int
    ): Response<List<Quote>>

}