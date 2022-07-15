package com.example.animechan.random.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.animechan.RestClient
import com.example.animechan.random.model.Quote
import com.example.animechan.utils.BaseRepo

class RandomQuoteRepo (): BaseRepo () {

    private val randomQuoteAPI: RandomQuoteAPI = RestClient.getInstance().create(RandomQuoteAPI::class.java)

    private val _quotes: MutableLiveData<List<Quote>> = MutableLiveData()
    val quotes: LiveData<List<Quote>> = _quotes


    suspend fun getQuotes(){
        getData(_quotes) {
            randomQuoteAPI.getRandomQuotes()
        }
    }
}