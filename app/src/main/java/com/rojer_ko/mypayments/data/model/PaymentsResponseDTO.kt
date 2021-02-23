package com.rojer_ko.mypayments.data.model

import com.google.gson.annotations.SerializedName

data class PaymentsResponseDTO(
	@SerializedName("desc") val description: String?,
	@SerializedName("amount") val amount: String?,
	@SerializedName("currency") val currency: String?,
	@SerializedName("created") val createdDate: String?
)