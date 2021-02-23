package com.rojer_ko.mypayments.presentation.contracts

import com.rojer_ko.mypayments.domain.model.DataResult
import com.rojer_ko.mypayments.domain.model.Payments

interface PaymentsInteractor {

    suspend fun getPayments(): DataResult<List<Payments>>
}