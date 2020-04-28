package com.krikun.presentation.view.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.krikun.presentation.base.coroutines.CoroutineLifecycleAwareScope

abstract class BaseActivity<ViewBindingType : ViewDataBinding> : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutRes: Int
    protected abstract val currentFragment: Fragment?
    protected lateinit var binding: ViewBindingType

    //coroutines
    protected val coroutineScope: CoroutineLifecycleAwareScope by lazy {
        CoroutineLifecycleAwareScope(
            lifecycle
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
    }

}