package com.rojer_ko.mypayments.data.provider

import com.rojer_ko.mypayments.domain.model.DataResult

interface AuthProvider {

    suspend fun login(login: String, secret: String): DataResult
}