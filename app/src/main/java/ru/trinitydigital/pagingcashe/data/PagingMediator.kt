package ru.trinitydigital.pagingcashe.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ru.trinitydigital.pagingcashe.data.db.PagingCasheAppDatabase
import ru.trinitydigital.pagingcashe.data.model.PageKeys
import ru.trinitydigital.pagingcashe.data.model.RowsModel
import ru.trinitydigital.pagingcashe.data.remote.CoursesService
import java.io.IOException
import java.io.InvalidObjectException

const val START_PAGE = 1

@ExperimentalPagingApi  // класс для пагинации
class PagingMediator(
    private val service: CoursesService,
    private val db: PagingCasheAppDatabase
) : RemoteMediator<Int, RowsModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RowsModel>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {  // обновлен списка
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)

                remoteKeys?.nextKey?.minus(1) ?: START_PAGE
            }
            LoadType.PREPEND -> {  //когда скролитс вверх
                val remoteKeys =
                    getRemoteKeyForFirstItem(state)
                        ?: throw InvalidObjectException("prepend error")
                remoteKeys.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {   //когда скролитс вниз
                val remoteKeys =
                    getRemoteKeyForLastItem(state) ?: throw InvalidObjectException("prepend error")
                if (remoteKeys.nextKey == null) throw InvalidObjectException("prepend error")  // если null выкидвает исключен throw

                remoteKeys.nextKey
            }

        }
        // запросы в интернет
        try {
            Log.d("fsfsfs",page.toString())
            val apiResponse =
                service.getCourses("android:name,description", page, state.config.pageSize)
            val endOfPaginationReached = apiResponse.items.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {   // когда идет перезагруз страница вся база удаляетс
                    db.getPagingCasheDao().deleteAll()
                    db.getPagingKeysDao().deleteAll()
                }
                val prevkey = if (page == START_PAGE) null else page -1
                val nextkey = if (endOfPaginationReached) null else page +1
                val keys = apiResponse.items.map {
                    PageKeys(id = it.id, prevKey = prevkey,nextKey = nextkey)   // созд массив данных
                }
                db.getPagingKeysDao().insertAll(keys)
                db.getPagingCasheDao().insert(apiResponse.items)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached) //если список загрузлс, то пагинаци не загружает дальше
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(  // для предыдущ стрн
        state: PagingState<Int, RowsModel>
    ): PageKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                db.getPagingKeysDao().getKeyId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RowsModel>): PageKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                db.getPagingKeysDao().getKeyId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RowsModel>): PageKeys? {   // it.data.isNotEmpty() дополн проверка чтоб,лист не пришел пустым
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()  // когда скролитс вниз беретс последний или ноль lastOrNull()
            ?.let { repo ->
                db.getPagingKeysDao().getKeyId(repo.id)  //getKeyId(repo.id) вытасквт данные по id
            }
    }
}