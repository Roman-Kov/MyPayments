package com.rojer_ko.mypayments.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rojer_ko.mypayments.domain.contracts.AuthRepository
import com.rojer_ko.mypayments.domain.model.DataResult
import com.rojer_ko.mypayments.presentation.contracts.PaymentsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentsViewModel(
    private val authRepository: AuthRepository,
    private val paymentsInteractor: PaymentsInteractor
) : ViewModel() {

    private val _paymentsResult: MutableLiveData<DataResult> = MutableLiveData()
    val paymentsResult: LiveData<DataResult> = _paymentsResult

    fun getPayments() {
        _paymentsResult.value = DataResult.Process
        viewModelScope.launch(Dispatchers.IO) {
            _paymentsResult.postValue(paymentsInteractor.getPayments())
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.logout()
        }
    }
}