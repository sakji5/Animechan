package com.example.animechan.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Response

abstract class BaseRepo {

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    protected suspend fun <T> getData(liveData: MutableLiveData<T>, isPageLoading: Boolean = false,apiRequest: suspend () -> Response<T>){
        try {
            if (!isPageLoading) _loading.value = true
            _error.value = ""
            val result = apiRequest()
            result.body().let {
                if (result.code() != 200) {
                    throw Exception ("Some error occurred")
                }
                liveData.postValue(result.body())
                _loading.value = false
            }
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
            _error.value = e.message
            _loading.value = false
        }
    }
}