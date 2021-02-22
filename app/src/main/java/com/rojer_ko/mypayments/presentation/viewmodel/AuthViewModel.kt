package com.rojer_ko.mypayments.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rojer_ko.mypayments.domain.contracts.AuthRepository
import com.rojer_ko.mypayments.domain.model.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _formState: MutableLiveData<Boolean> = MutableLiveData(false)
    val formState: LiveData<Boolean> = _formState
    private val _loginState: MutableLiveData<DataResult> = MutableLiveData()
    val loginState: LiveData<DataResult> = _loginState
    private val _tokenState: MutableLiveData<DataResult> = MutableLiveData()
    val tokenState: LiveData<DataResult> = _tokenState

    fun checkForm(fieldsValues: List<String>) {
        var isFormFilled = true
        for (item in fieldsValues) {
            isFormFilled = isFormFilled && item.isNotBlank()
        }
        _formState.value = isFormFilled
    }

    fun login(login: String, secret: String) {
        _formState.value = false
        _loginState.value = DataResult.Process

        viewModelScope.launch(Dispatchers.IO) {
            _loginState.postValue(authRepository.login(login, secret))
        }
    }

    fun getToken() {
        viewModelScope.launch(Dispatchers.IO) {
            _tokenState.postValue(authRepository.getToken())
        }
    }
}