package com.example.translator.model.dataSource

import com.example.translator.model.dataSource.local.HistoryEntity

interface DataSourceLocal {
    suspend fun saveData(data: List<HistoryEntity>)
    suspend fun getAllHistory(): List<HistoryEntity>
    suspend fun getWordData(word: String): HistoryEntity
}