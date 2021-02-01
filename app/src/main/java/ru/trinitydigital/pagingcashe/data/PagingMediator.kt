package ru.trinitydigital.pagingcashe.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import ru.trinitydigital.pagingcashe.data.db.PagingCasheAppDatabase
import ru.trinitydigital.pagingcashe.data.model.RowsModel
import ru.trinitydigital.pagingcashe.data.remote.CoursesService

@ExperimentalPagingApi
class PagingMediator(
    private val service: CoursesService,
    private val db: PagingCasheAppDatabase
) : RemoteMediator<Int, RowsModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RowsModel>
    ): MediatorResult {
        TODO("Not yet implemented")
    }
}