package com.rojer_ko.mypayments.domain.contracts

import com.rojer_ko.mypayments.data.model.PaymentsResponseDTO
import com.rojer_ko.mypayments.domain.model.DataResult

interface PaymentsRepository {

    suspend fun getPayments(): DataResult<List<PaymentsResponseDTO>>
}