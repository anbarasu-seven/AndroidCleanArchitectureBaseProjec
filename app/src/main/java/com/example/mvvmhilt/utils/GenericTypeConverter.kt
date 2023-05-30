package com.example.mvvmhilt.utils

import com.example.mvvmhilt.data.models.UserData
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

class GenericTypeConverter @Inject constructor() {
    private val gson: Gson = Gson()

    /**
     * Transform from valid json string to [UserEntity].
     *
     * @param userJsonResponse A json representing a user profile.
     * @return [UserEntity].
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    @Throws(JsonSyntaxException::class)
    fun transformUserEntity(userJsonResponse: String?): UserData {
        val userEntityType: Type = object : TypeToken<UserData?>() {}.type
        return gson.fromJson(userJsonResponse, userEntityType)
    }

    /**
     * Transform from valid json string to List of [UserEntity].
     *
     * @param userListJsonResponse A json representing a collection of users.
     * @return List of [UserEntity].
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    @Throws(JsonSyntaxException::class)
    fun transformUserEntityCollection(userListJsonResponse: String?): List<UserData> {
        val listOfUserEntityType: Type = object : TypeToken<List<UserData?>?>() {}.type
        return gson.fromJson<List<UserData>>(userListJsonResponse, listOfUserEntityType)
    }

}