package com.example.translator.model.repositories

interface Repository<T> {

    suspend fun getData(word: String): T
}