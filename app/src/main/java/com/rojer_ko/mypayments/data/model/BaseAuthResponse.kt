package com.rojer_ko.mypayments.data.model

import com.google.gson.annotations.SerializedName

data class BaseAuthResponse(
	@SerializedName("success")
	val success: Boolean,
	@SerializedName("response")
	val response: TokenResponseDTO?,
	@SerializedName("error")
	val error: ErrorDTO?
)