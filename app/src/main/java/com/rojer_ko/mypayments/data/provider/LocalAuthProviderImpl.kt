package com.rojer_ko.mypayments.data.provider

import android.content.SharedPreferences

class LocalAuthProviderImpl(private val tokenPrefs: SharedPreferences): LocalAuthProvider {

    companion object {

        private const val ACCESS_TOKEN_PREF = "access_token"
    }

    private val editor = tokenPrefs.edit()
    override suspend fun saveToken(token: String) {
        if (token.isNotBlank()) {
            editor.putString(ACCESS_TOKEN_PREF, token)
            editor.apply()
        }
    }

    override suspend fun getToken(): String {
        return tokenPrefs.getString(ACCESS_TOKEN_PREF, "") ?: ""
    }

    override suspend fun removeToken() {
        editor.clear()
        editor.apply()
    }
}