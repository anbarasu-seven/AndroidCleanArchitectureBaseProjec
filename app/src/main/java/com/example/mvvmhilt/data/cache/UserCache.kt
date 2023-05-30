package com.example.mvvmhilt.data.cache

interface UserCache {
    /**
     * Gets an [Observable] which will emit a [UserEntity].
     *
     * @param userId The user id to retrieve data.
     */
    suspend fun get(userId: String, clazz: Class<Any?>): Any?

    /**
     * Puts and element into the cache.
     *
     * @param userEntity Element to insert in the cache.
     */
    suspend fun put(any: Any?, clazz: Class<Any>, objectId: String)

    /**
     * Checks if an element (User) exists in the cache.
     *
     * @param userId The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    fun isCached(userId: String): Boolean

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    val isExpired: Boolean

    /**
     * Evict all elements of the cache.
     */
    fun evictAll()
}