package com.example.storage

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.storage.StorageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.io.*
import java.util.*

class ExternalStorageManager private constructor() {
    // Checks if a volume containing external storage is available for read and write.
    val isExternalStorageWritable: Boolean
        get() = Environment.getExternalStorageState() === Environment.MEDIA_MOUNTED

    // Checks if a volume containing external storage is available to at least read.
    val isExternalStorageReadable: Boolean
        get() = Environment.getExternalStorageState() === Environment.MEDIA_MOUNTED ||
                Environment.getExternalStorageState() === Environment.MEDIA_MOUNTED_READ_ONLY

    fun getPrimaryExternalLocation(context: Context?): File {
        val externalStorageVolumes =
            ContextCompat.getExternalFilesDirs(
                context!!, null
            )
        return externalStorageVolumes[0]
    }

    fun create(context: Context, fileName: String?): Boolean {
        val appSpecificExternalDir = fileName?.let {
            File(context.getExternalFilesDir(fileName),
                it
            )
        }
        return appSpecificExternalDir != null
    }

    fun getFile(context: Context, fileName: String?): File? {
        return fileName?.let { File(context.getExternalFilesDir(fileName), it) }
    }

    fun write(file: File?, data: String?) {
        try {
            val fileWriter = FileWriter(file)
            fileWriter.append(data)
            fileWriter.flush()
            fileWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun read(file: File?): StringBuilder {
        var line: String?
        val stringBuilder = StringBuilder()
        try {
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder
    }

    fun createCacheFile(context: Context, filename: String?): Boolean {
        val externalCacheFile = filename?.let { File(context.externalCacheDir, it) }
        return externalCacheFile != null
    }

    fun getCacheFile(context: Context, filename: String?): File? {
        return filename?.let { File(context.externalCacheDir, it) }
    }

    fun deleteCacheFile(context: Context, filename: String?): Boolean? {
        val externalCacheFile = filename?.let { File(context.externalCacheDir, it) }
        return externalCacheFile?.delete()
    }

    fun saveMediaFiles(context: Context, fileName: String?, directoryName: String?): File? {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        //directoryName = Environment.DIRECTORY_PICTURES
        val file = fileName?.let {
            File(
                context.getExternalFilesDir(
                    directoryName
                ), it
            )
        }
        if (file == null || !file.mkdirs()) {
            Toast.makeText(context, "File not created.", Toast.LENGTH_SHORT).show()
        }
        return file
    }

    fun queryFreeSpace(spaceNeededForMyApp: Long, context: Context) {
        val storageManager = context.getSystemService(
            StorageManager::class.java
        )
        val appSpecificInternalDirUuid: UUID?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                appSpecificInternalDirUuid = storageManager.getUuidForPath(context.filesDir)
                val availableBytes: Long = storageManager.getAllocatableBytes(appSpecificInternalDirUuid)
                if (availableBytes >= spaceNeededForMyApp) {
                    storageManager.allocateBytes(
                        appSpecificInternalDirUuid, spaceNeededForMyApp
                    )
                } else {
                    val storageIntent = Intent()
                    storageIntent.action = StorageManager.ACTION_MANAGE_STORAGE
                    // Display prompt to user, requesting that they choose files to remove.
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        var instance: ExternalStorageManager? = null
            get() {
                if (field == null) {
                    field = ExternalStorageManager()
                }
                return field
            }
            private set
    }
}