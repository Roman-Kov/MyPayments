package com.rojer_ko.mypayments.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rojer_ko.mypayments.domain.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentsViewModel(private val authRepository: AuthRepository): ViewModel() {

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.logout()
        }
    }
}