package com.krikun.bettermetesttask.presentation.view.movies.adapter

import android.view.LayoutInflater
import android.view.View
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

class MoviesPagedAdapter :
    PagedListAdapter<Entity.Movie, MoviesPagedAdapter.DataHolder>(MovieDiffCallback()) {

    private val onAlbumItemClickSubject = PublishSubject.create<Entity.Movie>()
    val albumItemClickEvent: Observable<Entity.Movie> = onAlbumItemClickSubject

    inner class DataHolder(private var itemAlbumBinding: ItemMovieBinding) : RecyclerView.ViewHolder
        (itemAlbumBinding.root), View.OnClickListener {

        fun bind(movie: Entity.Movie) {
            itemAlbumBinding.movie = movie
            itemAlbumBinding.root.setOnClickListener(this)
            itemAlbumBinding.executePendingBindings()
        }

        override fun onClick(view: View) {
            val album = getItem((adapterPosition))
            album?.let {
                val product: Entity.Movie = album
                onAlbumItemClickSubject.onNext(product)
            }
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