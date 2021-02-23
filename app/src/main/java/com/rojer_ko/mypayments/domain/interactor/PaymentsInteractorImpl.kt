package com.rojer_ko.mypayments.domain.interactor

import com.rojer_ko.mypayments.data.model.PaymentsResponseDTO
import com.rojer_ko.mypayments.domain.contracts.PaymentsRepository
import com.rojer_ko.mypayments.domain.model.DataResult
import com.rojer_ko.mypayments.presentation.contracts.PaymentsInteractor
import com.rojer_ko.mypayments.utils.Consts

class PaymentsInteractorImpl(private val repository: PaymentsRepository) : PaymentsInteractor {
    override suspend fun getPayments(): DataResult {
        return when (val result = repository.getPayments()) {
            is DataResult.Success<*> -> {
                val payments = PaymentsConverter.normalize(result.data as List<PaymentsResponseDTO>)
                DataResult.Success(payments)
            }
            is DataResult.Error -> {
                result
            }
            else -> DataResult.Error(Throwable(Consts.Error.BAD_RESPONSE))
        }
    }
}