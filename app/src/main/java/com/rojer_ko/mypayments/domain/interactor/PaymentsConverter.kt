package com.rojer_ko.mypayments.domain.interactor

import com.rojer_ko.mypayments.data.model.PaymentsResponseDTO
import com.rojer_ko.mypayments.domain.model.Payments
import com.rojer_ko.mypayments.utils.toDateString

class PaymentsConverter {
    companion object {

        private const val MAX_DESC_LENGTH = 12
        private const val MAX_CURR_LENGTH = 4
        private const val ROUNDING_CHARACTERS = 2
        private const val DATE_FORMAT = "dd.MM.yy hh:mm"
        private const val UNKNOWN_OPERATION = "unknown operation"
        private const val UNKNOWN_AMOUNT = "unknown amount"
        private const val UNKNOWN_CURRENCY = "??"
        private const val UNKNOWN_DATE = "unknown date"

        fun normalize(payments: List<PaymentsResponseDTO>): List<Payments> {
            val paymentsResult = mutableListOf<Payments>()

            for (payment in payments) {
                val description = payment.description?.let {
                    it.subSequence(0, getMaxLength(it, MAX_DESC_LENGTH)).toString()
                }.takeIf {
                    !it.isNullOrBlank()
                } ?: UNKNOWN_OPERATION
                val amount: String = try {
                    payment.amount?.let {
                        String.format("%.$ROUNDING_CHARACTERS" + "f", it.toDouble())
                    } ?: UNKNOWN_AMOUNT
                } catch (e: Exception) {
                    UNKNOWN_AMOUNT
                }.toString()
                val currency = payment.currency?.let {
                    it.subSequence(0, getMaxLength(it, MAX_CURR_LENGTH)).toString()
                }.takeIf {
                    !it.isNullOrBlank()
                } ?: UNKNOWN_CURRENCY
                val createdDate: String = try {
                    payment.createdDate?.toLong()?.toDateString(DATE_FORMAT) ?: UNKNOWN_DATE
                } catch (e: Exception) {
                    UNKNOWN_DATE
                }.toString()

                paymentsResult.add(Payments(description, amount, currency, createdDate))
            }
            return paymentsResult
        }

        private fun getMaxLength(string: String, maxLength: Int): Int {
            return string.length.takeIf {
                it < maxLength
            } ?: maxLength
        }
    }
}