package com.rojer_ko.mypayments.data.retrofit

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("login")
    @FormUrlEncoded
    suspend fun getTokenAsync(
        @Field("login")
        username: String,
        @Field("password")
        secret: String
    ): Response<JsonObject>

    @GET("payments")
    suspend fun getPaymentsAsync(
        @Query("token") token: String
    ): Response<JsonObject>
}