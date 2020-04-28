package com.krikun.presentation.utils

import com.google.gson.Gson
import retrofit2.Response

/*_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_API_EXTENSIONS_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_*/

inline fun <T : Any, reified ErrorType : Any> Response<T>.castError(): ErrorType? {
    val gson = Gson()
    return try {
        gson.fromJson(errorBody()?.string(), ErrorType::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


enum class HttpCode(val code: Int) {
    SUCCESS(200),
    ERROR_SERVER(500),
}

fun <T> Response<T>.handleResponse(
    onSuccess: (response: Response<T>) -> Unit,
    onError: (response: Response<T>) -> Unit
) {
    when (this.code()) {
        HttpCode.SUCCESS.code -> onSuccess(this)
        else -> onError(this)
    }
}