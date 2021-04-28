package com.example.netsample.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netsample.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ObservableFragment : Fragment() {

    companion object {
        fun newInstance() = ObservableFragment()
    }

    private lateinit var postsView: RecyclerView
    private lateinit var viewModel: ObservableViewModel
    private var postsAdapter: PostsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.observable_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postsView = view.findViewById(R.id.postsView)
        postsView.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        NetworkPostRepository.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { list ->
                    onPostsLoaded(list)
                }, {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                })
    }

    private fun onPostsLoaded(posts: List<Post>) {
        val postsAdapter = PostsAdapter(posts)
        postsView.adapter = postsAdapter
        postsAdapter.notifyDataSetChanged()
        this.postsAdapter = postsAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ObservableViewModel::class.java)
        // TODO: Use the ViewModel
    }

}