package com.gosty.tryoutapp.data.remote.network

import com.gosty.tryoutapp.data.remote.responses.NumerationResponse
import retrofit2.http.GET

interface ApiService {
    @GET("/tryout/numeration")
    suspend fun getAllNumerationTryouts(): NumerationResponse?
}