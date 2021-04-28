package com.example.netsample.ui.main

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface Api {
    @GET("posts")
    fun getPostsObs(): Observable<List<Post>>
}

object NetworkPostRepository: IPostRepository {

    private const val POSTS_URL = "https://jsonplaceholder.typicode.com/"
    private val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(POSTS_URL)
            .build()

    private val api = retrofit.create(Api::class.java)

    override fun getPosts(): Single<List<Post>> {
        return api.getPostsObs().map { list ->
            list.subList(0, 10)
        }.single(emptyList())
    }
}