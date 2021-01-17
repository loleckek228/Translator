package com.example.translator.model.data

import com.squareup.moshi.Json

class Meanings(
    @field:Json(name = "translation") val translation: Translation?,
    @field:Json(name = "imageUrl") val imageUrl: String?
)