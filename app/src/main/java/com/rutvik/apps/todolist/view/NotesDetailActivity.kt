package com.rutvik.apps.todolist.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.PointerIconCompat.load
import com.bumptech.glide.Glide
import com.rutvik.apps.todolist.utils.AppConstant
import com.rutvik.apps.todolist.R
import java.io.File

class NotesDetailActivity : AppCompatActivity() {

    lateinit var textViewTitle: TextView
    lateinit var textViewDescription: TextView
    lateinit var imageViewNotes : ImageView

    var imagePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_detail)

        bindViews()
        setIntentData()
//        var file = File(imagePath)
        Glide.with(this@NotesDetailActivity).load(imagePath).into(imageViewNotes)
    }

    private fun bindViews() {
        textViewTitle = findViewById(R.id.textViewNotesTitle)
        textViewDescription = findViewById(R.id.textViewNotesDescription)
        imageViewNotes = findViewById(R.id.imageViewNotesDetail)
    }

    private fun setIntentData() {
        val intent = intent
        textViewTitle.text = intent.getStringExtra(AppConstant.NOTES_TITLE)
        textViewDescription.text = intent.getStringExtra(AppConstant.NOTES_DESCRIPTION)
        imagePath = intent.getStringExtra(AppConstant.NOTES_IMAGE_PATH)
    }

}