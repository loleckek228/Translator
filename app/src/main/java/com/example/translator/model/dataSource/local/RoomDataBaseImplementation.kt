package com.example.translator.model.dataSource.local

import com.example.translator.model.data.DataModel
import com.example.translator.model.dataSource.DataSource
import io.reactivex.Observable

class RoomDataBaseImplementation : DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("Not yet implemented")
    }
}