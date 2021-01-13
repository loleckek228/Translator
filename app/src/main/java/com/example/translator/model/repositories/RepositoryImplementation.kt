package com.example.translator.model.repositories

import com.example.translator.model.data.DataModel
import com.example.translator.model.dataSource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> = dataSource.getData(word)
}