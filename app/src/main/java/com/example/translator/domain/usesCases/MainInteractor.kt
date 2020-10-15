package com.example.translator.domain.usesCases

import com.example.translator.di.NAME_LOCAL
import com.example.translator.di.NAME_REMOTE
import com.example.translator.model.data.AppState
import com.example.translator.model.data.DataModel
import com.example.translator.model.repositories.Repository
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: Repository<List<DataModel>>,
    @Named(NAME_LOCAL) val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository
        } else {
            localRepository
        }.getData(word).map { AppState.Success(it) }
    }
}