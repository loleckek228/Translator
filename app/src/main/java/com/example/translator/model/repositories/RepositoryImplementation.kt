package com.example.translator.model.repositories

import com.example.translator.model.data.SearchResult
import com.example.translator.model.dataSource.DataSource
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResult>>) :
    Repository<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> = dataSource.getData(word)
}