package com.rojer_ko.mypayments.data.provider

interface LocalAuthProvider {

    suspend fun saveToken(token: String)
    suspend fun getToken(): String
    suspend fun removeToken()
}