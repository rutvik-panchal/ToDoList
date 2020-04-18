package com.rutvik.apps.todolist

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.rutvik.apps.todolist.db.NotesDatabase

class NotesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }

    fun getNotesDb() : NotesDatabase {
        return NotesDatabase.getInstance(this)
    }

}