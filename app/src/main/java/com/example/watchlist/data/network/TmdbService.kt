package com.example.watchlist.data.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TmdbService{

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185"
        const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w300"

        private val retrofitService by lazy {
            // Add api key to every request
            val interceptor = Interceptor { chain ->
                val request = chain.request()
                val url = request.url().newBuilder()
                    .addQueryParameter("api_key", "a148602c4a0fd87a3c65332a5e407703")
                    .build()
                val newRequest = request.newBuilder()
                    .url(url)
                    .build()
                chain.proceed(newRequest)
            }

            val httpClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(TmdbService::class.java)
        }

        fun getInstance(): TmdbService {
            return retrofitService
        }
    }

    @GET("discover/movie?certification_country=US&adult=false&vote_count.gte=100&" +
            "with_original_language=en&sort_by=primary_release_date.desc")
    suspend fun getMovies(): Response<TmdbMovieList>
}