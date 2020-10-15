package com.example.translator.domain.usesCases


interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}