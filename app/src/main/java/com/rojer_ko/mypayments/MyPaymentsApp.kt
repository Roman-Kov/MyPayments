package com.rojer_ko.mypayments

import android.app.Application
import com.rojer_ko.mypayments.di.appModule
import com.rojer_ko.mypayments.di.authModule
import com.rojer_ko.mypayments.di.paymentsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyPaymentsApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyPaymentsApp)
            modules(listOf(appModule, authModule, paymentsModule))
        }
    }
}