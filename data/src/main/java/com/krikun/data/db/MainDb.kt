package com.krikun.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.krikun.data.db.movie.FavouriteMoviesDao
import com.krikun.data.db.movie.MovieData
import com.krikun.data.db.movie.MoviesDao

@TypeConverters(MovieData.IdsConverter::class)
@Database(
    entities = [MovieData.Movie::class, MovieData.FavouriteMovie::class],
    version = 3,
    exportSchema = false
)
abstract class MainDb : RoomDatabase() {
    abstract fun favouriteMoviesDao(): FavouriteMoviesDao
    abstract fun moviesDao(): MoviesDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, MainDb::class.java, "MyDatabase")
                .fallbackToDestructiveMigration()
                .build()
    }
}


