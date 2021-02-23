package com.rojer_ko.mypayments.data.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class BasePaymentsResponse(
	@SerializedName("success")
	val success: Boolean,
	@SerializedName("response")
	val response: List<PaymentsResponseDTO>?,
	@SerializedName("error")
	val error: ErrorDTO?
)