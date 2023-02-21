package com.example.storage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.storage.databinding.ActivityInternalStorageBinding
import java.io.File

class InternalStorageActivity : AppCompatActivity(), View.OnClickListener {

    private val internalStorageManager: InternalStorageManager? = Storage.instance?.internalStorage
    private lateinit var binding: ActivityInternalStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal_storage)

        binding = ActivityInternalStorageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnCreateCacheFile.setOnClickListener(this)
        binding.btnCreateFile.setOnClickListener(this)
        binding.btnRead.setOnClickListener(this)
        binding.btnWrite.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnCreateFile -> {
                val isFileCreated = internalStorageManager!!.createFile(this, "one")
                if (isFileCreated) {
                    Toast.makeText(this, "File created.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btnWrite -> {
                val file: File? = internalStorageManager!!.getFile(this, "one")
                Storage.instance?.internalStorage?.write(
                    file,
                    "qwertyuiopasdfghjklzxcvbnm.+111"
                )
                Toast.makeText(this, "File written.", Toast.LENGTH_SHORT).show()
            }
            R.id.btnRead -> {
                val file1: File? = Storage.instance?.internalStorage?.getFile(this, "one")
                val data: StringBuilder? = Storage.instance?.internalStorage?.read(file1)
                Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show()
            }
            R.id.btnCreateCacheFile -> {
                val isFileCreated3 = internalStorageManager!!.createCacheFile(
                    this,
                    "CacheFile"
                )
                if (isFileCreated3) {
                    Toast.makeText(this, "File created.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                }

                //write something
                val file3: File? = internalStorageManager.getCacheFile(this, "CacheFile")
                internalStorageManager.write(file3, "Cache Text.")

                //read it
                val data3 = internalStorageManager.read(file3)
                Toast.makeText(this, data3.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}