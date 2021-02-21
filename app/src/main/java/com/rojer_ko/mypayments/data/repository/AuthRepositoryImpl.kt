package com.rojer_ko.mypayments.data.repository

import com.rojer_ko.mypayments.domain.AuthRepository
import com.rojer_ko.mypayments.domain.DataResult
import com.rojer_ko.mypayments.domain.LocalAuthProvider
import com.rojer_ko.mypayments.utils.Consts

var ACCESS_TOKEN = ""

class AuthRepositoryImpl(private val localAuthProvider: LocalAuthProvider): AuthRepository {

    override suspend fun login(login: String, secret: String): DataResult {
        return if (login == "demo" && secret == "12345") {
            val token = "AAA_BBB_CCC_DDD"
            ACCESS_TOKEN = token
            localAuthProvider.saveToken(token)
            DataResult.Success(Consts.Result.OK)
        } else {
            DataResult.Error(Throwable(Consts.Error.LOGIN_PASSWORD_WRONG))
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
        localAuthProvider.removeToken()
    }
}