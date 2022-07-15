package com.example.animechan.search.data

import com.example.animechan.random.model.Quote
import retrofit2.Response

class QuotesMockRepo {

    fun getQuotes(): List<Quote>{
        return mutableListOf(
            Quote("1", "1","1"),
            Quote("2", "1","2"),
            Quote("3", "1","3"),
            Quote("4", "1","4"),
            Quote("5", "1","5"),
            Quote("6", "1","6"),
            Quote("7", "1","7"),
            Quote("8", "1","8"),
            Quote("9", "1","9"),
            Quote("10", "1","10")
        )
    }
}