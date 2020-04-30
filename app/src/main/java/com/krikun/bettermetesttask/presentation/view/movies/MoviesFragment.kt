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
import io.reactivex.disposables.CompositeDisposable
import ir.hosseinabbasi.data.common.extension.applyIoScheduler
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoviesFragment : BaseFragment<FragmentMoviesBinding>(), SwipeRefreshLayout.OnRefreshListener {
    private val viewModel: MoviesViewModel by viewModel()

    private val adapter: MoviesPagedAdapter by lazy {
        MoviesPagedAdapter()
    }
    override val layoutRes: Int
        get() = R.layout.fragment_movies

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

    @SuppressLint("CheckResult")
    private fun initRx() {
        adapter.movieAddItemClickEvent.applyIoScheduler().subscribe { it ->
            viewModel.addMovieToFav(it) {
                activity?.runOnUiThread {
                    Toast.makeText(
                        activity,
                        getString(R.string.added_to_favourite),
                        Toast.LENGTH_SHORT
                    ).show()
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
        observe(viewModel.moviesLiveData, ::showMovies)
        observe(viewModel.errorData, ::showError)
    }

    private fun initView() {
        srMovies.isRefreshing = false
        srMovies.setOnRefreshListener(this)
        rvMovies.adapter = adapter
    }

    private fun showMovies(movies: ResultState<PagedList<Entity.Movie>>) {
        when (movies) {
            is ResultState.Success -> {
                viewModel.isLoading.set(false)
                viewModel.isEmpty.set(false)
                adapter.submitList(movies.data)
                srMovies.isRefreshing = false
            }
            is ResultState.Error -> {
                viewModel.isLoading.set(false)
                viewModel.isEmpty.set(false)
                Toast.makeText(activity, movies.throwable.message, Toast.LENGTH_SHORT).show()
                adapter.submitList(movies.data)
            }
            is ResultState.Loading -> {
                if (movies.data.isEmpty()) {
                    viewModel.isEmpty.set(true)
                } else {
                    viewModel.isEmpty.set(false)
                }
                adapter.submitList(movies.data)
            }
            is ResultState.Empty -> {
                viewModel.isEmpty.set(true)
                viewModel.isLoading.set(false)
            }
        }
    }


    private fun showError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
        viewModel.isLoading.set(false)
        srMovies.isRefreshing = false
    }

    override fun onRefresh() {
        viewModel.getMovies()
    }

}