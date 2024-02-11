package com.example.mynotesapp

import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.databinding.ItemNoteRowBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class ListNoteAdapter(private val context: Context,private val listNote: ArrayList<Notes>) : RecyclerView.Adapter<ListNoteAdapter.ListViewHolder>() {
    class ListViewHolder( var binding: ItemNoteRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemNoteRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listNote.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name,description) = listNote[position]
        holder.binding.tvNotesName.text = name
        holder.binding.tvNotesDescription.text = description
        holder.binding.btnDelete.setOnClickListener{
            val gson = Gson()
            val list = ArrayList<Notes>()
            try {
                val outputStreamReader = context.openFileInput("notes.json")
                val jsonReader =  BufferedReader( InputStreamReader(outputStreamReader))
                val type = object : TypeToken<List<Notes>>() {}.type
                val notesList:ArrayList<Notes> = gson.fromJson(jsonReader,type)
                for (i in notesList.indices) {
                    val notes = Notes(notesList[i].name, notesList[i].description)
                    list.add(notes)
                }

            }catch (e : FileNotFoundException){
                val outputStreamWriter = OutputStreamWriter(context.openFileOutput("notes.json",
                    AppCompatActivity.MODE_PRIVATE
                ))
                outputStreamWriter.close()
            }
            Log.d("position",position.toString())
            list.removeAt(position)
            val updatedJson = gson.toJson(list)
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput("notes.json", Context.MODE_PRIVATE))
            outputStreamWriter.write(updatedJson)
            outputStreamWriter.close()
            val intent = Intent(context,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_NOTE,listNote[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }

    }

}