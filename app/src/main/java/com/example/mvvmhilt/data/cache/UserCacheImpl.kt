package com.example.mvvmhilt.data.cache

import android.content.Context
import kotlinx.coroutines.*
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserCacheImpl @Inject internal constructor(
    context: Context?, serializer: Serializer?,
    fileManager: FileManager?
) : UserCache {

    companion object {
        private const val SETTINGS_FILE_NAME = "com.example.mvvmhilt.SETTINGS"
        private const val SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update"
        private const val DEFAULT_FILE_NAME = "cache_"
        private const val EXPIRATION_TIME = (60 * 10 * 1000).toLong()
    }

    private val context: Context
    private val cacheDir: File
    private val serializer: Serializer
    private val fileManager: FileManager

    /**
     * Constructor of the class [UserCacheImpl].
     *
     * @param context A
     * @param serializer [Serializer] for object serialization.
     * @param fileManager [FileManager] for saving serialized objects to the file system.
     */
    init {
        require(!(context == null || serializer == null || fileManager == null)) { "Invalid null parameter" }
        this.context = context.applicationContext
        cacheDir = this.context.cacheDir
        this.serializer = serializer
        this.fileManager = fileManager
    }

    override suspend fun get(userId: String, clazz: Class<Any?>): Any? {
        val userEntityFile = buildFile(userId)
        val fileContent =
            withContext(Dispatchers.IO) { fileManager.readFileContent(userEntityFile) }
        val entity: Any? = serializer.deserialize(fileContent, clazz::class.java)
        return entity
    }

    override suspend fun put(any: Any?, clazz: Class<Any>, objectId: String) {
        if (any != null) {
            val userEntityFile = buildFile(objectId)
            if (!isCached(objectId)) {
                val jsonString = serializer.serialize(any, clazz::class.java)
                withContext(Dispatchers.IO) { fileManager.writeToFile(userEntityFile, jsonString) }
                setLastCacheUpdateTimeMillis()
            }
        }
    }

    override fun isCached(objectId: String): Boolean {
        val userEntityFile = buildFile(objectId)
        return fileManager.exists(userEntityFile)
    }


    override val isExpired: Boolean
        get() {
            val currentTime = System.currentTimeMillis()
            val lastUpdateTime = lastCacheUpdateTimeMillis
            val expired = currentTime - lastUpdateTime > EXPIRATION_TIME
            if (expired) {
                evictAll()
            }
            return expired
        }

    override fun evictAll() {
        GlobalScope.async { fileManager.clearDirectory(cacheDir) }
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param userId The id user to build the file.
     * @return A valid file.
     */
    private fun buildFile(userId: String): File {
        val fileNameBuilder = StringBuilder()
        fileNameBuilder.append(cacheDir.path)
        fileNameBuilder.append(File.separator)
        fileNameBuilder.append(DEFAULT_FILE_NAME)
        fileNameBuilder.append(userId)
        return File(fileNameBuilder.toString())
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private fun setLastCacheUpdateTimeMillis() {
        val currentMillis = System.currentTimeMillis()
        fileManager.writeToPreferences(
            context, SETTINGS_FILE_NAME,
            SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis
        )
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private val lastCacheUpdateTimeMillis: Long
        private get() = fileManager.getFromPreferences(
            context, SETTINGS_FILE_NAME,
            SETTINGS_KEY_LAST_CACHE_UPDATE
        )


}
