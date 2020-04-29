package com.krikun.bettermetesttask.presentation.view.favourite

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.krikun.bettermetesttask.R
import com.krikun.bettermetesttask.databinding.FragmentFavouriteBinding
import com.krikun.bettermetesttask.presentation.base.fragment.BaseFragment
import com.krikun.bettermetesttask.presentation.extensions.observe
import com.krikun.bettermetesttask.presentation.view.favourite.adapter.FavMoviesPagedAdapter
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import ir.hosseinabbasi.data.common.extension.applyIoScheduler
import kotlinx.android.synthetic.main.fragment_favourite.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: FavouriteViewModel by viewModel()
    private var isLoading = false
    private val adapter: FavMoviesPagedAdapter by lazy {
        FavMoviesPagedAdapter()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_favourite

    private fun showMovies(movies: ResultState<PagedList<Entity.Movie>>) {
        when (movies) {
            is ResultState.Success -> {
                hideLoading()
                adapter.submitList(movies.data)
            }
            is ResultState.Error -> {
                hideLoading()
                Toast.makeText(activity, movies.throwable.message, Toast.LENGTH_SHORT).show()
                adapter.submitList(movies.data)
            }
            is ResultState.Loading -> {
                adapter.submitList(movies.data)
            }
        }
        isLoading = false
        srFav.isRefreshing = false
    }


    override fun onViewCreated() {
        binding.viewModel = viewModel
        initView()
        observe(viewModel.moviesLiveData, ::showMovies)
        viewModel.getMovies()
    }

    @SuppressLint("CheckResult")
    private fun initView() {
        srFav.isRefreshing = true
        srFav.setOnRefreshListener(this)
        rvFavourite.adapter = adapter

        adapter.movieDeleteItemClickEvent.applyIoScheduler().subscribe { it ->
            viewModel.deleteMovie(it) {
                //Can do some fancy stuff
            }
        }
        showLoading()
    }

    override fun onRefresh() {
        viewModel.getMovies()
    }
}