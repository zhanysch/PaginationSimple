package ru.trinitydigital.pagingcashe.ui.main

import androidx.paging.PagedList
import ru.trinitydigital.pagingcashe.data.model.RowsModel

class MainDataSource : PagedList.BoundaryCallback<RowsModel>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
    }

    override fun onItemAtEndLoaded(itemAtEnd: RowsModel) {
        super.onItemAtEndLoaded(itemAtEnd)
    }

    override fun onItemAtFrontLoaded(itemAtFront: RowsModel) {
        super.onItemAtFrontLoaded(itemAtFront)
    }
}