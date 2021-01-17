package com.example.translator.utils

import com.example.translator.model.data.AppState
import com.example.translator.model.data.DataModel
import com.example.translator.model.data.Meanings
import com.example.translator.model.data.Translation
import com.example.translator.model.dataSource.local.HistoryEntity

fun parseSearchResults(state: AppState): AppState {
    val newSearchResults = arrayListOf<DataModel>()

    when (state) {
        is AppState.Success -> {
            val searchResults = state.data
            if (!searchResults.isNullOrEmpty()) {
                for (searchResult in searchResults) {
                    newSearchResults.addAll(parseResult(searchResult))
                }
            }
        }
    }

    return AppState.Success(newSearchResults)
}

private fun parseResult(dataModel: DataModel): ArrayList<DataModel> {
    var newDataModels = arrayListOf<DataModel>()

    if (!dataModel.word.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()

        for (meaning in dataModel.meanings) {
            if (meaning.translation != null && !meaning.translation.translation.isNullOrBlank()) {
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
            }
        }

        if (newMeanings.isNotEmpty()) {
            newDataModels.add(DataModel(dataModel.word, newMeanings))
        }
    }

    return newDataModels
}

fun convertHistoryEntityToDataModelList(entityList: List<HistoryEntity>): List<DataModel> {
    val dataModelList = arrayListOf<DataModel>()

    entityList.map {
        val meanings = listOf(
            Meanings(
                translation = Translation(it.translation),
                imageUrl = it.imageUrl
            )
        )

        dataModelList.add(
            DataModel(
                word = it.word,
                meanings = meanings
            )
        )
    }


    return dataModelList
}

fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()

    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.translation, ", ")
        } else {
            meaning.translation?.translation
        }
    }

    return meaningsSeparatedByComma
}

fun convertDataModelListToHistoryEntityList(dataModel: List<DataModel>): List<HistoryEntity> {
    val historyEntityList = arrayListOf<HistoryEntity>()

    dataModel.map { dataModel ->
        dataModel.meanings?.map { meanings ->
            historyEntityList.add(
                HistoryEntity(
                    word = dataModel.word!!,
                    translation = meanings.translation?.translation,
                    imageUrl = meanings.imageUrl
                )
            )
        }
    }

    return historyEntityList
}