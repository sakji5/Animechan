package com.example.animechan.home.ui

import androidx.lifecycle.*
import com.example.animechan.home.data.network.AnimeListRepo
import com.example.animechan.utils.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    private var animeListRepo: AnimeListRepo = AnimeListRepo()

    private val _animeList: MutableLiveData<List<String>> = MutableLiveData()
    val animeList: LiveData<List<String>> = _animeList

    private val animeListObserver: Observer<List<String>> = Observer {
        _animeList.value = it
    }

    init {
        animeListRepo.animeList.observeForever(animeListObserver)
        initObservers(animeListRepo)
    }

    fun getAnimeList() {

        viewModelScope.launch {
            animeListRepo.getAnimeList()
        }
    }

    override fun onCleared() {
        super.onCleared()
        removeObservers(animeListRepo)
        animeListRepo.animeList.removeObserver(animeListObserver)
    }

}