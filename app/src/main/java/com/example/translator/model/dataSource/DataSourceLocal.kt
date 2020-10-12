package com.example.translator.model.dataSource

import com.example.translator.model.data.SearchResult
import io.reactivex.Observable

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> =
        remoteProvider.getData(word)

}