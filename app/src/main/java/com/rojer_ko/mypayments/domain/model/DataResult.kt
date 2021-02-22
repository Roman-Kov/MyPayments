package com.rojer_ko.mypayments.domain.model

sealed class DataResult {

    //данные получены
    data class Success<out T>(val data: T): DataResult()

    //данные в процессе получения
    object Process: DataResult()

    //получена ошибка
    data class Error(val error: Throwable): DataResult()
}
