package com.rojer_ko.mypayments.presentation.converter

import android.content.Context
import com.rojer_ko.mypayments.R
import com.rojer_ko.mypayments.utils.Consts

class ErrorStringConverter {
    companion object {

        fun convertToContainer(context: Context, errorConst: String): String {
            return when (errorConst) {
                Consts.Error.BAD_RESPONSE.text -> context.getString(R.string.data_error)
                Consts.Error.LOGIN_PASSWORD_WRONG.text -> context.getString(R.string.login_password_error)
                Consts.Error.NETWORK_UNAVAILABLE.text -> context.getString(R.string.network_unavailable_error)
                Consts.Error.TOKEN_IS_BLANK.text -> context.getString(R.string.token_blank_error)
                Consts.Error.UNKNOWN.text -> context.getString(R.string.unknown_error)
                else -> context.getString(R.string.unknown_error)
            }
        }
    }
}