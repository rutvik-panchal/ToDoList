package com.rutvik.apps.todolist.clicklisteners

import com.rutvik.apps.todolist.db.Notes

interface NotesClickListeners {

    fun onClick(notes: Notes)

    fun onUpdate(notes: Notes)

}