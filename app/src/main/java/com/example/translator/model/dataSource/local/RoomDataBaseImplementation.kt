package com.example.translator.model.dataSource.local

import com.example.translator.model.data.DataModel
import com.example.translator.model.dataSource.DataSource

class RoomDataBaseImplementation : DataSource<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        TODO("Not yet implemented")
    }
}