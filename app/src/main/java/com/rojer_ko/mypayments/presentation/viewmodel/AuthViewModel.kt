package com.rojer_ko.mypayments.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rojer_ko.mypayments.domain.contracts.AuthRepository
import com.rojer_ko.mypayments.domain.model.DataResult
import com.rojer_ko.mypayments.utils.Consts
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _formState: MutableLiveData<Boolean> = MutableLiveData(false)
    val formState: LiveData<Boolean> = _formState
    private val _loginState: MutableLiveData<DataResult<String>> = MutableLiveData()
    val loginState: LiveData<DataResult<String>> = _loginState
    private val _tokenState: MutableLiveData<DataResult<String>> = MutableLiveData()
    val tokenState: LiveData<DataResult<String>> = _tokenState

    private val loginExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("AuthViewModel", exception.message.toString())
        _loginState.postValue(DataResult.Error(Throwable(Consts.Error.UNKNOWN.text)))
    }

    private val tokenExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("AuthViewModel", exception.message.toString())
        _tokenState.postValue(DataResult.Error(Throwable(Consts.Error.UNKNOWN.text)))
    }

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

        viewModelScope.launch(Dispatchers.IO + loginExceptionHandler) {
            _loginState.postValue(authRepository.login(login, secret))
        }
    }

    fun getToken() {
        viewModelScope.launch(Dispatchers.IO + tokenExceptionHandler) {
            _tokenState.postValue(authRepository.getToken())
        }
    }
}