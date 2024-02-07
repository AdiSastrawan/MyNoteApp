package com.example.mynotesapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mynotesapp.databinding.ActivityAddNoteBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class AddNoteActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding:ActivityAddNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAdd.setOnClickListener(this)
    }

    private fun writeJson() {
        val name = binding.editName.text.toString()
        val description = binding.editDescription.text.toString()
        val note = Notes(name,description)

        val gson = Gson()


        try{

            val outputStreamReader = openFileInput("notes.json")
            val jsonReader =  BufferedReader( InputStreamReader(outputStreamReader))
            val type = object : TypeToken<List<Notes>>() {}.type
            val notesList:ArrayList<Notes> = gson.fromJson(jsonReader,type)
            notesList.add(note)
            val updatedJson = gson.toJson(notesList)
            val outputStreamWriter = OutputStreamWriter(openFileOutput("notes.json", Context.MODE_PRIVATE))
            outputStreamWriter.write(updatedJson)
            outputStreamWriter.close()
            Toast.makeText(this, "Saved to "+filesDir+"/notes.json",Toast.LENGTH_LONG).show()
        }catch (e:NullPointerException){
            val notesList:ArrayList<Notes> = ArrayList()
            notesList.add(note)
            val updatedJson = gson.toJson(notesList)
            val outputStreamWriter = OutputStreamWriter(openFileOutput("notes.json", Context.MODE_PRIVATE))
            outputStreamWriter.write(updatedJson)
            outputStreamWriter.close()
            Toast.makeText(this, "Saved to test pertama "+filesDir+"/notes.json",Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btnAdd.id -> {
                writeJson()
                val moveIntent = Intent(this@AddNoteActivity,MainActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }
}