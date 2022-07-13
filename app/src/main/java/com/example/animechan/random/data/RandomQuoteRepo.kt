package com.example.animechan.random.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.animechan.RestClient
import com.example.animechan.random.model.RandomQuote
import com.example.animechan.utils.BaseRepo
import retrofit2.create

class RandomQuoteRepo (): BaseRepo () {

    private val randomQuoteAPI: RandomQuoteAPI = RestClient.getInstance().create(RandomQuoteAPI::class.java)

    private val _quotes: MutableLiveData<List<RandomQuote>> = MutableLiveData()
    val quotes: LiveData<List<RandomQuote>> = _quotes


    suspend fun getQuotes(){
        getData(_quotes) {
            randomQuoteAPI.getRandomQuotes()
        }
    }
}