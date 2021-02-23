package com.rojer_ko.mypayments.domain.contracts

import com.rojer_ko.mypayments.domain.model.DataResult
import kotlinx.coroutines.CoroutineScope

interface PaymentsRepository {

   suspend fun getPayments(): DataResult
}