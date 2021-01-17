package com.example.translator.domain.usesCases

import com.example.translator.model.data.AppState
import com.example.translator.model.repositories.IHistoryRepository
import com.example.translator.utils.convertHistoryEntityToDataModelList

class HistoryInteractor(private val repository: IHistoryRepository) : IHistoryInteractor {
    override suspend fun getAllHistory(): AppState {
        return AppState.Success(
            convertHistoryEntityToDataModelList(repository.getAllHistory())
        )
    }
}