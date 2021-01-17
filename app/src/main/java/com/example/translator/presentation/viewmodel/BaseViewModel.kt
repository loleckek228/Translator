package com.example.translator.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.translator.model.data.AppState
import kotlinx.coroutines.*

abstract class BaseViewModel<T>(
    protected open val mutableLiveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {

    protected val viewModelCouroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable -> handleError(throwable) }
    )

    abstract fun getData(word: String)
    abstract fun handleError(error: Throwable)

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected fun cancelJob() {
        viewModelCouroutineScope.coroutineContext.cancelChildren()
    }
}