package com.example.translator.domain.usesCases

import io.reactivex.Observable


interface Interactor<T> {

    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}