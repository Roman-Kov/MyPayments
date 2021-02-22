package com.rojer_ko.mypayments.data.retrofit

import com.rojer_ko.mypayments.data.repository.ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class AppInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
            //.header("HID", Settings.Secure.ANDROID_ID)
            .header("app-key", "12345")
            .header("v", "1")
            .apply {
                if (ACCESS_TOKEN.isNotBlank()) {
                    header("Authorization", "Bearer $ACCESS_TOKEN")
                }
            }
        val finalRequest = requestBuilder.build()
        val logging = HttpLoggingInterceptor.Logger.DEFAULT
        logging.log(finalRequest.headers().toString())
        return chain.proceed(finalRequest)
    }
}