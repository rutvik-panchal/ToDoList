package com.rutvik.apps.todolist.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

// DAO - Data Access Object

@Dao
interface NotesDao {

    @Query("SELECT * from notesData")
    fun getAll(): List<Notes>

    @Insert(onConflict = REPLACE)
    fun insert(notes: Notes)

    @Update
    fun update(notes: Notes)

    @Delete
    fun delete(notes: Notes)

    @Query("DELETE FROM notesData WHERE isTaskCompleted = :status")
    fun deleteNotes(status : Boolean)

}