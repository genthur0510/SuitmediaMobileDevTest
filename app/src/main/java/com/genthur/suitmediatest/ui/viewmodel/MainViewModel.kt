package com.genthur.suitmediatest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.genthur.suitmediatest.data.remote.response.DataItem
import com.genthur.suitmediatest.repository.Repository

class MainViewModel(private val repository: Repository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getLists(): LiveData<PagingData<DataItem>> {
        return repository.getUserLists().cachedIn(viewModelScope)
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}