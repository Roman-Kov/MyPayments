package com.rojer_ko.mypayments.presentation.converter

import com.rojer_ko.mypayments.domain.model.Payments
import com.rojer_ko.mypayments.presentation.ui.PaymentContainer

class PaymentItemConverter {
    companion object {

        fun convertToContainer(payments: List<Payments>): List<PaymentContainer> {
            val paymentContainers = mutableListOf<PaymentContainer>()

            for (payment in payments) {
                paymentContainers.add(
                    PaymentContainer(
                        payment.description,
                        payment.amount,
                        payment.currency,
                        payment.createdDate
                    )
                )
            }
            return paymentContainers
        }
    }
}