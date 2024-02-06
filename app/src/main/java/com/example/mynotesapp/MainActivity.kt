package com.example.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  val list = ArrayList<Notes>()
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvNotes.setHasFixedSize(true)
        list.addAll(getAllNote())
        showRecyclerList(binding)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_note ->{
                val moveIntent = Intent(this@MainActivity,AddNoteActivity::class.java)
                startActivity(moveIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showRecyclerList(binding:ActivityMainBinding) {
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        var listNoteAdapter = ListNoteAdapter(list)
        binding.rvNotes.adapter = listNoteAdapter
    }

    private fun getAllNote(): ArrayList<Notes> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val listNote = ArrayList<Notes>()
        for (i in dataName.indices) {
            val notes = Notes(dataName[i], dataDescription[i])
            listNote.add(notes)
        }
        return listNote
    }



}