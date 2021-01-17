package com.example.translator.model.repositories

import com.example.translator.model.data.DataModel
import com.example.translator.model.dataSource.DataSourceLocal
import com.example.translator.model.dataSource.DataSourceRemote
import com.example.translator.model.dataSource.local.HistoryEntity
import com.example.translator.utils.convertDataModelListToHistoryEntityList

class MainRepository(
    private val dataSourceRemote: DataSourceRemote,
    private val dataSourceLocal: DataSourceLocal
) : IMainRepository {
    override suspend fun getRemoteData(word: String): List<DataModel> {
        return dataSourceRemote.getData(word).apply {
            saveData(this)
        }
    }

    override suspend fun getLocalData(word: String): HistoryEntity {
        return dataSourceLocal.getWordData(word)
    }

    private suspend fun saveData(data: List<DataModel>) {
        val historyEntityList = convertDataModelListToHistoryEntityList(data)
        dataSourceLocal.saveData(historyEntityList)
    }
}