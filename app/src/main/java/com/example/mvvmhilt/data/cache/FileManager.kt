package com.example.mvvmhilt.data.cache

import android.content.Context
import android.content.SharedPreferences
import java.io.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileManager @Inject internal constructor() {
    /**
     * Writes a file to Disk.
     * This is an I/O operation and this method executes in the main thread, so it is recommended to
     * perform this operation using another thread.
     *
     * @param file The file to write to Disk.
     */
    suspend fun writeToFile(file: File, fileContent: String?) {
        if (!file.exists()) {
            try {
                val writer = FileWriter(file)
                writer.write(fileContent)
                writer.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Reads a content from a file.
     * This is an I/O operation and this method executes in the main thread, so it is recommended to
     * perform the operation using another thread.
     *
     * @param file The file to read from.
     * @return A string with the content of the file.
     */
    suspend fun readFileContent(file: File): String {
        val fileContentBuilder = StringBuilder()
        if (file.exists()) {
            var stringLine: String?
            try {
                val fileReader = FileReader(file)
                val bufferedReader = BufferedReader(fileReader)
                while (bufferedReader.readLine().also { stringLine = it } != null) {
                    fileContentBuilder.append(stringLine).append("\n")
                }
                bufferedReader.close()
                fileReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return fileContentBuilder.toString()
    }

    /**
     * Returns a boolean indicating whether this file can be found on the underlying file system.
     *
     * @param file The file to check existence.
     * @return true if this file exists, false otherwise.
     */
    fun exists(file: File): Boolean {
        return file.exists()
    }

    /**
     * Warning: Deletes the content of a directory.
     * This is an I/O operation and this method executes in the main thread, so it is recommended to
     * perform the operation using another thread.
     *
     * @param directory The directory which its content will be deleted.
     */
    suspend fun clearDirectory(directory: File): Boolean {
        var result = false
        if (directory.exists()) {
            for (file in directory.listFiles()) {
                result = file.delete()
            }
        }
        return result
    }

    /**
     * Write a value to a user preferences file.
     *
     * @param context [android.content.Context] to retrieve android user preferences.
     * @param preferenceFileName A file name reprensenting where data will be written to.
     * @param key A string for the key that will be used to retrieve the value in the future.
     * @param value A long representing the value to be inserted.
     */
    fun writeToPreferences(
        context: Context, preferenceFileName: String?, key: String?,
        value: Long
    ) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            preferenceFileName,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    /**
     * Get a value from a user preferences file.
     *
     * @param context [android.content.Context] to retrieve android user preferences.
     * @param preferenceFileName A file name representing where data will be get from.
     * @param key A key that will be used to retrieve the value from the preference file.
     * @return A long representing the value retrieved from the preferences file.
     */
    fun getFromPreferences(context: Context, preferenceFileName: String?, key: String?): Long {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            preferenceFileName,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getLong(key, 0)
    }
}