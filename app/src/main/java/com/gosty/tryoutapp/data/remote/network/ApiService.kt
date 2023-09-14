package com.gosty.tryoutapp.data.remote.network

import com.gosty.tryoutapp.data.remote.responses.NumerationResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    /***
     * this method call API to get all numeration tryouts.
     * @author Ghifari Octaverin
     * @since Sept 4th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    @GET("mocks/8587320d-4d93-41e7-a2d4-23d4ebc2ceae/tryout/numeration")
    suspend fun getAllNumerationTryouts(): Response<NumerationResponse>
}