package com.example.translator.model.dataSource.local

import androidx.room.*

@Entity(
    indices = [Index(
        value = arrayOf("word"),
        unique = true
    )]
)
data class HistoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "translation")
    val translation: String?,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?
)