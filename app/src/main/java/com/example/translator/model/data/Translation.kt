package com.example.translator.model.data

import com.squareup.moshi.Json

class Translation(
    @field:Json(name = "text")
    val translation: String?
)