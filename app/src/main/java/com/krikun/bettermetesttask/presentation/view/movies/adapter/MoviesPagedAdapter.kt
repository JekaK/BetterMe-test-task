package com.krikun.bettermetesttask.presentation.view.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.krikun.bettermetesttask.R
import com.krikun.bettermetesttask.databinding.ItemMovieBinding
import com.krikun.domain.entity.Entity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesPagedAdapter :
    PagedListAdapter<Entity.Movie, MoviesPagedAdapter.DataHolder>(MovieDiffCallback()) {

    private val onMovieAddItemClickSubject = PublishSubject.create<Entity.Movie>()
    val movieAddItemClickEvent: Observable<Entity.Movie> = onMovieAddItemClickSubject

    private val onMovieShareItemClickSubject = PublishSubject.create<Entity.Movie>()
    val movieShareItemClickEvent: Observable<Entity.Movie> = onMovieShareItemClickSubject

    inner class DataHolder(private var itemAlbumBinding: ItemMovieBinding) : RecyclerView.ViewHolder
        (itemAlbumBinding.root) {

        fun bind(movie: Entity.Movie) {
            itemAlbumBinding.movie = movie
            itemAlbumBinding.root.btnAdd.setOnClickListener {
                movie.let {
                    onMovieAddItemClickSubject.onNext(movie)
                }
            }
            itemAlbumBinding.root.btnFav.setOnClickListener {
                movie.let {
                    onMovieShareItemClickSubject.onNext(movie)
                }
            }
            itemAlbumBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val bind: ItemMovieBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_movie, parent, false
        ) as ItemMovieBinding

        return DataHolder(bind)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(movie) }
    }
}