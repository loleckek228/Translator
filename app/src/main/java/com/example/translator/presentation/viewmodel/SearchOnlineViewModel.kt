package com.example.translator.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.example.translator.domain.usesCases.MainInteractor
import com.example.translator.model.data.AppState
import com.example.translator.utils.parseSearchResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchOnlineViewModel(private val interactor: MainInteractor) :
    BaseViewModel<AppState>() {
    private var liveDataForViewToObserve: LiveData<AppState> = mutableLiveData

    fun subscribe(): LiveData<AppState> = liveDataForViewToObserve

    override fun getData(word: String) {
        mutableLiveData.value = AppState.Loading(null)
        cancelJob()

        viewModelCouroutineScope.launch { startGetData(word) }
    }

    private suspend fun startGetData(word: String) =
        withContext(Dispatchers.IO) {
            mutableLiveData.postValue(parseSearchResults(interactor.getData(word)))
        }


    override fun handleError(error: Throwable) {
        mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}