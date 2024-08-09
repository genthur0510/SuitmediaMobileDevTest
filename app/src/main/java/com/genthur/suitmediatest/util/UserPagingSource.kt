package com.genthur.suitmediatest.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.genthur.suitmediatest.data.remote.api.ApiService
import com.genthur.suitmediatest.data.remote.response.DataItem
import com.genthur.suitmediatest.data.remote.response.ListResponse

class UserPagingSource(private val apiService: ApiService): PagingSource<Int, DataItem>() {
    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData: ListResponse = apiService.getList(position, params.loadSize)

            // Filter out null items
            val filteredData = responseData.data?.filterNotNull() ?: emptyList()

            LoadResult.Page(
                data = filteredData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (filteredData.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }


    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}