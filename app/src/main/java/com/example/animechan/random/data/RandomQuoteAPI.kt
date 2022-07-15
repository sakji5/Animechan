package com.example.animechan.random.data

import com.example.animechan.random.model.Quote
import retrofit2.Response
import retrofit2.http.GET

interface RandomQuoteAPI {

    @GET("quotes")
    suspend fun getRandomQuotes(): Response<List<Quote>>
}