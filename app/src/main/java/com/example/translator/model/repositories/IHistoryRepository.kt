package com.example.translator.model.repositories

import com.example.translator.model.dataSource.local.HistoryEntity

interface IHistoryRepository {
    suspend fun getAllHistory(): List<HistoryEntity>
}