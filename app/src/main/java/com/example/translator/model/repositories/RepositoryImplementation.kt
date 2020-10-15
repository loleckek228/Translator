package com.example.translator.model.repositories

import com.example.translator.model.data.DataModel
import com.example.translator.model.dataSource.DataSource
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> = dataSource.getData(word)
}