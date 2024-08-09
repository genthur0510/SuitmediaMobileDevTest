package com.genthur.suitmediatest.data.remote.api

import com.genthur.suitmediatest.data.remote.response.ListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ):ListResponse
}