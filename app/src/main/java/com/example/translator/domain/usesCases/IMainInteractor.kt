package com.example.translator.domain.usesCases

import com.example.translator.model.data.AppState
import com.example.translator.model.dataSource.local.HistoryEntity


interface IMainInteractor {
    suspend fun getDataOffline(word: String): HistoryEntity
    suspend fun getData(word: String): AppState
}