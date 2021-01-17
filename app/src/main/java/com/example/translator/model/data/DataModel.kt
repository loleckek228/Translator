package com.example.translator.model.data

import com.squareup.moshi.Json

class DataModel(
    @field:Json(name = "text") val word: String?,
    @field:Json(name = "meanings") val meanings: List<Meanings>?
)