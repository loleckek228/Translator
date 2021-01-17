package com.example.translator.di

import androidx.room.Room
import com.example.translator.domain.usesCases.HistoryInteractor
import com.example.translator.domain.usesCases.MainInteractor
import com.example.translator.model.dataSource.DataSourceLocal
import com.example.translator.model.dataSource.DataSourceRemote
import com.example.translator.model.dataSource.local.AppDataBase
import com.example.translator.model.dataSource.local.RoomDataBaseImplementation
import com.example.translator.model.dataSource.remote.RetrofitImplementation
import com.example.translator.model.repositories.HistoryRepository
import com.example.translator.model.repositories.IHistoryRepository
import com.example.translator.model.repositories.IMainRepository
import com.example.translator.model.repositories.MainRepository
import com.example.translator.presentation.viewmodel.HistoryViewModel
import com.example.translator.presentation.viewmodel.SearchOfflineViewModel
import com.example.translator.presentation.viewmodel.SearchOnlineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDataBase::class.java,
            "HistoryDataBase"
        )
            .build()
    }

    single { get<AppDataBase>().historyDao() }

    single<DataSourceLocal> {
        RoomDataBaseImplementation(get())
    }

    single<DataSourceRemote> {
        RetrofitImplementation()
    }

    single<IMainRepository> {
        MainRepository(get(), get())
    }

    single<IHistoryRepository> {
        HistoryRepository(get())
    }
}

val mainScreen = module {
    factory { MainInteractor(get()) }
    viewModel { SearchOnlineViewModel(get()) }
    viewModel { SearchOfflineViewModel(get()) }
}

val historyScreen = module {
    factory { HistoryInteractor(get()) }
    viewModel { HistoryViewModel(get()) }
}