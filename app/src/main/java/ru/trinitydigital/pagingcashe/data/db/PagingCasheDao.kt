package ru.trinitydigital.pagingcashe.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.trinitydigital.pagingcashe.data.model.RowsModel

@Dao
interface PagingCasheDao {

    @Insert
    fun insert(data: List<RowsModel>)

    @Query("SELECT * FROM rowsmodel")
    fun getAll(): PagingSource<Int, RowsModel>

}