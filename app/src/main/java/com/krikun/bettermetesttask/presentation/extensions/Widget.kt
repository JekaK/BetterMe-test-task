package com.krikun.bettermetesttask.presentation.extensions

import android.view.View

fun <T : View?> T.postSelf(action: T.() -> Unit) = this?.post { errorSafety(action = { action() }) }

inline fun errorSafety(onError: (e: Exception) -> Unit = { it.printStackTrace() }, action: () -> Unit) {
    try {
        action()
    } catch (e: Exception) {
        onError(e)
    }
}