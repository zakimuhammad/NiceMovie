package com.zaki.nicemovie.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zaki.nicemovie.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: Movie): Long

    @Query("SELECT * FROM movies")
    fun getAllMovie(): LiveData<List<Movie>>

    @Delete
    suspend fun deleteMovie(movie: Movie)
}