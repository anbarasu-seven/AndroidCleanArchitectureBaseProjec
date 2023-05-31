package com.example.mvvmhilt.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mvvmhilt.data.models.User
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(val context: Context) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        val NAME = stringPreferencesKey("NAME")
        val CRAFT = stringPreferencesKey("CRAFT")
        val EMAIL = stringPreferencesKey("EMAIL")
        val PHONE_NUMBER = stringPreferencesKey("PHONE_NUMBER")
    }

    suspend fun save(userData: User) {
        context.dataStore.edit {
            it[NAME] = userData.name
            it[EMAIL] = "user.email"
            it[PHONE_NUMBER] = "user.phoneNo"
        }
    }

    suspend fun getFromDataStore() = context.dataStore.data.map {
        User(name = it[NAME] ?: "", craft = it[CRAFT] ?: "",)
    }
}