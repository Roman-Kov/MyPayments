package com.rojer_ko.mypayments.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rojer_ko.mypayments.domain.contracts.AuthRepository
import com.rojer_ko.mypayments.domain.model.DataResult
import com.rojer_ko.mypayments.domain.model.Payments
import com.rojer_ko.mypayments.presentation.contracts.PaymentsInteractor
import com.rojer_ko.mypayments.utils.Consts
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentsViewModel(
    private val authRepository: AuthRepository,
    private val paymentsInteractor: PaymentsInteractor
) : ViewModel() {

    private val _paymentsResult: MutableLiveData<DataResult<List<Payments>>> = MutableLiveData()
    val paymentsResult: LiveData<DataResult<List<Payments>>> = _paymentsResult

    private val paymentsExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("PaymentsViewModel", exception.message.toString())
        _paymentsResult.postValue(DataResult.Error(Throwable(Consts.Error.UNKNOWN.text)))
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("PaymentsViewModel", exception.message.toString())
    }

    fun getPayments() {
        _paymentsResult.value = DataResult.Process
        viewModelScope.launch(Dispatchers.IO + paymentsExceptionHandler) {
            _paymentsResult.postValue(paymentsInteractor.getPayments())
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            authRepository.logout()
        }
    }
}