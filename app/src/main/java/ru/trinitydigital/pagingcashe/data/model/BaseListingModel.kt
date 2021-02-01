package ru.trinitydigital.pagingcashe.data.model

data class BaseListingModel<T>(
    val items: List<T>,
    val total_count: Int
)