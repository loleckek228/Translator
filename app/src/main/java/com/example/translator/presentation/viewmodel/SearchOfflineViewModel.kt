package com.example.translator.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.example.translator.domain.usesCases.MainInteractor
import com.example.translator.model.dataSource.local.HistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchOfflineViewModel(private val interactor: MainInteractor) :
    BaseViewModel<HistoryEntity>() {
    private var liveDataForViewToObserve: LiveData<HistoryEntity> = mutableLiveData

    fun subscribe(): LiveData<HistoryEntity> = liveDataForViewToObserve

    override fun getData(word: String) {
        cancelJob()

        viewModelCouroutineScope.launch { startInteractor(word) }
    }

    private suspend fun startInteractor(word: String) =
        withContext(Dispatchers.IO) {
            mutableLiveData.postValue(interactor.getDataOffline(word))
        }

    override fun handleError(error: Throwable) {
        mutableLiveData.postValue(throw Exception("Error in DataBase"))
    }

    override fun onCleared() {
        mutableLiveData.value = null
        super.onCleared()
    }
}