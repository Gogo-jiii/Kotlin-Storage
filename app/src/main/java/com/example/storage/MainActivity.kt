package com.example.storage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnGotoExternalStorageActivity.setOnClickListener {
            startActivity(Intent(this@MainActivity, ExternalStorageActivity::class.java))
        }

        binding.btnGotoInternalStorageActivity.setOnClickListener {
            startActivity(Intent(this@MainActivity, InternalStorageActivity::class.java))
        }
    }
}