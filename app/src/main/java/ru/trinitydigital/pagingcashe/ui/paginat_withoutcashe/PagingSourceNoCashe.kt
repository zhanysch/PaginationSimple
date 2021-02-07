package ru.trinitydigital.pagingcashe.ui.paginat_withoutcashe

import android.app.DownloadManager
import androidx.paging.PagingSource
import retrofit2.HttpException
import ru.trinitydigital.pagingcashe.data.START_PAGE
import ru.trinitydigital.pagingcashe.data.model.RowsModel
import ru.trinitydigital.pagingcashe.data.remote.CoursesService
import ru.trinitydigital.pagingcashe.data.repository.PagingRepository.Companion.PAGE_SIZE
import java.io.IOException

private const val PARAM_QUERY = "in:name,description"


class PagingSourceNoCashe(
    private val service: CoursesService,
    private val query: String
) : PagingSource<Int, RowsModel>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RowsModel> {

        val page =
            params.key ?: START_PAGE  //  :? проверка на null берет или params.key или START_PAGE
        val apiQuery = query + PARAM_QUERY

        return try {
            val response = service.getCourses(
                query = apiQuery,
                page = page,
                params.loadSize
            )  // params.loadSize сколко стр будет загружатс
            val itemsCount = response.items.size // узнаем сколько стрнц

            val nextPage = if (itemsCount == 0) { // узнаем есть ли следующ стрнц
                null
            } else {
                page + (params.loadSize / PAGE_SIZE)
            }
            LoadResult.Page(
                data = response.items,
                prevKey = if (page == START_PAGE) null else page - 1,
                nextKey = nextPage

            )
        } catch (e: IOException) {
            LoadResult.Error(e)

        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}