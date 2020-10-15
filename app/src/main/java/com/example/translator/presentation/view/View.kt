package com.example.translator.presentation.view

import com.example.translator.model.data.AppState

interface View {

    fun renderData(dataModel: AppState)
}