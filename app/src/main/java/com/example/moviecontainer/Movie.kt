package com.example.moviecontainer

data class MovieDetails(
    val Title: String,
    val Genre: String,
    val Actors: String,
    val Director: String,
    val Released: String,
    val imdbRating: String,
    val Poster: String,
    val Country: String,
    val Plot: String,
    val Response:String,
    val Runtime:String,
    val Awards:String,
)

data class Movie(
    val Title:String,
    val Year:String,
    val Poster:String,
    val imdbID:String
)

data class MoviesResponse(
    val Search:List<Movie>,
    val Response: String,
    val totalResults: String
)
