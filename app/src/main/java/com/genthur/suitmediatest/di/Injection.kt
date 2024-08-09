package com.genthur.suitmediatest.di

import android.content.Context
import com.genthur.suitmediatest.data.remote.api.ApiConfig
import com.genthur.suitmediatest.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}