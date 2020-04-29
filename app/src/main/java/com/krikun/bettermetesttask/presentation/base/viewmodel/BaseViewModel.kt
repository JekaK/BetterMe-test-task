package com.krikun.bettermetesttask.presentation.base.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    /*_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_RX_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_*/

    private val subscriptionHandler: CompositeDisposable by lazy { CompositeDisposable() }
    protected val subscriptionHandler2: CompositeDisposable by lazy { CompositeDisposable() }

    fun Disposable.track() {
        subscriptionHandler.add(this)
    }

    protected fun clearRx(handler: CompositeDisposable = subscriptionHandler) {
        handler.clear()
    }

    protected fun clearAllRx() {
        subscriptionHandler.clear()
        subscriptionHandler2.clear()
    }

    open fun destroyRx() {
        clearAllRx()
    }

    /*_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_COROUTINES_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_*/

    private val job: Job = SupervisorJob()
    protected val coroutineScope: CoroutineScope =
        CoroutineScope(job + Dispatchers.Main + CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() })

    /*_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_VIEW MODEL STUFF_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_*/

    override fun onCleared() {
        super.onCleared()
        destroyRx() // Unsubscribe from all rx subscriptions
        job.cancel() // Cancel all active coroutines
    }
}