package com.example.translator.model.repositories

import com.example.translator.model.dataSource.DataSourceLocal
import com.example.translator.model.dataSource.local.HistoryEntity

class HistoryRepository(private val dataSourceLocal: DataSourceLocal) : IHistoryRepository {
    override suspend fun getAllHistory(): List<HistoryEntity> {
        return dataSourceLocal.getAllHistory()
    }
}