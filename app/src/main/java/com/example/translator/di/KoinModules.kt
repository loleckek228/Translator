package com.example.translator.di

import com.example.translator.domain.usesCases.MainInteractor
import com.example.translator.model.data.DataModel
import com.example.translator.model.dataSource.local.RoomDataBaseImplementation
import com.example.translator.model.dataSource.remote.RetrofitImplementation
import com.example.translator.model.repositories.Repository
import com.example.translator.model.repositories.RepositoryImplementation
import com.example.translator.presentation.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }

    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) {
        RepositoryImplementation(
            RoomDataBaseImplementation()
        )
    }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    viewModel { MainViewModel(get()) }
}