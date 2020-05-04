package com.krikun.cleanarch.presentation.view.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.krikun.cleanarch.R
import com.krikun.cleanarch.databinding.ItemMovieBinding
import com.krikun.domain.entity.Entity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

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

    override fun onBindViewHolder(holder: DataHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            //Can update any kind of data and item views
            //For this case I don't need update view in item but potentially now how to do it
            val movie = payloads.find { (it as Entity.Movie).id == getItemId(position) }
            movie?.let {
                val newValue = getItem(position)?.apply {
                    title = (movie as Entity.Movie).title
                }
                newValue?.let { it1 -> holder.bind(it1) }
            }
        }
    }
}