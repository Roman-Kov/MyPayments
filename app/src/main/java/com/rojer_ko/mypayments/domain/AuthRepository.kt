package com.rojer_ko.mypayments.domain

interface AuthRepository {

    suspend fun login(login: String, secret: String): DataResult
    suspend fun getToken(): DataResult
    suspend fun logout()
}