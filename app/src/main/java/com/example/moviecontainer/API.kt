package com.example.moviecontainer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//coroutines
//rx java

interface API {

    @GET("/")
    fun getMovies(@Query("s") s:String,@Query("apikey")apikey:String):Call<MoviesResponse>

    @GET("/")
    fun getMovieDetail(@Query("i") i:String,@Query("apikey") apikey: String):Call<MovieDetails>

}

