package com.rojer_ko.mypayments.data.provider

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.rojer_ko.mypayments.data.model.BaseAuthResponse
import com.rojer_ko.mypayments.data.retrofit.ApiService
import com.rojer_ko.mypayments.domain.model.DataResult
import com.rojer_ko.mypayments.utils.Consts

class AuthProviderImpl(private val api: ApiService) : AuthProvider {

    private val gson = GsonBuilder().setLenient().create()

    override suspend fun login(login: String, secret: String): DataResult<String> {
        val response = api.getTokenAsync(login, secret)
        return if (response.isSuccessful) {
            try {
                val baseResponse = gson.fromJson(response.body(), BaseAuthResponse::class.java)
                when (baseResponse.success) {
                    true -> {
                        baseResponse.response?.let {
                            DataResult.Success(it.token)
                        } ?: DataResult.Error(Throwable(Consts.Error.BAD_RESPONSE))
                    }
                    false -> DataResult.Error(Throwable(Consts.Error.LOGIN_PASSWORD_WRONG))
                }
            } catch (e: JsonSyntaxException) {
                Log.e("Api", Exception(e).message.toString())
                DataResult.Error(Throwable(Consts.Error.BAD_RESPONSE))
            }
        } else {
            DataResult.Error(Throwable(Consts.Error.BAD_RESPONSE))
        }
    }
}