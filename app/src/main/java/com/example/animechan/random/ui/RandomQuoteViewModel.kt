package com.example.animechan.random.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.animechan.random.data.RandomQuoteRepo
import com.example.animechan.random.model.Quote
import com.example.animechan.utils.BaseViewModel
import kotlinx.coroutines.launch

class RandomQuoteViewModel : BaseViewModel () {

    private val randomQuoteRepo: RandomQuoteRepo = RandomQuoteRepo()
    private val _quotes: MutableLiveData<List<Quote>> = MutableLiveData()
    val quotes: LiveData<List<Quote>> = _quotes


    private val quotesObserver: Observer<List<Quote>> = Observer {
        _quotes.value = it
    }

    init {
        randomQuoteRepo.quotes.observeForever(quotesObserver)
        initObservers(randomQuoteRepo)
    }

    fun getQuotes(){
        viewModelScope.launch {
            randomQuoteRepo.getQuotes()
        }
    }

    override fun onCleared() {
        super.onCleared()
        removeObservers(randomQuoteRepo)
        randomQuoteRepo.quotes.removeObserver(quotesObserver)
    }
}