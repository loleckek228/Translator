package com.example.translator.model.dataSource

import com.example.translator.model.data.DataModel

interface DataSourceRemote {
    suspend fun getData(word: String): List<DataModel>
}