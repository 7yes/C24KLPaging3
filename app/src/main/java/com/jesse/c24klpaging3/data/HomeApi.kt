package com.jesse.c24klpaging3.data

import com.jesse.c24klpaging3.data.model.HomeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("character/")
    suspend fun getCharacters(@Query("page") page: Int): Response<HomeResponse>

}