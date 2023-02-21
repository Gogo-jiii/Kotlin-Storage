package com.example.storage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.storage.databinding.ActivityExternalStorageBinding
import java.io.File


class ExternalStorageActivity : AppCompatActivity(), View.OnClickListener {

    private val externalStorageManager: ExternalStorageManager? = Storage.instance?.externalStorage
    private lateinit var binding: ActivityExternalStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external_storage)

        binding = ActivityExternalStorageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnCreateFile.setOnClickListener(this)
        binding.btnWrite.setOnClickListener(this)
        binding.btnRead.setOnClickListener(this)
        binding.btnCreateCacheFile.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnCreateFile -> {
                val isFileCreated = externalStorageManager!!.create(this, "Four")
                if (isFileCreated) {
                    Toast.makeText(this, "File created.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "File NOT created.", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btnWrite -> {
                val file: File? = externalStorageManager!!.getFile(this, "Four")
                externalStorageManager.write(file, "999999999")
                Toast.makeText(this, "File written.", Toast.LENGTH_SHORT).show()
            }
            R.id.btnRead -> {
                val file2: File? = externalStorageManager!!.getFile(this, "Four")
                val stringBuilder = externalStorageManager.read(file2)
                Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show()
            }
            R.id.btnCreateCacheFile -> {
                val isCacheFileCreated = externalStorageManager!!.createCacheFile(
                    this,
                    "CacheFile"
                )
                if (isCacheFileCreated) {
                    Toast.makeText(this, "Cache File created.", Toast.LENGTH_SHORT).show()
                    val file3: File? = externalStorageManager.getCacheFile(this, "CacheFile")
                    externalStorageManager.write(file3, "Cache Text")
                    val stringBuilder1 = externalStorageManager.read(file3)
                    Toast.makeText(this, stringBuilder1, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}