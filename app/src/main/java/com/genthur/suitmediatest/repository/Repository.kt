package com.genthur.suitmediatest.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.genthur.suitmediatest.data.remote.api.ApiService
import com.genthur.suitmediatest.data.remote.response.DataItem
import com.genthur.suitmediatest.util.UserPagingSource

class Repository private constructor(
    private val apiService: ApiService
){
    fun getUserLists():LiveData<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = { UserPagingSource(apiService) }
        ).liveData
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            apiService: ApiService,
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }
}