package com.rojer_ko.mypayments.utils

sealed class Consts {

    class Result {
        companion object {

            const val OK = "ok"
        }
    }

    class Error{
        companion object {

            const val LOGIN_PASSWORD_WRONG = "Login or password wrong"
            const val TOKEN_IS_BLANK = "Token is blank"
            const val BAD_RESPONSE = "Bad response"
        }
    }
}