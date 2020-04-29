package com.krikun.bettermetesttask.presentation.view.movies

import com.krikun.bettermetesttask.R
import com.krikun.bettermetesttask.databinding.FragmentMoviesBinding
import com.krikun.bettermetesttask.presentation.base.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {
    private val viewModel: MoviesViewModel by viewModel()

    override val layoutRes: Int
        get() = R.layout.fragment_movies

    override fun onViewCreated() {
        binding.viewModel = viewModel
    }
}