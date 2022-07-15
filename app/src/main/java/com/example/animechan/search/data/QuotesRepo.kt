package com.example.animechan.search.data

import android.util.Log
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
//        getData(_quotes) {
//            quotesAPI.getQuotesByCharacter(name, page)
//        }
        val mock = QuotesMockRepo()
        _quotes.value =  mock.getQuotes()
    }

    suspend fun getQuotesByAnime (title: String, page: Int) {
        getData(_quotes) {
            quotesAPI.getQuotesByAnime(title, page)
        }
    }
}