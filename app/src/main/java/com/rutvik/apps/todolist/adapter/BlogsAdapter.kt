package com.rutvik.apps.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rutvik.apps.todolist.R
import com.rutvik.apps.todolist.model.Blogs

class BlogsAdapter(val list: List<Blogs>)
    : RecyclerView.Adapter<BlogsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_adapter_layout,
                parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog = list[position]
        holder.textViewBlogsTitle.text = blog.title.trim()
        holder.textViewBlogsDescription.text = blog.description.trim()
        Glide.with(holder.itemView).load(blog.img_url).into(holder.imageViewBlogs)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewBlogs: ImageView = itemView.findViewById(R.id.imageViewBlogs)
        val textViewBlogsTitle: TextView = itemView.findViewById(R.id.textViewBlogTitle)
        val textViewBlogsDescription: TextView = itemView.findViewById(R.id.textViewBlogDescription)
    }
}