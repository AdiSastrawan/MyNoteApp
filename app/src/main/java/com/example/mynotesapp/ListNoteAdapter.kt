package com.example.mynotesapp

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.databinding.ItemNoteRowBinding
class ListNoteAdapter(private val listNote: ArrayList<Notes>) : RecyclerView.Adapter<ListNoteAdapter.ListViewHolder>() {
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

    }
}