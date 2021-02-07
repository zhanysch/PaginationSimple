package ru.trinitydigital.pagingcashe.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import ru.trinitydigital.pagingcashe.data.remote.CoursesService
import ru.trinitydigital.pagingcashe.data.remote.RetrofitBuilder
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.trinitydigital.pagingcashe.data.db.PagingCasheAppDatabase
import ru.trinitydigital.pagingcashe.data.db.PagingCasheDao
import ru.trinitydigital.pagingcashe.data.repository.PagingRepository
import ru.trinitydigital.pagingcashe.ui.main.MainViewModel
import ru.trinitydigital.pagingcashe.ui.paginat_withoutcashe.WithoutCasheViewModel


val viewModelModule: Module = module {
    viewModel { MainViewModel(get()) }
    viewModel { WithoutCasheViewModel(get()) }
}

val dbModule: Module = module {
    single<PagingCasheAppDatabase> { PagingCasheAppDatabase.getInstanceDB(androidApplication()) }
}

val repositoryModule: Module = module {
    single { PagingRepository(get(),get())}
}

val apiModule: Module = module {
    single { RetrofitBuilder.buildRetrofit() }
}

val appModules =
    listOf(viewModelModule, apiModule, repositoryModule, dbModule)