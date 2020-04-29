package com.krikun.bettermetesttask.presentation.view.favourite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.krikun.bettermetesttask.R
import com.krikun.bettermetesttask.databinding.ItemFavouriteBindingImpl
import com.krikun.domain.entity.Entity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_favourite.view.*

class FavMoviesPagedAdapter :
    PagedListAdapter<Entity.Movie, FavMoviesPagedAdapter.DataHolder>(FavMovieDiffCallback()) {

    private val onMovieDeleteItemClickSubject = PublishSubject.create<Entity.Movie>()
    val movieDeleteItemClickEvent: Observable<Entity.Movie> = onMovieDeleteItemClickSubject

    inner class DataHolder(private var itemAlbumBinding: ItemFavouriteBindingImpl) :
        RecyclerView.ViewHolder
            (itemAlbumBinding.root) {

        fun bind(movie: Entity.Movie) {
            itemAlbumBinding.movie = movie
            itemAlbumBinding.root.btnAdd.setOnClickListener {
                movie.let {
                    onMovieDeleteItemClickSubject.onNext(movie)
                }
            }

            itemAlbumBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val bind: ItemFavouriteBindingImpl = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_favourite, parent, false
        ) as ItemFavouriteBindingImpl

        return DataHolder(bind)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(movie) }
    }
}