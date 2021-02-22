package com.rojer_ko.mypayments.data.retrofit

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    @FormUrlEncoded
    suspend fun getTokenAsync(
        @Field("login")
        username: String,
        @Field("password")
        secret: String
    ): Response<JsonObject>
}