package com.krikun.cleanarch.presentation.view.favourite

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.paging.PagedList
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.krikun.cleanarch.R
import com.krikun.cleanarch.databinding.FragmentFavouriteBinding
import com.krikun.cleanarch.presentation.base.fragment.BaseFragment
import com.krikun.cleanarch.presentation.extensions.observe
import com.krikun.cleanarch.presentation.view.favourite.adapter.FavMoviesPagedAdapter
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import ir.hosseinabbasi.data.common.extension.applyIoScheduler
import kotlinx.android.synthetic.main.fragment_favourite.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: FavouriteViewModel by viewModel()
    private val adapter: FavMoviesPagedAdapter by lazy {
        FavMoviesPagedAdapter()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_favourite

    override fun onCreate(initial: Boolean) {
        super.onCreate(initial)
        if (initial) {
            initRx()
            viewModel.isLoading.set(true)
        }
    }

    override fun onViewCreated() {
        binding.viewModel = viewModel
        initView()
        viewModel.getMovies()
    }

    private fun initView() {
        srFav.isRefreshing = false
        srFav.setOnRefreshListener(this)
        rvFavourite.adapter = adapter
    }

    @SuppressLint("CheckResult")
    private fun initRx() {
        adapter.movieDeleteItemClickEvent.applyIoScheduler().subscribe { it ->
            viewModel.deleteMovie(it) {
                //Can do some fancy stuff
            }
        }
        observe(viewModel.moviesLiveData, ::showMovies)
    }

    private fun showMovies(movies: ResultState<PagedList<Entity.Movie>>) {
        when (movies) {
            is ResultState.Success -> {
                viewModel.isLoading.set(false)
                viewModel.isEmpty.set(false)
                adapter.submitList(movies.data)
                srFav.isRefreshing = false
            }
            is ResultState.Error -> {
                viewModel.isLoading.set(false)
                viewModel.isEmpty.set(false)
                Toast.makeText(activity, movies.throwable.message, Toast.LENGTH_SHORT).show()
                adapter.submitList(movies.data)
            }
            is ResultState.Loading -> {
                viewModel.isEmpty.set(false)
                adapter.submitList(movies.data)
            }
            is ResultState.Empty -> {
                viewModel.isEmpty.set(true)
                viewModel.isLoading.set(false)
            }
        }
    }

    override fun onRefresh() {
        viewModel.getMovies()
    }
}