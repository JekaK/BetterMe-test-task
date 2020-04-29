package com.krikun.bettermetesttask.presentation.view.movies

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.paging.PagedList
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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


class MoviesFragment : BaseFragment<FragmentMoviesBinding>(), SwipeRefreshLayout.OnRefreshListener {
    private val viewModel: MoviesViewModel by viewModel()
    private val adapter: MoviesPagedAdapter by lazy {
        MoviesPagedAdapter()
    }
    private var isLoading = false

    override val layoutRes: Int
        get() = R.layout.fragment_movies

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
        srMovies.isRefreshing = false
    }

    override fun onViewCreated() {
        binding.viewModel = viewModel
        initView()
        observe(viewModel.moviesLiveData, ::showMovies)
        viewModel.getMovies()
    }

    @SuppressLint("CheckResult")
    private fun initView() {
        srMovies.isRefreshing = true
        srMovies.setOnRefreshListener(this)
        rvMovies.adapter = adapter

        adapter.movieAddItemClickEvent.applyIoScheduler().subscribe { it ->
            viewModel.addMovieToFav(it) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "Done", Toast.LENGTH_SHORT).show()
                }
            }
        }

        adapter.movieShareItemClickEvent.applyIoScheduler().subscribe { it ->
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, it.title + "\n " + it.overview)
            sendIntent.type = "text/plain"
            activity?.runOnUiThread {
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

        }
        showLoading()
    }

    override fun onRefresh() {
        viewModel.getMovies()
    }

}