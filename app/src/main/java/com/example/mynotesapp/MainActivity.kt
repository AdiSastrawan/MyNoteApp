package com.example.mynotesapp

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotesapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class MainActivity : AppCompatActivity() {
    private  val list = ArrayList<Notes>()
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvNotes.setHasFixedSize(true)
        list.addAll(loadJson())
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
        if(list.size >0){

        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        var listNoteAdapter = ListNoteAdapter(this,list)
        binding.rvNotes.adapter = listNoteAdapter
        }
    }

    fun loadJson(): ArrayList<Notes>{
        val gson = Gson()
        try {
            val outputStreamReader = openFileInput("notes.json")
            val jsonReader =  BufferedReader( InputStreamReader(outputStreamReader))
            val type = object : TypeToken<List<Notes>>() {}.type
            val notesList:ArrayList<Notes> = gson.fromJson(jsonReader,type)
            val listNote = ArrayList<Notes>()
            for (i in notesList.indices) {
                val notes = Notes(notesList[i].name, notesList[i].description)
                listNote.add(notes)
            }
            return listNote
        }catch(e:FileNotFoundException){
            val outputStreamWriter = OutputStreamWriter(openFileOutput("notes.json", MODE_PRIVATE))
            outputStreamWriter.close()
            return ArrayList()
        }catch(e:NullPointerException){
            return ArrayList()
        }
    }


}