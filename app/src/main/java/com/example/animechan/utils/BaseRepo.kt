package com.example.animechan.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Response

abstract class BaseRepo {

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    protected suspend fun <T> getData(liveData: MutableLiveData<T>,apiRequest: suspend () -> Response<T>){
        try {
            _loading.value = true
            _error.value = ""

            val result = apiRequest()
            result.body().let {
                liveData.postValue(result.body())
                _loading.value = false
            }
        } catch (e: Exception) {
            _error.value = (e.message ?: "some error occurred")
            _loading.value = false
        }
    }
}