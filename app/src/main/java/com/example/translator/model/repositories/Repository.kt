package com.example.translator.model.repositories

import io.reactivex.Observable

interface Repository<T> {

    fun getData(word: String): Observable<T>
}