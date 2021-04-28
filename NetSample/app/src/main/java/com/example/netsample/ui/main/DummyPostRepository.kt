package com.example.netsample.ui.main

import io.reactivex.Observable
import io.reactivex.Single

object DummyPostRepository : IPostRepository {
    override fun getPosts(): Single<List<Post>> {
        return Observable.fromArray(
                Post(1, 1, "Fake post 1", "Lorem ipsum dolor sit amet..."),
                Post(2, 1, "Fake post 2", "Lorem ipsum dolor sit amet..."),
                Post(3, 1, "Fake post 3", "Lorem ipsum dolor sit amet..."),
                Post(4, 1, "Fake post 4", "Lorem ipsum dolor sit amet..."),
                Post(5, 1, "Fake post 5", "Lorem ipsum dolor sit amet..."),
                Post(6, 1, "Fake post 6", "Lorem ipsum dolor sit amet..."),
                Post(7, 1, "Fake post 7", "Lorem ipsum dolor sit amet..."),
                Post(8, 1, "Fake post 8", "Lorem ipsum dolor sit amet..."),
        ).take(5)
                .toList()
    }
}