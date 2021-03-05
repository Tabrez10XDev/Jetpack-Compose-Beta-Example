package com.example.inteliheadsinternship.api

import com.example.inteliheadsinternship.data.CartItems
import com.example.inteliheadsinternship.data.Data
import retrofit2.Response
import retrofit2.http.GET

interface IntelikartAPI {

    @GET("/v3/4509d8cc-2fe4-439c-9075-b9196c6ac455/")
    suspend fun getData(): Response<CartItems>

}