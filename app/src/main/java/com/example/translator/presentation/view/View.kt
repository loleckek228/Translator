package com.example.translator.presentation.view

import com.example.translator.model.data.DataModel

interface View {

    fun renderData(dataModel: DataModel)
}