package com.example.translator.model.dataSource.local

import com.example.translator.model.dataSource.DataSourceLocal

class RoomDataBaseImplementation(private val historyDao: HistoryDao) : DataSourceLocal {

    override suspend fun saveData(data: List<HistoryEntity>) {
        historyDao.insertAll(data)
    }

    override suspend fun getAllHistory(): List<HistoryEntity> {
        val data = historyDao.getAllHistory()

        return data
    }

    override suspend fun getWordData(word: String): HistoryEntity {
        val wordData = historyDao.getWordFromHistory(word)

        return wordData
    }
}