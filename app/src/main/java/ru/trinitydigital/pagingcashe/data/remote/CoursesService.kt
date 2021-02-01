package ru.trinitydigital.pagingcashe.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.trinitydigital.pagingcashe.data.model.BaseListingModel
import ru.trinitydigital.pagingcashe.data.model.RowsModel

interface CoursesService {

    @GET("search/repositories?sort=stars")
    suspend fun getCourses(
            @Query("q") query: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int
    ): BaseListingModel<RowsModel>
}