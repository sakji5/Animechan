package com.example.animechan.home.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.animechan.RestClient
import com.example.animechan.home.data.network.service.ApiService

class AnimeListRepo {

    private var apiService = RestClient.getInstance().create(ApiService::class.java)

    private val _animeList: MutableLiveData<List<String>> = MutableLiveData()
    val animeList: LiveData<List<String>> = _animeList

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    suspend fun getAnimeList() {

        Log.d("HOMEREPO", animeList.value?.size.toString())
        try {
            _loading.value = true
            _error.value = ""

            val result = apiService.getAnimeList()
            result.body()?.let {
                _animeList.postValue(it)
                _loading.value = false
            }
        } catch (e: Exception) {
            _error.value = (e.message ?: "some error occurred")
            _loading.value = false
        }
    }

}