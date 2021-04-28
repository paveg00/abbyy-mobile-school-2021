package com.example.netsample.ui.main

import io.reactivex.Single

interface IPostRepository {
    fun getPosts(): Single<List<Post>>
}