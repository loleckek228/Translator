package com.example.translator.model.dataSource

interface DataSource<T> {

    suspend fun getData(word: String): T
}