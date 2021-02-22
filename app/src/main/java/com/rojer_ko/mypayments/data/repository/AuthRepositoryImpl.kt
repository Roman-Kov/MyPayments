package com.rojer_ko.mypayments.data.repository

import com.rojer_ko.mypayments.data.provider.AuthProvider
import com.rojer_ko.mypayments.data.provider.LocalAuthProvider
import com.rojer_ko.mypayments.domain.contracts.AuthRepository
import com.rojer_ko.mypayments.domain.model.DataResult
import com.rojer_ko.mypayments.utils.Consts

var ACCESS_TOKEN = ""

class AuthRepositoryImpl(
    private val localAuthProvider: LocalAuthProvider,
    private val authProvider: AuthProvider
): AuthRepository {

    override suspend fun login(login: String, secret: String): DataResult {
        return when (val tokenResult = authProvider.login(login, secret)) {
            is DataResult.Success<*> -> {
                val token: String = tokenResult.data.toString()
                if (token.isNotBlank()) {
                    ACCESS_TOKEN = token
                    localAuthProvider.saveToken(token)
                    DataResult.Success(Consts.Result.OK)
                } else {
                    DataResult.Error(Throwable(Consts.Error.BAD_RESPONSE))
                }
            }
            is DataResult.Error -> {
                tokenResult
            }
            else -> DataResult.Error(Throwable(Consts.Error.BAD_RESPONSE))
        }
    }

    override suspend fun getToken(): DataResult {
        val token = localAuthProvider.getToken()
        return if (token.isNotBlank()) {
            ACCESS_TOKEN = token
            DataResult.Success(Consts.Result.OK)
        } else {
            DataResult.Error(
                Throwable(Consts.Error.TOKEN_IS_BLANK)
            )
        }
    }

    override suspend fun logout() {
        ACCESS_TOKEN = ""
        localAuthProvider.removeToken()
    }
}