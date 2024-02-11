package com.example.mynotesapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mynotesapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_NOTE = "extra_note"
    }
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra<Notes>(EXTRA_NOTE,Notes::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Notes>(EXTRA_NOTE)
        }
        Log.d("notes",notes.toString())
        binding.tvName.text = notes?.name
        binding.tvDescription.text = notes?.description
    }
}