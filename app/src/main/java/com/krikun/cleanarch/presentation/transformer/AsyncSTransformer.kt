package com.krikun.cleanarch.presentation.transformer

import com.krikun.domain.transformer.STransformer
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.schedulers.Schedulers


class AsyncSTransformer<T> : STransformer<T>() {

    override fun apply(upstream: Single<T>): SingleSource<T> = upstream.subscribeOn(Schedulers.io())
}