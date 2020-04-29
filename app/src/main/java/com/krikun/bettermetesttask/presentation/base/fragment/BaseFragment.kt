package com.krikun.bettermetesttask.presentation.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import com.krikun.bettermetesttask.presentation.base.coroutines.CoroutineLifecycleAwareScope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlin.properties.Delegates

abstract class BaseFragment< ViewBindingType : ViewDataBinding> :
    Fragment() {
    @get:LayoutRes
    abstract val layoutRes: Int
//    protected var host: ActivityType? = null
    protected lateinit var binding: ViewBindingType

    // Coroutines
    val coroutineScope: CoroutineLifecycleAwareScope by lazy {
        CoroutineLifecycleAwareScope(
            lifecycle
        )
    }

    // Lifecycle
    val viewLifecycle: LifecycleRegistry by lazy { LifecycleRegistry(this) }

    // Session time
    private var startTime: Long by Delegates.notNull()
    open val initTime = 200


    // Defines whether auto handling message (showing toast) enabled
    protected var handleMessage = true

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)
//        host = try {
//            context as ActivityType
//        } catch (e: ClassCastException) {
//            throw error("Activity type doesn't much!")
//        }
    }

    override fun onDetach() {
        super.onDetach()
//        host = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTime = System.currentTimeMillis()
        //call custom onCreate
        onCreate(savedInstanceState == null)

        // Lifecycle state
        viewLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onActivityCreated()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call abstract onViewCreated
        onViewCreated()

        // Lifecycle state
        viewLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Lifecycle state
        viewLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyRx()
        // Lifecycle state
        viewLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    open fun onCreate(initial: Boolean) {}

    open fun onActivityCreated() {}

    abstract fun onViewCreated()

    /*_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_RX_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_*/

    private val subscriptionHandler: CompositeDisposable by lazy { CompositeDisposable() }
    protected val subscriptionHandler2: CompositeDisposable by lazy { CompositeDisposable() }

    protected fun clearRx(handler: CompositeDisposable = subscriptionHandler) {
        handler.clear()
    }

    protected fun clearAll() {
        subscriptionHandler.clear()
        subscriptionHandler2.clear()
    }

    open fun destroyRx() {
        listOf(subscriptionHandler, subscriptionHandler2).forEach { clearRx(it) }
    }

    fun Disposable.untilDestroy(handler: CompositeDisposable = subscriptionHandler) =
        handler.add(this)

}