package com.rojer_ko.mypayments.domain.interactor

import com.rojer_ko.mypayments.data.model.PaymentsResponseDTO
import com.rojer_ko.mypayments.domain.contracts.PaymentsRepository
import com.rojer_ko.mypayments.domain.model.DataResult
import com.rojer_ko.mypayments.domain.model.Payments
import com.rojer_ko.mypayments.presentation.contracts.PaymentsInteractor
import com.rojer_ko.mypayments.utils.Consts

class PaymentsInteractorImpl(private val repository: PaymentsRepository) : PaymentsInteractor {
    override suspend fun getPayments(): DataResult<List<Payments>> {
        return when (val result = repository.getPayments()) {
            is DataResult.Success<List<PaymentsResponseDTO>> -> {
                val payments = PaymentsConverter.normalize(result.data)
                DataResult.Success(payments)
            }
            is DataResult.Error -> {
                result
            }
            else -> DataResult.Error(Throwable(Consts.Error.BAD_RESPONSE))
        }
    }
}