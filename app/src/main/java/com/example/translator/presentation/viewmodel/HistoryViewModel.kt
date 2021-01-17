package com.example.translator.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.translator.domain.usesCases.HistoryInteractor
import com.example.translator.domain.usesCases.MainInteractor
import com.example.translator.model.data.AppState
import com.example.translator.model.dataSource.local.HistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(private val interactor: HistoryInteractor) :
    BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String) {
        cancelJob()

        viewModelCouroutineScope.launch { startInteractor() }
    }

    private suspend fun startInteractor() =
        withContext(Dispatchers.IO) {
            mutableLiveData.postValue(interactor.getAllHistory())
        }

    override fun onCleared() {
        mutableLiveData.value = null

        super.onCleared()
    }

    override fun handleError(error: Throwable) {
        mutableLiveData.postValue(throw Exception("Error in DataBase"))
    }
}
