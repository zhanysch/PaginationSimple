package ru.trinitydigital.pagingcashe.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity       // data class сохрн данн предыдущ и след стрнц
data class PageKeys(
    @PrimaryKey
    val id: Long,  // id каждой страницы 1стр, 2 стр
    val prevKey: Int?,  // стрнц предыдущ записи
    val nextKey: Int?   // стрнц следщ записи
)