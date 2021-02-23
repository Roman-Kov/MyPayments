package com.rojer_ko.mypayments.domain.contracts

import com.rojer_ko.mypayments.domain.model.DataResult

interface AuthRepository {

    suspend fun login(login: String, secret: String): DataResult<String>
    suspend fun getToken(): DataResult<String>
    suspend fun logout()
}