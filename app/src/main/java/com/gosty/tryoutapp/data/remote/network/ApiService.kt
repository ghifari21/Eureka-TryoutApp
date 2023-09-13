package com.gosty.tryoutapp.data.remote.network

import com.gosty.tryoutapp.data.remote.responses.NumerationResponse
import retrofit2.http.GET

interface ApiService {
    @GET("mocks/8587320d-4d93-41e7-a2d4-23d4ebc2ceae/tryout/numeration")
    suspend fun getAllNumerationTryouts(): NumerationResponse
}