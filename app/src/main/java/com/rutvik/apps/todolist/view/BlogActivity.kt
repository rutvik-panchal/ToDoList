package com.rutvik.apps.todolist.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.rutvik.apps.todolist.R
import com.rutvik.apps.todolist.adapter.BlogsAdapter
import com.rutvik.apps.todolist.model.Blogs
import com.rutvik.apps.todolist.model.JsonResponse


class BlogActivity : AppCompatActivity() {

    val TAG = "BlogActivity"

    var blogsList = ArrayList<Blogs>()

    lateinit var recyclerViewBlogs : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)

        bindViews()
        supportActionBar?.title = "Blogs"
        getBlogs()
    }

    private fun getBlogs() {
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(JsonResponse::class.java, object : ParsedRequestListener<JsonResponse> {
                    override fun onResponse(response: JsonResponse?) {
                        setUpBlogsAdapter(response)
                    }

                    override fun onError(anError: ANError?) {
                        Log.d(TAG, anError?.localizedMessage)
                    }

                })
    }

    private fun setUpBlogsAdapter(response: JsonResponse?) {
        val blogsAdapter = BlogsAdapter(response!!.data)
        val linearLayoutManager = LinearLayoutManager(this@BlogActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        val dividerItemDecoration = DividerItemDecoration(recyclerViewBlogs.context,
                linearLayoutManager.orientation)
        recyclerViewBlogs.addItemDecoration(dividerItemDecoration)
        recyclerViewBlogs.layoutManager = linearLayoutManager
        recyclerViewBlogs.adapter = blogsAdapter
    }

    private fun bindViews() {
        recyclerViewBlogs = findViewById(R.id.recyclerViewBlogs)
    }
}
