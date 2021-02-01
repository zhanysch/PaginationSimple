package ru.trinitydigital.pagingcashe.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PageKeys(
    @PrimaryKey
    val id: Long,
    val prevKey: Int?,
    val nextKey: Int?
)