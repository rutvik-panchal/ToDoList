package com.rutvik.apps.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rutvik.apps.todolist.R
import com.rutvik.apps.todolist.clicklisteners.NotesClickListeners
import com.rutvik.apps.todolist.db.Notes

class NotesAdapter(private val list: List<Notes>, private val itemClickListener: NotesClickListeners)
    : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout,
                parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note  = list[position]
        holder.textViewTitle.text = note.title
        holder.textViewDescription.text = note.description
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(note)
        }

        Glide.with(holder.itemView).load(note.imagePath).into(holder.imageViewImage)
        holder.checkBoxWorkStatus.isChecked = note.isTaskCompleted
        holder.checkBoxWorkStatus.setOnCheckedChangeListener { buttonView, isChecked ->
            note.isTaskCompleted = isChecked
            itemClickListener.onUpdate(note)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val checkBoxWorkStatus: CheckBox = itemView.findViewById(R.id.checkBoxMarkStatus)
        val imageViewImage: ImageView = itemView.findViewById(R.id.imageViewImage)
    }
}