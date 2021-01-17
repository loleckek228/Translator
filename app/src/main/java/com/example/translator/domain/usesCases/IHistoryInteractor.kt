package com.example.translator.domain.usesCases

import com.example.translator.model.data.AppState

interface IHistoryInteractor {
    suspend fun getAllHistory(): AppState
}
