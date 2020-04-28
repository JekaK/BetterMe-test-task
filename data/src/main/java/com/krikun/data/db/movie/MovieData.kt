package com.krikun.data.db.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class MovieData {

    @Entity(tableName = "movie_table")
    data class Movie(
        val adult: Boolean,
        val backdrop_path: String,
        val genre_ids: List<Int>,
        @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id: Long,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
    ) : MovieData()

}