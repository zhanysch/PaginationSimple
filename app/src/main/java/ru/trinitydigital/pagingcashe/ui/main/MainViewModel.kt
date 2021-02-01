package ru.trinitydigital.pagingcashe.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.trinitydigital.pagingcashe.data.model.BaseListingModel
import ru.trinitydigital.pagingcashe.data.model.RowsModel

class MainViewModel() : ViewModel() {

    val data = MutableLiveData<BaseListingModel<RowsModel>>()
}