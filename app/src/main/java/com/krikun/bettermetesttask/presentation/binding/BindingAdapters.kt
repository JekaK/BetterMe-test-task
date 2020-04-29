package com.krikun.bettermetesttask.presentation.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.krikun.bettermetesttask.presentation.utils.gone
import com.krikun.bettermetesttask.presentation.utils.visible

@BindingAdapter("isVisible")
fun bindIsVisible(view: View, isVisible: Boolean?) {
    if (isVisible == null || !isVisible) {
        view.gone()
    } else {
        view.visible()
    }
}
