package com.rojer_ko.mypayments.presentation.contracts

import com.rojer_ko.mypayments.domain.model.DataResult

interface PaymentsInteractor {

    suspend fun getPayments(): DataResult
}