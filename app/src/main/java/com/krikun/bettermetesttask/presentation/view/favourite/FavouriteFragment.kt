package com.krikun.bettermetesttask.presentation.view.favourite

import com.krikun.bettermetesttask.R
import com.krikun.bettermetesttask.databinding.FragmentFavouriteBinding
import com.krikun.bettermetesttask.presentation.base.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteFragment: BaseFragment<FragmentFavouriteBinding>() {

    private val viewModel: FavouriteViewModel by viewModel()

    override val layoutRes: Int
        get() = R.layout.fragment_favourite

    override fun onViewCreated() {
        binding.viewModel = viewModel
    }
}