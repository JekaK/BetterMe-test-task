package com.krikun.cleanarch.presentation.view.favourite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.krikun.cleanarch.R
import com.krikun.cleanarch.databinding.ItemFavouriteBindingImpl
import com.krikun.domain.entity.Entity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

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