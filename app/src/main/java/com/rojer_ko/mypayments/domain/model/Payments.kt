package com.rojer_ko.mypayments.domain.model

data class Payments(
	val description: String,
	val amount: String,
	val currency: String,
	val createdDate: String
)