package com.example.animechan.home.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.animechan.RestClient
import com.example.animechan.home.data.network.service.AnimeApiService
import com.example.animechan.utils.BaseRepo

class AnimeListRepo: BaseRepo() {

    private var apiService = RestClient.getInstance().create(AnimeApiService::class.java)

    private val _animeList: MutableLiveData<List<String>> = MutableLiveData()
    val animeList: LiveData<List<String>> = _animeList

    suspend fun getAnimeList() {
        getData (_animeList) {
            apiService.getAnimeList()
        }
    }

}