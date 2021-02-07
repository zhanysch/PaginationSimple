package ru.trinitydigital.pagingcashe.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.trinitydigital.pagingcashe.data.model.BaseListingModel
import ru.trinitydigital.pagingcashe.data.model.RowsModel
import ru.trinitydigital.pagingcashe.data.repository.PagingRepository

class MainViewModel(private val repository : PagingRepository) : ViewModel() {


    @ExperimentalPagingApi
    fun getPagingData(): LiveData<PagingData<RowsModel>>{
        return repository.getPagingResult()
    }
}