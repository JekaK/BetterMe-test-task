package com.krikun.bettermetesttask.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.krikun.bettermetesttask.R
import com.krikun.bettermetesttask.presentation.base.activity.BaseActivity
import com.krikun.bettermetesttask.presentation.extensions.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private var currentNavController: LiveData<NavController>? = null
    private val mainViewModel: MainViewModel by viewModel()

    override val layoutRes: Int
        get() = R.layout.activity_main
    override val currentFragment: Fragment?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        binding.viewModel = mainViewModel
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds =
            listOf(R.navigation.nav_movie, R.navigation.nav_fav)

        val controller = bnv_main.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.container,
            intent = intent
        )
        currentNavController = controller
    }
}
