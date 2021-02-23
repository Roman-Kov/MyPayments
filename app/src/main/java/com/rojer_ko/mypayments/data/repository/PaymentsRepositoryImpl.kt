package com.rojer_ko.mypayments.data.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.rojer_ko.mypayments.data.model.BasePaymentsResponse
import com.rojer_ko.mypayments.data.model.PaymentsResponseDTO
import com.rojer_ko.mypayments.data.retrofit.ApiService
import com.rojer_ko.mypayments.data.retrofit.NetworkManager
import com.rojer_ko.mypayments.domain.contracts.PaymentsRepository
import com.rojer_ko.mypayments.domain.model.DataResult
import com.rojer_ko.mypayments.utils.Consts

class PaymentsRepositoryImpl(
    private val api: ApiService,
    private val networkManager: NetworkManager
) : PaymentsRepository {

    private val gson = GsonBuilder().setLenient().create()

    override suspend fun getPayments(): DataResult<List<PaymentsResponseDTO>> {
        if (!networkManager.isNetworkAvailable()) {
            return DataResult.Error(Throwable(Consts.Error.NETWORK_UNAVAILABLE.text))
        }
        val response = api.getPaymentsAsync(ACCESS_TOKEN)
        return if (response.isSuccessful) {
            try {
                val baseResponse = gson.fromJson(response.body(), BasePaymentsResponse::class.java)
                when (baseResponse.success) {
                    true -> {
                        DataResult.Success(baseResponse.response ?: listOf())
                    }
                    false -> DataResult.Error(Throwable(Consts.Error.LOGIN_PASSWORD_WRONG.text))
                }
            } catch (e: JsonSyntaxException) {
                Log.e("Api", Exception(e).message.toString())
                DataResult.Error(Throwable(Consts.Error.BAD_RESPONSE.text))
            }
        } else {
            DataResult.Error(Throwable(Consts.Error.BAD_RESPONSE.text))
        }
    }
}