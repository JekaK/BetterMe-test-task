package com.krikun.data.db.movie

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.krikun.data.db.BaseDao
import io.reactivex.Flowable

@Dao
interface MoviesDao : BaseDao<MovieData.Movie> {

    @Query("SELECT * FROM movie_table WHERE id = :id")
    override fun select(id: Long): Flowable<MovieData.Movie>

    @Query("SELECT * FROM movie_table ORDER BY id")
    override fun selectAll(): DataSource.Factory<Int, MovieData.Movie>


    @Query("DELETE FROM movie_table")
    override fun truncate()

    @Transaction
    fun replace(favouriteMovies: List<MovieData.Movie>) {
        val firstId: Long = favouriteMovies.firstOrNull()?.id ?: run {
            0L
        }

        val lastId = favouriteMovies.lastOrNull()?.id ?: run {
            Long.MAX_VALUE
        }

        deleteRange(firstId, lastId)
        insert(favouriteMovies)
    }

    @Query("DELETE FROM movie_table WHERE id BETWEEN :firstId AND :lastId")
    fun deleteRange(firstId: Long, lastId: Long)

}
