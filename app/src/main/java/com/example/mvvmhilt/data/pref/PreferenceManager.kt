package com.example.mvvmhilt.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mvvmhilt.data.models.AuthInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(val context: Context) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        val NAME = stringPreferencesKey("NAME")
        val PASSWORD = stringPreferencesKey("PASSWORD")
    }

    suspend fun login(authInfo: AuthInfo): Boolean {
        return context.dataStore.edit {
            it[NAME] = authInfo.name
            it[PASSWORD] = authInfo.password
        }.asMap().isNotEmpty()
    }

    suspend fun isLoggedIn(): Boolean {
        return !context.dataStore.edit {  }.asMap().isEmpty()
    }

    suspend fun logout(): Boolean {
        return context.dataStore.edit { it.clear() }.asMap().isEmpty()
    }

}