package com.example.animechan.home.ui

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.*
import com.example.animechan.home.data.network.AnimeListRepo
import com.example.animechan.utils.ErrorMessageInfo
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var animeListRepo: AnimeListRepo = AnimeListRepo()

    private val _animeList: LiveData<List<String>> = animeListRepo.animeList
    val animeList: LiveData<List<String>> = _animeList

    private val _errorMessageState: MutableLiveData<ErrorMessageInfo> = MutableLiveData(ErrorMessageInfo(View.GONE, ""))
    val errorMessageState:LiveData<ErrorMessageInfo> = _errorMessageState

    private val _progressBarState = MutableLiveData(View.GONE)
    val progressBarState: LiveData<Int> = _progressBarState

    private val loadingObserver: Observer<Boolean> = Observer {
        _progressBarState.postValue(if (it!!) ProgressBar.VISIBLE else ProgressBar.GONE)
    }

    private val errorObserver: Observer<String> = Observer { message ->
        val viewVisibility = if (message.isBlank()) View.GONE else View.VISIBLE
        _errorMessageState.postValue(ErrorMessageInfo(viewVisibility, message))
    }

    init {
        animeListRepo.loading.observeForever(loadingObserver)
        animeListRepo.error.observeForever(errorObserver)
    }

    fun getAnimeList() {
        if (!_animeList.value.isNullOrEmpty()) return

        viewModelScope.launch {
            animeListRepo.getAnimeList()
        }

    }

    override fun onCleared() {
        super.onCleared()
        animeListRepo.loading.removeObserver(loadingObserver)
        animeListRepo.error.removeObserver(errorObserver)
    }

}