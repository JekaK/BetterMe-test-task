package com.krikun.data.extension

import com.google.gson.Gson
import retrofit2.Response

inline fun <T : Any, reified ErrorType : Any> Response<T>.castError(): ErrorType? {
    val gson = Gson()
    return try {
        gson.fromJson(errorBody()?.string(), ErrorType::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}