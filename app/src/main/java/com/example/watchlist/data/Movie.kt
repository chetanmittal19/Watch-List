package com.example.watchlist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

//https://developers.themoviedb.org/3/discover/movie-discover
//https://api.themoviedb.org/3/discover/movie?api_key=fd2e2649af1655e26b1049940615a98b
// &language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1
@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    val id: Long,

    val title: String,

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val posterPath: String,

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    val backdropPath: String,

    val overview: String,

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: Date
)