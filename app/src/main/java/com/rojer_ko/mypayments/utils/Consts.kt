package com.rojer_ko.mypayments.utils

sealed class Consts {

    class Result {
        companion object {

            const val OK = "ok"
        }
    }

    enum class Error(val text: String){
        LOGIN_PASSWORD_WRONG ("Login or password wrong"),
        TOKEN_IS_BLANK ("Token is blank"),
        BAD_RESPONSE ("Bad response"),
        NETWORK_UNAVAILABLE ("Network unavailable"),
        UNKNOWN ("Unknown error")
    }
}