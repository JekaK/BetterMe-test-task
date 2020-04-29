package com.krikun.bettermetesttask.presentation.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.krikun.bettermetesttask.R
import com.krikun.bettermetesttask.presentation.extensions.gone
import com.krikun.bettermetesttask.presentation.extensions.postSelf
import com.krikun.bettermetesttask.presentation.extensions.visible
import jp.wasabeef.glide.transformations.GrayscaleTransformation


@BindingAdapter("isVisible")
fun bindIsVisible(view: View, isVisible: Boolean?) {
    if (isVisible == null || !isVisible) {
        view.gone()
    } else {
        view.visible()
    }
}


/*_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_IMAGEVIEW_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_*/

@BindingAdapter(
    value = ["imageUrl", "placeholder", "error", "colorless", "imageOnPost"],
    requireAll = false
)
fun bindImageFromUrl(
    view: ImageView,
    imageUrl: String?,
    placeholder: Drawable?,
    error: Drawable?,
    colorless: Boolean? = false,
    imageOnPost: Boolean? = true
) {

    fun action() {
        Glide.with(view.context)
            .load(imageUrl)
            .apply(RequestOptions().apply {
                placeholder?.let { this.placeholder(it) }
                error?.let { this.error(it) }
                colorless?.takeIf { it }?.let {
                    this.apply(RequestOptions.bitmapTransform(GrayscaleTransformation()))
                }
            })
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }

    if (imageOnPost == true) {
        view.postSelf { action() }
    } else {
        action()
    }
}

@BindingAdapter("srcGif")
fun setSrcGif(view: ImageView, @DrawableRes drawable: Int) {
    Glide.with(view.context)
        .load(view.context.getDrawable(R.drawable.empty))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}


