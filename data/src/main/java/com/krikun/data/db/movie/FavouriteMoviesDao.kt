package com.krikun.data.db.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.krikun.data.db.BaseDao
import io.reactivex.Flowable

@Dao
interface FavouriteMoviesDao : BaseDao<MovieData.Movie> {

    @Query("SELECT * FROM movie_table WHERE id = :id")
    override fun select(id: Long): Flowable<MovieData.Movie>

    @Query("SELECT * FROM movie_table ORDER BY id")
    override fun selectAll(): List<MovieData.Movie>


    @Query("DELETE FROM movie_table")
    override fun truncate()

    @Transaction
    fun replace(movies: List<MovieData.Movie>) {
        val firstId: Long = movies.firstOrNull()?.id ?: run {
            0L
        }

        val lastId = movies.lastOrNull()?.id ?: run {
            Long.MAX_VALUE
        }

        deleteRange(firstId, lastId)
        insert(movies)
    }

    @Query("DELETE FROM movie_table WHERE id BETWEEN :firstId AND :lastId")
    fun deleteRange(firstId: Long, lastId: Long)

}
