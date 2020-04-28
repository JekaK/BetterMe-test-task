package com.krikun.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.krikun.data.db.movie.FavouriteMoviesDao

@Database(entities = [],version = 1)
abstract class MainDb:RoomDatabase() {
    abstract fun favouriteMoviesDao(): FavouriteMoviesDao
}