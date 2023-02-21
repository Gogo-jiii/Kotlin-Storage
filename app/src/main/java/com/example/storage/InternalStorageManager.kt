package com.example.storage

import android.content.Context
import java.io.*

class InternalStorageManager private constructor() {
    fun createFile(context: Context, fileName: String?): Boolean {
        fileName?.let { File(context.filesDir, it) }
        return true
    }

    fun getFile(context: Context, fileName: String?): File? {
        return fileName?.let { File(context.filesDir, it) }
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

    fun getFileNames(context: Context): Array<String> {
        return context.fileList()
    }

    fun createFileInDirectory(context: Context, fileName: String?): Boolean {
        val directory = context.filesDir
        val file = fileName?.let { File(directory, it) }
        return true
    }

    fun getFileInDirectory(
        context: Context,
        fileName: String?
    ): File? {
        val directory = context.filesDir
        return fileName?.let { File(directory, it) }
    }

    fun createCacheFile(context: Context, filename: String?): Boolean {
        var file: File? = null
        try {
            file = filename?.let { File.createTempFile(it, null, context.cacheDir) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file != null
    }

    fun getCacheFile(context: Context, filename: String?): File? {
        return filename?.let { File(context.cacheDir, it) }
    }

    fun deleteCacheFile(context: Context, filename: String?): Boolean {
        return context.deleteFile(filename)
    }

    companion object {
        var instance: InternalStorageManager? = null
            get() {
                if (field == null) {
                    field = InternalStorageManager()
                }
                return field
            }
            private set
    }
}