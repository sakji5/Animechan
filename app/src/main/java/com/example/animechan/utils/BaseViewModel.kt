package com.example.animechan.utils

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val _errorMessageState: MutableLiveData<ErrorMessageInfo> = MutableLiveData(ErrorMessageInfo(
        View.GONE, ""))
    val errorMessageState: LiveData<ErrorMessageInfo> = _errorMessageState

    private val _progressBarState = MutableLiveData(View.GONE)
    val progressBarState: LiveData<Int> = _progressBarState

    private val loadingObserver: Observer<Boolean> = Observer {
        _progressBarState.postValue(if (it!!) ProgressBar.VISIBLE else ProgressBar.GONE)
    }

    private val errorObserver: Observer<String> = Observer { message ->
        val viewVisibility = if (message.isBlank()) View.GONE else View.VISIBLE
        _errorMessageState.postValue(ErrorMessageInfo(viewVisibility, message))
    }

    protected fun initObservers(networkUtils: BaseRepo){
        networkUtils.loading.observeForever(loadingObserver)
        networkUtils.error.observeForever(errorObserver)
    }

    protected fun removeObservers(networkUtils: BaseRepo){
         networkUtils.loading.removeObserver(loadingObserver)
        networkUtils.error.removeObserver(errorObserver)
    }

}