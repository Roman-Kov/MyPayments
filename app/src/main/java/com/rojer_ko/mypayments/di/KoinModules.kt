package com.rojer_ko.mypayments.di

import android.content.Context
import com.rojer_ko.mypayments.data.provider.LocalAuthProviderImpl
import com.rojer_ko.mypayments.data.repository.AuthRepositoryImpl
import com.rojer_ko.mypayments.domain.AuthRepository
import com.rojer_ko.mypayments.domain.LocalAuthProvider
import com.rojer_ko.mypayments.presentation.viewmodel.AuthViewModel
import com.rojer_ko.mypayments.presentation.viewmodel.PaymentsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val TOKEN_PREFERENCES = "tokenPreferences"

val appModule = module {
    single { androidApplication().getSharedPreferences(TOKEN_PREFERENCES, Context.MODE_PRIVATE) }
    single<LocalAuthProvider> { LocalAuthProviderImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}
val authModule = module {
    viewModel { AuthViewModel(get()) }
}

val paymentsModule = module {
    viewModel { PaymentsViewModel(get()) }
}
