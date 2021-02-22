package com.rojer_ko.mypayments.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rojer_ko.mypayments.data.provider.AuthProvider
import com.rojer_ko.mypayments.data.provider.AuthProviderImpl
import com.rojer_ko.mypayments.data.provider.LocalAuthProvider
import com.rojer_ko.mypayments.data.provider.LocalAuthProviderImpl
import com.rojer_ko.mypayments.data.repository.AuthRepositoryImpl
import com.rojer_ko.mypayments.data.retrofit.*
import com.rojer_ko.mypayments.domain.contracts.AuthRepository
import com.rojer_ko.mypayments.presentation.viewmodel.AuthViewModel
import com.rojer_ko.mypayments.presentation.viewmodel.PaymentsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TOKEN_PREFERENCES = "tokenPreferences"
private val interceptorsList = mutableListOf(
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    },
    AppInterceptor()
)

private fun getOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .also { builder ->
            interceptorsList.forEach {
                builder.addInterceptor(it)
            }
        }
        .build()
}

private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL_LOCATION)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
}

private fun getApi(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

val appModule = module {
    single { getOkHttpClient() }
    single { getRetrofit(get()) }
    single { getApi(get()) }
    single { androidApplication().getSharedPreferences(TOKEN_PREFERENCES, Context.MODE_PRIVATE) }
    single<LocalAuthProvider> { LocalAuthProviderImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<AuthProvider> { AuthProviderImpl(get()) }
}
val authModule = module {
    viewModel { AuthViewModel(get()) }
}
val paymentsModule = module {
    viewModel { PaymentsViewModel(get()) }
}
