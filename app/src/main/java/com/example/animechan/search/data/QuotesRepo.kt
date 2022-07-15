package com.example.animechan.search.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.animechan.RestClient
import com.example.animechan.random.model.Quote
import com.example.animechan.utils.BaseRepo

class QuotesRepo : BaseRepo () {

    private val quotesAPI = RestClient.getInstance().create(QuotesAPI::class.java)

    private val _quotes = MutableLiveData<List<Quote>> ()
    val quotes: LiveData<List<Quote>> = _quotes

    suspend fun getQuotesByCharacter (name: String, page: Int) {
        val isPageLoading = page <= 1
        getData(_quotes, isPageLoading) {
            quotesAPI.getQuotesByCharacter(name, page)
        }
    }

    suspend fun getQuotesByAnime (title: String, page: Int) {
        val isPageLoading = page <= 1
        getData(_quotes, isPageLoading) {
            quotesAPI.getQuotesByAnime(title, page)
        }
    }
}