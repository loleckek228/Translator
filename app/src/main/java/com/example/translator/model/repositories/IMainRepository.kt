package com.example.translator.model.repositories

import com.example.translator.model.data.DataModel
import com.example.translator.model.dataSource.local.HistoryEntity

interface IMainRepository {
    suspend fun getRemoteData(word: String): List<DataModel>
    suspend fun getLocalData(word: String): HistoryEntity
}