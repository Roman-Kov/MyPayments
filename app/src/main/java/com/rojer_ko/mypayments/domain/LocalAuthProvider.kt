package com.rojer_ko.mypayments.domain

interface LocalAuthProvider {

    suspend fun saveToken(token: String)
    suspend fun getToken(): String
    suspend fun removeToken()
}