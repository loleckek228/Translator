package com.example.translator.presentation.presenters

import com.example.translator.model.data.DataModel
import com.example.translator.presentation.view.View

interface Presenter<T : DataModel, V : View> {

    fun attachView(view: V)
    fun detachView(view: V)
    fun getData(word: String, isOnline: Boolean)
}