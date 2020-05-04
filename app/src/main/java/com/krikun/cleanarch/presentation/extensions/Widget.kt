package com.krikun.cleanarch.presentation.extensions

import android.view.View

/*_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_VIEW_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_*/

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun <T : View?> T.postSelf(action: T.() -> Unit) = this?.post { errorSafety(action = { action() }) }

inline fun errorSafety(
    onError: (e: Exception) -> Unit = { it.printStackTrace() },
    action: () -> Unit
) {
    try {
        action()
    } catch (e: Exception) {
        onError(e)
    }
}

