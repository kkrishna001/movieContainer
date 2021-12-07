package com.example.moviecontainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), movieAdapter.OnMovieListener {

    private val BASE_URL="https://www.omdbapi.com"
    private val API_KEY="1ddee4bf"

    private lateinit var retrofitBuilder:API
    private val moviesList=ArrayList<Movie>()
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: movieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retrofitBuilder=Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
            BASE_URL).build().create(API::class.java)

        setContentView(R.layout.activity_main)

        val searchNow=findViewById<SearchView>(R.id.searchBarMovie)


        searchNow.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
            //
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!!.isNotEmpty())
                {
                    val search = newText
                    //debounce
                    getData(search)
                }
                else
                {
                    mAdapter?.updateData(emptyList())
                }
                return true
            }
        })
        mRecyclerView = findViewById(R.id.recyclerView)
        mAdapter = movieAdapter(this,moviesList,this)
        mRecyclerView?.adapter = mAdapter
        mRecyclerView?.layoutManager = LinearLayoutManager(this)

    }

    private fun getData(search: String) {

        lifecycleScope.launch(Dispatchers.IO){
            val retrofitData=retrofitBuilder.getMovies(search,API_KEY)

            retrofitData.enqueue(object : Callback<MoviesResponse?> {

                override fun onResponse(
                    call: Call<MoviesResponse?>,
                    response: Response<MoviesResponse?>
                ) {
                    val result=response.body()
                    if(result!=null&&result.Response.equals("true",ignoreCase = true)) {

                        mAdapter?.updateData(result.Search)
                        Toast.makeText(this@MainActivity, "total results found"+ result.totalResults, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<MoviesResponse?>, t: Throwable) {
                    Log.d("API_CALL","onFailure"+t.message)

                    mAdapter?.updateData(emptyList())

                }
            })
        }

    }

    override fun onMovieClick(imdbId:String) {
        val intent=Intent(this,movieDetail::class.java)
        intent.putExtra("imdbID",imdbId)
        startActivity(intent)
    }

}