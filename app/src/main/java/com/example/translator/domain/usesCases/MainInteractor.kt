package com.example.translator.domain.usesCases

import com.example.translator.model.data.AppState
import com.example.translator.model.dataSource.local.HistoryEntity
import com.example.translator.model.repositories.IMainRepository

class MainInteractor(
    private val repository: IMainRepository
) : IMainInteractor {
    override suspend fun getData(word: String): AppState {
        return AppState.Success(
            repository.getRemoteData(word)
        )
    }

    override suspend fun getDataOffline(word: String): HistoryEntity {
        return repository.getLocalData(word)
    }

}