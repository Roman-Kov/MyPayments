package com.rojer_ko.mypayments.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponse(
	@SerializedName("success")
	val success: Boolean,
	@SerializedName("response")
	val response: ResponseDTO?,
	@SerializedName("error")
	val error: ErrorDTO?
)