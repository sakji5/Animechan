package com.example.animechan.search.ui

import android.util.Log
import androidx.core.view.isEmpty
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.animechan.R
import com.example.animechan.random.model.Quote
import com.example.animechan.search.data.QuotesRepo
import com.example.animechan.utils.BaseViewModel
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {

    private val quotesRepo: QuotesRepo = QuotesRepo()

    private val _quotes: MutableLiveData<List<Quote>> = MutableLiveData()
    val quotes: LiveData<List<Quote>> = _quotes

    private var _currentPage: Int = 1
    private var previousQueryType: QueryType = QueryType.NONE
    private var previousQueryData: String = ""

    private val _dataValidatorMessageInfo: MutableLiveData<String?> = MutableLiveData(null)
    val dataValidatorMessageInfo: LiveData<String?> = _dataValidatorMessageInfo

    private val quotesObserver: Observer<List<Quote>> = Observer {
        _quotes.value = it
    }

    init {
        initObservers(quotesRepo)
        quotesRepo.quotes.observeForever(quotesObserver)
    }

    fun loadNextPage() {
        _currentPage += 1
        when (previousQueryType) {
            QueryType.ANIME_TITLE -> getQuotesByAnime(previousQueryData)
            QueryType.CHARACTER_NAME -> getQuotesByCharacter(previousQueryData)
            QueryType.NONE -> return
        }

    }

    fun getQuotes(query: String, checkedButtons: List<Int>){
        if (query.isEmpty() || checkedButtons.isEmpty()) {
            var errorMessage = ""
            if (query.isEmpty()) errorMessage += "Не заполнено поле запроса. "
            if (checkedButtons.isEmpty()) errorMessage += "Не выбран ни один критерий поиска."
            _dataValidatorMessageInfo.value = errorMessage
            return
        }

        if (checkedButtons[0] == R.id.anime_title_btn) {
            getQuotesByAnime(query)
        } else {
            getQuotesByCharacter(query)
        }
    }

    private fun getQuotesByCharacter(name: String) {
        if (previousQueryType == QueryType.ANIME_TITLE) _currentPage = 0
        previousQueryData = name
        previousQueryType = QueryType.ANIME_TITLE
        viewModelScope.launch {
            quotesRepo.getQuotesByCharacter(name, _currentPage)
        }
    }

    private fun getQuotesByAnime(title: String) {
        if (previousQueryType == QueryType.CHARACTER_NAME) _currentPage = 0

        previousQueryData = title
        previousQueryType = QueryType.CHARACTER_NAME
        viewModelScope.launch {
            quotesRepo.getQuotesByAnime(title, _currentPage)
        }
    }

    override fun onCleared() {
        super.onCleared()
        removeObservers(quotesRepo)
        quotesRepo.quotes.removeObserver(quotesObserver)
    }
}

enum class QueryType {
    NONE,
    ANIME_TITLE,
    CHARACTER_NAME
}