package com.example.netsample.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.netsample.R

class PostViewHolder(itemView: ConstraintLayout): RecyclerView.ViewHolder(itemView) {
    private val titleView: TextView = itemView.findViewById(R.id.titleView)
    private val bodyView: TextView = itemView.findViewById(R.id.bodyView)
    fun bind(post: Post) {
        titleView.text = post.title
        bodyView.text = post.body
    }

}

class PostsAdapter(private val postsList: List<Post>) : RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.post, parent, false)
        return PostViewHolder(itemView as ConstraintLayout)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postsList[position])
    }

    override fun getItemCount(): Int {
        return postsList.size
    }
}