package com.krikun.bettermetesttask.presentation.view.movies

import android.widget.Toast
import androidx.paging.PagedList
import com.krikun.bettermetesttask.R
import com.krikun.bettermetesttask.databinding.FragmentMoviesBinding
import com.krikun.bettermetesttask.presentation.base.fragment.BaseFragment
import com.krikun.bettermetesttask.presentation.extensions.observe
import com.krikun.bettermetesttask.presentation.view.movies.adapter.MoviesPagedAdapter
import com.krikun.domain.common.ResultState
import com.krikun.domain.entity.Entity
import ir.hosseinabbasi.data.common.extension.applyIoScheduler
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {
    private val viewModel: MoviesViewModel by viewModel()
    private val adapter: MoviesPagedAdapter by lazy {
        MoviesPagedAdapter()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_movies

    private fun showMovies(movies: ResultState<PagedList<Entity.Movie>>) {
        when (movies) {
            is ResultState.Success -> {
//                hideLoading()
                adapter.submitList(movies.data)
            }
            is ResultState.Error -> {
//                hideLoading()
                Toast.makeText(activity, movies.throwable.message, Toast.LENGTH_SHORT).show()
                adapter.submitList(movies.data)
            }
            is ResultState.Loading -> {
                adapter.submitList(movies.data)
            }
        }
//        isLoading = false
//        fragmentHomeSwp.isRefreshing = false
    }

    override fun onViewCreated() {
        binding.viewModel = viewModel
        initView()
        observe(viewModel.moviesLiveData, ::showMovies)
        viewModel.getMovies()
    }

    private fun initView() {
        rvMovies.adapter = adapter
        adapter.albumItemClickEvent.applyIoScheduler().subscribe { it ->
//            viewModel.deleteAlbum(it)
        }
    }

}