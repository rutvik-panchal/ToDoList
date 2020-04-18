package com.rutvik.apps.todolist.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.github.therajanmaurya.sweeterror.SweetUIErrorHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rutvik.apps.todolist.NotesApp
import com.rutvik.apps.todolist.utils.AppConstant
import com.rutvik.apps.todolist.R
import com.rutvik.apps.todolist.adapter.NotesAdapter
import com.rutvik.apps.todolist.clicklisteners.NotesClickListeners
import com.rutvik.apps.todolist.db.Notes
import com.rutvik.apps.todolist.workmanager.MyWorker
import java.util.concurrent.TimeUnit

class MyNotesActivity : AppCompatActivity() {

    val TAG = "MyNotesActivity"

    lateinit var recyclerViewNotes: RecyclerView
    lateinit var fabAddNotes: FloatingActionButton
    lateinit var userFullName: String

    private var notesList = ArrayList<Notes>()

    lateinit var layoutError : View
    lateinit var sweetUIErrorHandler: SweetUIErrorHandler

    val ADD_NOTES_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        bindViews()
        getIntentData()
        getDataFromDatabase()
        supportActionBar?.title = userFullName
        showToDoList()
        clicklisteners()
        setUpWorkManager()
    }

    private fun clicklisteners() {
        fabAddNotes.setOnClickListener {
            //setUpAddNotesDialogBox()
            val intent = Intent(this@MyNotesActivity, AddNotesActivity::class.java)
            startActivityForResult(intent, ADD_NOTES_CODE)
        }
    }

    private fun setUpWorkManager() {
        val constraint = Constraints.Builder().build()
        val workRequest = PeriodicWorkRequest
                .Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()
        WorkManager.getInstance().enqueue(workRequest)
    }

    private fun getDataFromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesList.addAll(notesDao.getAll())
    }

    private fun setUpAddNotesDialogBox() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialog_layout, null)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)

        val addNotesDialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create()

        buttonSubmit.setOnClickListener {
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()
            if (title.isNotEmpty() && description.isNotEmpty()) {
                val note = Notes(title = title, description = description)
                notesList.add(note)
                addNoteToDatabase(note)
            } else {
                Toast.makeText(this,
                        "Title and Description can't be empty!", Toast.LENGTH_SHORT).show()
            }
            setUpNotesAdapter()
            addNotesDialog.hide()
        }
        addNotesDialog.show()
    }

    private fun addNoteToDatabase(note: Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(note)
    }

    private fun setUpNotesAdapter() {
        val notesClickListener = object : NotesClickListeners {
            override fun onClick(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, NotesDetailActivity::class.java)
                intent.putExtra(AppConstant.NOTES_TITLE, notes.title)
                intent.putExtra(AppConstant.NOTES_DESCRIPTION, notes.description)
                intent.putExtra(AppConstant.NOTES_IMAGE_PATH, notes.imagePath)
                startActivity(intent)
            }
            override fun onUpdate(notes: Notes) {
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.update(notes)
            }
        }
        val notesAdapter = NotesAdapter(notesList, notesClickListener)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter
//        recyclerViewNotes.adapter?.notifyItemChanged(notesList.size -1)
    }

    private fun showEmptyError() {
        sweetUIErrorHandler.showSweetEmptyUI(getString(R.string.error_no_todo), R.drawable.ic_list_check, recyclerViewNotes,
                layoutError)
    }

    private fun hideEmptyError() {
        sweetUIErrorHandler.hideSweetErrorLayoutUI(recyclerViewNotes, layoutError)
    }

    private fun showToDoList() {
        if (notesList.size == 0) {
            showEmptyError()
        } else {
            hideEmptyError()
            setUpNotesAdapter()
        }
    }

    private fun bindViews() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
        layoutError = findViewById(R.id.layoutError)
        sweetUIErrorHandler = SweetUIErrorHandler(this, findViewById(android.R.id.content))
    }

    private fun getIntentData() {
        val intent = intent
        userFullName = intent.getStringExtra(AppConstant.FULL_NAME)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_NOTES_CODE) {
                val note = Notes(title = data!!.getStringExtra(AppConstant.NOTES_TITLE),
                        description = data!!.getStringExtra(AppConstant.NOTES_DESCRIPTION),
                        imagePath = data!!.getStringExtra(AppConstant.NOTES_IMAGE_PATH))
                addNoteToDatabase(note)
                notesList.add(note)
                showToDoList()
//                recyclerViewNotes.adapter?.notifyItemChanged(notesList.size -1)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.blog) {
            startActivity(Intent(this@MyNotesActivity, BlogActivity::class.java))
            Log.d(TAG, "Blog Clicked")
        }
        return super.onOptionsItemSelected(item)
    }
}