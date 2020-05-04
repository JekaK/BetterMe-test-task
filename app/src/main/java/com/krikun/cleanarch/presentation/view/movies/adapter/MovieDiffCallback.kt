package com.krikun.cleanarch.presentation.view.movies.adapter

import androidx.recyclerview.widget.DiffUtil
import com.krikun.domain.entity.Entity

class MovieDiffCallback : DiffUtil.ItemCallback<Entity.Movie>() {

    private val mOldList: List<Entity.Movie>? = null

    private val mNewList: List<Entity.Movie>? = null

    override fun areItemsTheSame(oldItem: Entity.Movie, newItem: Entity.Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Entity.Movie, newItem: Entity.Movie): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: Entity.Movie, newItem: Entity.Movie): Any? =
        if (oldItem.id == newItem.id &&
            oldItem.title == newItem.title &&
            oldItem.overview == newItem.overview
        ) {
            oldItem
        } else {
            newItem
        }
}