package com.krikun.bettermetesttask.presentation.view.movies.adapter

import androidx.recyclerview.widget.DiffUtil
import com.krikun.domain.entity.Entity

class MovieDiffCallback : DiffUtil.ItemCallback<Entity.Movie>() {

    override fun areItemsTheSame(oldItem: Entity.Movie, newItem: Entity.Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Entity.Movie, newItem: Entity.Movie): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: Entity.Movie, newItem: Entity.Movie): Any? {
        return super.getChangePayload(oldItem, newItem)
    }
}