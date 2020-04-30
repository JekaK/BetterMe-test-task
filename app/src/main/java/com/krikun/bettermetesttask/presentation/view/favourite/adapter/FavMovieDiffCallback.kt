package com.krikun.bettermetesttask.presentation.view.favourite.adapter

import androidx.recyclerview.widget.DiffUtil
import com.krikun.domain.entity.Entity

class FavMovieDiffCallback : DiffUtil.ItemCallback<Entity.Movie>() {

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