package com.example.data.api

import com.google.gson.JsonPrimitive
import retrofit2.Response
import retrofit2.http.GET

interface BalanceService {

    @GET("v2/rates/merchant/BTC/USD")
    suspend fun fetchExchangeRate(): Response<JsonPrimitive?>

}